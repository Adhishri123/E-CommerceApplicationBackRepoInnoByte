package com.innobyte.services.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.innobyte.services.dto.SignupRequest;
import com.innobyte.services.dto.UserDto;
import com.innobyte.services.enums.UserRole;
import com.innobyte.services.models.User;
import com.innobyte.services.repositories.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public UserDto createUser(SignupRequest signupRequest) {
		User user = new User();
		user.setEmail(signupRequest.getEmail());
		user.setName(signupRequest.getName());
		user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
		user.setRole(UserRole.CUSTOMER);
		User createdUser = userRepository.save(user); 
		UserDto userDto = new UserDto();
		userDto.setId(createdUser.getId());
		return userDto;
	}
	
	public Boolean hasUserWithEmail(String email) {
		return userRepository.findFirstByEmail(email).isPresent();
	}
	@PostConstruct
	public void createAdminAccount() {
		User adminAccount = userRepository.findByRole(UserRole.ADMIN);
		if(null == adminAccount) {
			User user = new User();
			user.setEmail("admin1@gmail.com");
			user.setName("Admin");
			user.setRole(UserRole.ADMIN);
			user.setPassword(new BCryptPasswordEncoder().encode("admin@1"));
			user.setAddress("Chinchwadgaon,Pune");
			userRepository.save(user);
		}
	}
}
