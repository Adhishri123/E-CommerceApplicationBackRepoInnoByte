package com.innobyte.services.services.auth;

import org.springframework.security.core.userdetails.UserDetails;

import com.innobyte.services.dto.SignupRequest;
import com.innobyte.services.dto.UserDto;
import com.innobyte.services.models.User;

public interface AuthService {

	UserDto createUser(SignupRequest signupRequest);
	Boolean hasUserWithEmail(String email);
//	UserDto getUserById(Long id);
//	User getUserProfile(String email);
	UserDto getUserProfile(UserDetails userDetails);

}
