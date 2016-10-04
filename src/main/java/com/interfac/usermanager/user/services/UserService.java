package com.interfac.usermanager.user.services;

import java.util.List;

import com.interfac.usermanager.user.model.User;
import com.interfac.usermanager.util.UsernameExistsException;

public interface UserService {

	void registerUser(User user) throws UsernameExistsException;

	void deleteUser(long userId);

	List<User> listUsers();

	User getUserById(long userId);

	User getUserByUserName(String username);

	void retrieveAudits(Long userId);

	boolean userNameExists(String username);

	void editUser(User user);

}