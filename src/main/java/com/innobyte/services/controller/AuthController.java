package com.innobyte.services.controller;

import java.io.IOException;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.innobyte.services.dto.AuthenticationRequest;
import com.innobyte.services.dto.ProductDto;
import com.innobyte.services.dto.SignupRequest;
import com.innobyte.services.dto.UserDto;
import com.innobyte.services.models.User;
import com.innobyte.services.repositories.UserRepository;
import com.innobyte.services.services.auth.AuthService;
import com.innobyte.services.utils.JwtUtil;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;
	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	private final AuthService authService;
	
	public static final String HEADER_STRING = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer";
	
	@PostMapping("/authenticate")
	public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,HttpServletResponse response) throws IOException, JSONException {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		}catch(BadCredentialsException e) {
			throw new BadCredentialsException("Incorrect username or password.");
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails.getUsername());
		
		if(optionalUser.isPresent()) {
			response.getWriter().write(new JSONObject()
					.put("userId", optionalUser.get().getId())
					.put("role", optionalUser.get().getRole())
					.toString());
			response.addHeader("Access-Control-Expose-Headers", "Authorization");
			response.addHeader("Access-Control-Allow-Headers", "Authorization,X-PINGOTHER,Origin,"+"X-Requested-With,Content-Type,Accept,X-Custom-header");
			response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
		}
	}
	
	@PostMapping("/sign-up")
	public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest) {
		if(authService.hasUserWithEmail(signupRequest.getEmail())) {
			return new ResponseEntity<>("User already exists",HttpStatus.NOT_ACCEPTABLE);
		}
		UserDto userDto = authService.createUser(signupRequest);
		return new ResponseEntity<>(userDto,HttpStatus.OK);
	}
	
	@GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
		if (userDetails == null) {
	        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	    }
        // Call the service to get the user profile
		UserDto userdto = authService.getUserProfile(userDetails);
		return new ResponseEntity<>(userdto,HttpStatus.OK);
    }
	
//	@GetMapping("/profile")
//    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String token) {
//		String email = jwtUtil.extractUsername(token.substring(6));
//        // Extract the user profile using the token
//        User user = authService.getUserProfile(email);
//
//        // Return the user profile data
//        return ResponseEntity.ok(user);
//    }
	
//	@GetMapping("/get_user_byid/{id}")
//	public ResponseEntity<UserDto> getUsersById(@PathVariable("id") Long id) {
//		UserDto productdto = authService.getUserById(id);
//		return new ResponseEntity<UserDto>(productdto, HttpStatus.OK);
//	}
		
//	@PutMapping("/update_user/{userId}")
//	public ResponseEntity<User> updateUserDetails(@PathVariable("userId") long userid, @RequestBody User userdetails) {
//		User user = userservice.addUserData(userdetails);
//		return new ResponseEntity<User>(user,HttpStatus.ACCEPTED);
//	}
}
