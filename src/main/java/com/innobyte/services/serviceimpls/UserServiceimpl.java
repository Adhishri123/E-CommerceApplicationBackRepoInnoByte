package com.innobyte.services.serviceimpls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innobyte.services.repositories.UserRepository;
import com.innobyte.services.services.UserService;

@Service
public class UserServiceimpl implements UserService {
 
	@Autowired
	UserRepository userrepository;
}
