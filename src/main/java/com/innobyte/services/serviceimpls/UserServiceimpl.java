package com.innobyte.services.serviceimpls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innobyte.services.models.User;
import com.innobyte.services.repositories.UserRepository;
import com.innobyte.services.services.UserService;

@Service
public class UserServiceimpl implements UserService {
 
	@Autowired
	UserRepository userrepository;

	@Override
	public User addUserData(User userdetails) {
		User user = userrepository.save(userdetails);
		return user;
	}

//	@Override
//	public List<User> getAllUser() {
//		
//		return userrepository.findAll();
//	}

	@Override
	public User getByIdUser(Long uid) {
		User ouser = userrepository.findByUserId(uid);
		return ouser;
	}

	@Override
	public User loginUser(String useremailid, String password) {
		User user = userrepository.findByUserEmailIdAndPassword(useremailid, password);
		return user;
	}

}
