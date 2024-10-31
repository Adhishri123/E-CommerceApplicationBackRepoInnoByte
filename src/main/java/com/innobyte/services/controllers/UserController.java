package com.innobyte.services.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innobyte.services.models.User;
import com.innobyte.services.services.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {
 
	@Autowired
	UserService userservice;
	
	@PostMapping("/signup_user")
	public ResponseEntity<User> registerUserDetails(@RequestBody User userdetails) {
		User user = userservice.addUserData(userdetails);
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}
	
	@PostMapping("/login_user")
	public ResponseEntity<User> loginUserDetails(@RequestParam("userEmailId") String useremailid,@RequestParam("password") String password) {
		User user = userservice.loginUser(useremailid, password);
        return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
//	@GetMapping("/get_all_users")
//	public ResponseEntity<List<User>> getAllUsersDetails() {
//		List<User> alluser = userservice.getAllUser();
//		return new ResponseEntity<List<User>>(alluser, HttpStatus.OK);
//	}
	
	@GetMapping("/getsingle_user/{userId}")
    public ResponseEntity<User> getUser(@PathVariable ("userId") Long uid) {
		User ud = userservice.getByIdUser(uid);
		return new ResponseEntity<User>(ud, HttpStatus.OK);		 
    }
		
	@PutMapping("/update_user/{userId}")
	public ResponseEntity<User> updateUserDetails(@PathVariable("userId") long userid, @RequestBody User userdetails) {
		User user = userservice.addUserData(userdetails);
		return new ResponseEntity<User>(user,HttpStatus.ACCEPTED);
	}
	
}
