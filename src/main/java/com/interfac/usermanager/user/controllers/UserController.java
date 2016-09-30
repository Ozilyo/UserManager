package com.interfac.usermanager.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.interfac.usermanager.user.model.User;
import com.interfac.usermanager.user.services.UserServiceImp;

@Controller
@RequestMapping(value = "/UserManager")
public class UserController {
	@Autowired
	private UserServiceImp userService; // change type to userService interface
	
	@RequestMapping(value = "", method=RequestMethod.GET)
	public String welcome(){
		return "welcome";
	}
	
	
	@RequestMapping(value = "/users")
	public String usersList(){
		return "";
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public String login(@PathVariable int userId, Model model){
		
		return "welcome";
	}
	
	
	//for testing only! REMOVE LATER!!
	@RequestMapping(value="/test")
	public String test(Model model){
		User ali = new User("Ali", "Abdalla", "ali");
		ali.setAdmin(true);
		ali.setEmail("ali@hotmail.com");
		ali.setPhone("0123456789");
		ali.setUserName("aliabdalla");
		
		userService.registerUser(ali);
		model.addAttribute("user", new User());
		model.addAttribute("usersList", userService.listUsers());
		return "users";
	}
	
}
