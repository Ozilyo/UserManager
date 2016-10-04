package com.interfac.usermanager.user.services;

import java.util.List;

import com.interfac.usermanager.user.model.User;
import com.interfac.usermanager.user.validation.UsernameExistsException;

public interface UserService {
	
	
	/**
	 * Responsible for registering a new user, first checks if the user already exists, if not
	 * it retrieves role objects, and checks <code>isAdmin</code> attribute in the user and sets the <code>roles</code> 
	 * accordingly, then calls the <code>save()</code> method on <code>userRepository</code> DAO
	 * 
	 * @param user
	 * @throws UsernameExistsException
	 */
	void registerUser(User user) throws UsernameExistsException;

	void deleteUser(long userId);

	List<User> listUsers();

	User getUserById(long userId);

	User getUserByUserName(String username);

	boolean userNameExists(String username);

	void editUser(User user);

}