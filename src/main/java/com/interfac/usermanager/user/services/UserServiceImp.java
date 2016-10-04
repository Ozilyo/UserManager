package com.interfac.usermanager.user.services;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interfac.usermanager.user.model.Role;
import com.interfac.usermanager.user.model.User;
import com.interfac.usermanager.user.repositories.RoleRepository;
import com.interfac.usermanager.user.repositories.UserRepository;
import com.interfac.usermanager.user.validation.UsernameExistsException;


/**
 * This class is an implementation for the {@link UserService} interface.
 * 
 * @author Ali
 *
 * @see UserService
 */
@Service
public class UserServiceImp implements UserService {

	/**
	 * This instance is Autowired to the {@link UserRepository} interface. handles user data access.
	 */
	@Autowired
	private UserRepository userRepository;

	/**
	 * This instance is Autowired to the {@link RoleRepository} interface. handles role data access.
	 */
	@Autowired
	private RoleRepository roleRepository;

	

	
	/* (non-Javadoc)
	 * @see com.interfac.usermanager.user.services.UserService#registerUser(com.interfac.usermanager.user.model.User)
	 */
	
	@Override
	public void registerUser(User user) throws UsernameExistsException{
		
		if (userNameExists(user.getUserName())) {
			throw new UsernameExistsException("A user already exists with that username: " + user.getUserName());
		}
		
		Role adminRole = roleRepository.findByName("ROLE_ADMIN");
		Role userRole = roleRepository.findByName("ROLE_USER");
		
		if (user.isAdmin()) {
			user.setRoles(Arrays.asList(adminRole));
		} else {
			user.setRoles(Arrays.asList(userRole));
		}
		
		userRepository.save(user);
	}
	
	/* (non-Javadoc)
	 * @see com.interfac.usermanager.user.services.UserService#deleteUser(long)
	 */
	/*
	 */
	@Override
	public void deleteUser(long userId){
		userRepository.delete(userId);
	}
	
	/* (non-Javadoc)
	 * @see com.interfac.usermanager.user.services.UserService#listUsers()
	 */
	@Override
	public List<User> listUsers(){
		return userRepository.findAll();
	}
	
	/* (non-Javadoc)
	 * @see com.interfac.usermanager.user.services.UserService#getUserById(long)
	 */
	@Override
	public User getUserById(long userId) {
		return userRepository.findOne(userId);
	}
	
	/* (non-Javadoc)
	 * @see com.interfac.usermanager.user.services.UserService#getUserByUserName(java.lang.String)
	 */
	@Override
	public User getUserByUserName(String username) {
		return userRepository.findByUserName(username);
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.interfac.usermanager.user.services.UserService#userNameExists(java.lang.String)
	 */
	@Override
	public boolean userNameExists(String username){
		List<User> users = userRepository.findUserByUserName(username);
		User user = null;
		if (users.isEmpty()) {
			return false;
		} else {
			user = users.get(0);
		}
		if (user != null) {
			return true;
		} else {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.interfac.usermanager.user.services.UserService#editUser(com.interfac.usermanager.user.model.User)
	 */
	@Override
	public void editUser(User user) {
		Role adminRole = roleRepository.findByName("ROLE_ADMIN");
		Role userRole = roleRepository.findByName("ROLE_USER");
		
		if (user.getIsAdmin()) {
			user.setRoles(Arrays.asList(adminRole));
		} else {
			user.setRoles(Arrays.asList(userRole));
		}
		userRepository.save(user);
	}
	
}
