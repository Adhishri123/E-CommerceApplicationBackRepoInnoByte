package com.innobyte.services.services.auth;

import com.innobyte.services.dto.SignupRequest;
import com.innobyte.services.dto.UserDto;

public interface AuthService {

	UserDto createUser(SignupRequest signupRequest);
	Boolean hasUserWithEmail(String email);
}
