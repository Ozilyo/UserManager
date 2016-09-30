package com.interfac.usermanager.user.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interfac.usermanager.user.model.User;
import com.interfac.usermanager.user.repositories.UserRepository;


@Service
public class UserServiceImp {
	
	@Autowired
	private UserRepository userRepository;
	
	public void registerUser(User user){
		userRepository.save(user);
	}
	
	public void deleteUser(long userId){
		userRepository.delete(userId);
	}
	
	public List<User> listUsers(){
		return userRepository.findAll();
	}
	
	public User getUserById(long userId) {
		return userRepository.findOne(userId);
	}
	
}
