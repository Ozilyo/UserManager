package com.interfac.usermanager.user.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transaction;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.interfac.usermanager.user.model.User;
import com.interfac.usermanager.user.services.UserServiceImp;
import com.interfac.usermanager.util.UsernameExistsException;

@Controller

public class UserController {
	@Autowired
	private UserServiceImp userService; // change type to userService interface
	
	@RequestMapping(value = "", method=RequestMethod.GET)
	public String welcome(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	      String name = auth.getName(); //get logged in username
		model.addAttribute("welcomeMessage", "You are logged in as '" + name + "' ");
		return "welcome";
	}
	
	
	@RequestMapping(value = "/users")
	public String usersList(Model model){
		model.addAttribute("usersList", userService.listUsers());
		model.addAttribute("user", new User());
		return "users";
	}
	
	
	
	
	@RequestMapping(value = "/registration")
	public String goToRegistrationPage(Model model){
		model.addAttribute("user", new User());
		return "registration_form";
	}
	
	
	
	@RequestMapping(value = "/{userId}", method=RequestMethod.GET)
	public String login(@PathVariable Long userId, Model model){
		model.addAttribute("user", userService.getUserById(userId));
		return "home";
	}
	
	
	//for testing only! REMOVE LATER!!
	@RequestMapping(value="/test")
	public String test(Model model){
		User ali = userService.getUserById(25);
		ali.setIsAdmin(true);
		ali.setEmail("XXXXXXXXX");
		ali.setPhone("0123456789");
		ali.setUserName("XXXXXXXX");
		ali.setPassword("XXXXXX");
		
//		userService.registerUser(ali);
		model.addAttribute("user", new User());
		model.addAttribute("usersList", userService.listUsers());
		return "users";
	}
	
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/user/add", method=RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user, Errors errors,	Model model) throws UsernameExistsException{
		if (errors.hasErrors())
			return "registration_form";
			
		if(user.getUserId() != 0){
			userService.editUser(user);
			return "redirect:/users";
		}
		try {
			userService.registerUser(user);
		} catch (UsernameExistsException e) {
			errors.reject("exists", "username already exists!");
			return "registration_form";
		}
		return "redirect:/users";
	}
	
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/users/delete/{userId}")
	public String deleteUser(@PathVariable("userId") int userId){
		userService.deleteUser(userId);
		return "redirect:/users";
	}
	
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/users/edit/{userId}")
	public String editUser(@PathVariable("userId") int userId, Model model){
		model.addAttribute("user", userService.getUserById(userId));
		return "registration_form";
	}
	
	
	@RequestMapping(value = "user/{userId}")
	public String displayUserProfile(@PathVariable("userId") int userId, Model model){
		model.addAttribute("user", userService.getUserById(userId));
		
		return "user_profile";
	}
	
	
	@RequestMapping(value = "audit/{userId}")
	public String auditUser(@PathVariable("userId") Long userId, Model model){
		userService.retrieveAudits(userId);
		
		return "user_profile";
	}
	
	
	@RequestMapping(value = "/login")
	public String loginPage(){
		return "login";
	}
	
	@RequestMapping(value = "/home")
	public String displayHomePage(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //get logged in username
		
	    model.addAttribute("user", userService.getUserByUserName(name));
	    
		return "user_profile";
	}
	

//	// Error page
//	@RequestMapping("/error")
//	public String error(HttpServletRequest request, Model model) {
//		model.addAttribute("errorCode", request.getAttribute("javax.servlet.error.status_code"));
//		Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
//		String errorMessage = null;
//		if (throwable != null) {
//			errorMessage = throwable.getMessage();
//		}
//		model.addAttribute("errorMessage", errorMessage);
//		return "error.html";
//	}
	
}
