package com.interfac.usermanager.user.services;

import java.util.List;

import com.interfac.usermanager.user.controllers.UserController;
import com.interfac.usermanager.user.model.User;
import com.interfac.usermanager.user.repositories.UserRepository;
import com.interfac.usermanager.user.validation.UsernameExistsException;

/**
 * Contains methods that works as adapter between {@link UserController} and {@link UserRepository}
 * <p> It has seven methods for: adding/updating, deleting, retrieving a User by
 * ID, username, listing all users in the database, and validating the existence of a username. </p>
 *  
 *  @author Ali Abdalla 
 * 
 *
 */
public interface UserService {
	
	
	
	/**
	 * Responsible for registering a new user, first checks if the user already exists, if not
	 * it retrieves role objects, and checks <code>isAdmin</code> attribute in the user and sets the <code>roles</code> 
	 * accordingly, then calls the <code>save()</code> method on <code>userRepository</code> DAO
	 * 
	 * @param user
	 * @throws UsernameExistsException with a custom message
	 */
	void registerUser(User user) throws UsernameExistsException;

	/**
	 * handles the user deletion service
	 * @param userId
	 */
	void deleteUser(long userId);

	/**
	 * responsible for providing a List of all users in the database. 
	 * @return List of User objects
	 */
	List<User> listUsers();

	/**
	 * Provides a user object given the <code>userId</code>.
	 * @param userId
	 * @return User object
	 */
	User getUserById(long userId);

	/**
	 * Provides a single user object given the <code>username</code>.
	 * 
	 * @param username must not be null
	 * @return a single user object
	 */
	User getUserByUserName(String username);

	/**
	 * checks if a user with a given <code>username</code> given exists in the database or not.
	 * 
	 * @param username
	 * @return true if users exists, false if not.
	 */
	boolean userNameExists(String username);

	/**
	 * sets the roles according to the isAdmin property in the user object. 
	 * Then updates the user in the database.
	 * 
	 * This method is seperate from the registerUser() method because it the userNameExists will fail for 
	 * editing operations.
	 * @param user
	 */
	void editUser(User user);
	
}