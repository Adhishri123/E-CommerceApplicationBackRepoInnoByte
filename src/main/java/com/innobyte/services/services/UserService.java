package com.innobyte.services.services;

import com.innobyte.services.models.User;

public interface UserService {

	public User addUserData(User userdetails);

//	public List<User> getAllUser();

	public User getByIdUser(Long uid);

	public User loginUser(String useremailid, String password);

}

