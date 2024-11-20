package com.innobyte.services.services.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.innobyte.services.dto.SignupRequest;
import com.innobyte.services.dto.UserDto;
import com.innobyte.services.enums.OrderStatus;
import com.innobyte.services.enums.UserRole;
import com.innobyte.services.models.Order;
import com.innobyte.services.models.Product;
import com.innobyte.services.models.User;
import com.innobyte.services.repositories.OrderRepository;
import com.innobyte.services.repositories.UserRepository;
import com.innobyte.services.utils.JwtUtil;

import jakarta.annotation.PostConstruct;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private OrderRepository orderRepository;
//	@Autowired
//	private JwtUtil jwtUtil;
	
	public UserDto createUser(SignupRequest signupRequest) {
		User user = new User();
		user.setEmail(signupRequest.getEmail());
		user.setName(signupRequest.getName());
		user.setAddress(signupRequest.getAddress());
		user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
		user.setRole(UserRole.CUSTOMER);
		User createdUser = userRepository.save(user); 
		
		Order order = new Order();
		   order.setTotalAmount(0L);
		   order.setUser(createdUser);
		   order.setOrderStatus(OrderStatus.Pending);
		 orderRepository.save(order);
		
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

//	@Override
//	public User getUserProfile(String email) {
//		return userRepository.findByEmail(email)
//              .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
//	}

	@Override
	public UserDto getUserProfile(UserDetails userDetails) {
		User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Convert User entity to UserDto
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setAddress(user.getAddress());
        userDto.setUserRole(user.getRole());

        return userDto;
	}

//	@Override
//	public UserDto getUserById(Long id) {
//		Optional<User> optionalProduct = userRepository.findById(id);
//		if (optionalProduct.isPresent()) {
//			return optionalProduct.get().getDto();
//		} else {
//			return null;
//		}
//	}
	
}
