package com.interfac.usermanager.user.controllers;

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

import com.interfac.usermanager.user.model.User;
import com.interfac.usermanager.user.repositories.UserRepository;
import com.interfac.usermanager.user.services.UserService;
import com.interfac.usermanager.user.validation.UsernameExistsException;

/**
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
	 * This instance is Autowired to the {@link UserService} interface.
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * Handle <a><i>"/"</i></a> request page. Returns a <code>welcomeMessage</code> variable 
	 * populated with the currently logged in username extracted from the {@link Authentication} object.
	 * @param model caries the <code>welcomeMessage</code> var to the view
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
	 * @param model caries the <code>welcomeMessage</code> var to the view
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/users")
	public String listUsers(Model model){
		model.addAttribute("usersList", userService.listUsers());
//		model.addAttribute("user", new User());
		return "users";
	}
	
	
	
	
	@RequestMapping(value = "/registration")
	public String goToRegistrationPage(Model model){
		model.addAttribute("user", new User());
		return "registration_form";
	}
	
	
	
	@RequestMapping(value = "/access_denied", method=RequestMethod.GET)
	public String accessDeniedController(){
		return "access_denied";
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
