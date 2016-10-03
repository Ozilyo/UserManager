package com.interfac.usermanager.util;

import org.springframework.beans.factory.annotation.Autowired;

import com.interfac.usermanager.user.model.User;
import com.interfac.usermanager.user.repositories.UserRepository;

public class ValidateUserExistance {
	
	@Autowired
	private UserRepository userRepository;
	
	public boolean usernameExists(String username){
		User user = userRepository.findByUserName(username);
		if (user != null) {
			return true;
		} else {
			return false;
		}
	}
}
