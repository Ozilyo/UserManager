package com.interfac.usermanager.user.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
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
import com.interfac.usermanager.user.services.UserService;
import com.interfac.usermanager.user.validation.UsernameExistsException;

/**
 * 
 *  This class handles mapping the HTTP requests to the views after calling the {@link UserService} 
 * for processing the needed service.
 * 
 * @author Ali
 *
 * @see UserRepository
 * @see UserService
 */
@Controller
public class UserController {
	/**
	 * This instance is Autowired to the {@link UserService} interface. handles user services.
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * Handle <a><i>"/"</i></a> request page. Returns a <code>welcomeMessage</code> variable 
	 * populated with the currently logged in username extracted from the {@link Authentication} object.
	 * @param model will carry the <code>welcomeMessage</code> var to the view.
	 * @return string resolved to the view path.
	 */
	@RequestMapping(value = "/", method=RequestMethod.GET)
	public String welcome(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName();
	      
		model.addAttribute("welcomeMessage", "You are logged in as '" + name + "' ");
		return "welcome";
	}
	
	
	/**
	 * Handle <a><i>"/users"</i></a> request. Returns a model attributes 
	 * back to the view.
	 * <ul>
	 * 		<li><i>usersList : </i>list of <i>User</i> objects, 
	 * 			   sent to the view to populate the table.</li>
	 * </ul>
	 * @param model will carry the <code>usersList</code> var to the view.
	 * @return string resolved to the view path.
	 */
	@RequestMapping(value = "/users")
	public String listUsers(Model model){
		model.addAttribute("usersList", userService.listUsers());
		return "users";
	}
	
	
	
	
	/**
	 * Handles requests to "/registration", returns registration form template name.
	 * 
	 * @param model
	 * @return string resolved to the view path.
	 */
	@RequestMapping(value = "/registration")
	public String goToRegistrationPage(Model model){
		model.addAttribute("user", new User());
		return "registration_form";
	}
	
	
	
	/**
	 * Handles {@link AccessDeniedException} page. 
	 * forwards the user to the custom access_denied page.
	 * 
	 * @return string resolved to the view path.
	 */
	@RequestMapping(value = "/access_denied", method=RequestMethod.GET)
	public String accessDeniedController(){
		return "access_denied";
	}
	
	
	/**
	 * This method handles user registration and user updating. 
	 * 
	 * First it checks for validation errors, if present it returns the <i>regestration_form</i> with <code>errors</code> object. 
	 * it then checks if the operation is an edit (<code>userId</code> != 0, user not new!), if so, i calls the <code>editUser</code> service method.
	 * and checks if the user is editing his own profile or he is an <i>ADMIN</i> and is editing other users. and redirects accordingly.
	 * if the operation is a new registration, it is delegated to <code>registerUser</code> service method and user is redirected to the <i>users</i> page.
	 * 
	 * @param user
	 * @param errors 
	 * @param model
	 * @return  string resolved to the view path.
	 * @throws UsernameExistsException custom exception, handled by sending a validation error message to the <i>registration_form</i> view
	 */
	@RequestMapping(value = "/user/add", method=RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user, Errors errors,	Model model) throws UsernameExistsException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (errors.hasErrors())
			return "registration_form";
			
		if(user.getUserId() != 0){
			userService.editUser(user);
			System.err.println(auth.getName() + "  " + user.getUserName());
			if (auth.getName().equals(user.getUserName())){
				return "redirect:/home";
			} else {
				return "redirect:/users";
			}
			
		}
		try {
			userService.registerUser(user);
		} catch (UsernameExistsException e) {
			errors.reject("exists", "username already exists!");
			return "registration_form";
		}
		return "redirect:/users";
	}
	
	
	/**
	 * handles requests for user deletion. Redirects to the <i>users</i> view page
	 * only available for users with ADMIN authority
	 * @param userId
	 * @return
	 */
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/users/delete/{userId}")
	public String deleteUser(@PathVariable("userId") int userId){
		userService.deleteUser(userId);
		return "redirect:/users";
	}
	
	
	/**
	 * handles request for user editing.
	 * only available for users with ADMIN authority
	 * @param userId 
	 * @param model to be populated with the user to be edited.
	 * @return string to the registration page.
	 */
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/users/edit/{userId}")
	public String editUser(@PathVariable("userId") int userId, Model model){
		model.addAttribute("user", userService.getUserById(userId));
		return "registration_form";
	}
	
	
	/**
	 * handles requests for user with authority <i>ROLE_USER</i> or <i>ROLE_ADMIN</i> to edit his own profile. 
	 * 
	 * @param model
	 * @return registration_form view page
	 */
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@RequestMapping(value = "/user/personal/edit")
	public String editPersonal(Model model){
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("user", userService.getUserByUserName(username));
		return "registration_form";
	}
	
	
	/**
	 * handles request to display a user's profile info given the <code>userId</code>
	 * only available for users with ADMIN authority
	 * 
	 * @param userId
	 * @param model
	 * @return user_profile view
	 */
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "user/{userId}")
	public String displayUserProfile(@PathVariable("userId") int userId, Model model){
		model.addAttribute("user", userService.getUserById(userId));
		
		return "user_profile";
	}
	
	
	
	/**
	 * handles requests for the custom login page.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login")
	public String loginPage(){
		return "login";
	}
	
	
	
	/**
	 * handles request for the currently logged in user's profile page
	 * 
	 * @param model
	 */
	@RequestMapping(value = "/home")
	public String displayHomePage(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //get logged in username
		
	    model.addAttribute("user", userService.getUserByUserName(name));
	    
		return "user_profile";
	}
	
	/**
	 * handles requests to <i>"/users/search"</i> to find an employee by username.
	 * 
	 * @param username
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/users/search")
	public String searchEmployees(@RequestParam("username") String username, Model model){
		List<User> users = new ArrayList<User>(Arrays.asList(userService.getUserByUserName(username)));
		model.addAttribute("usersList", users);
		return "users";
	}
	
}
