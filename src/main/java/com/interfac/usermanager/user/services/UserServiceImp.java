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
import com.interfac.usermanager.util.UsernameExistsException;


@Service
public class UserServiceImp implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private EntityManager entityManager;
	

	
	/* (non-Javadoc)
	 * @see com.interfac.usermanager.user.services.UserService#registerUser(com.interfac.usermanager.user.model.User)
	 */
	@Override
	public void registerUser(User user) throws UsernameExistsException{
		Role adminRole = roleRepository.findByName("ROLE_ADMIN");
		Role userRole = roleRepository.findByName("ROLE_USER");
		
		if (userNameExists(user.getUserName())) {
			throw new UsernameExistsException("A user already exists with that username: " + user.getUserName());
		}
		
		if (user.isAdmin()) {
			System.err.println(user.isAdmin());
			user.setRoles(Arrays.asList(adminRole));
		} else {
			System.err.println(user.isAdmin());
			user.setRoles(Arrays.asList(userRole));
		}
		
		userRepository.save(user);
	}
	
	/* (non-Javadoc)
	 * @see com.interfac.usermanager.user.services.UserService#deleteUser(long)
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
	 * @see com.interfac.usermanager.user.services.UserService#retrieveAudits(java.lang.Long)
	 */
	@Override
	public void retrieveAudits(Long userId) {
		
		
		AuditReader auditReader = AuditReaderFactory.get(entityManager);
		
		List<Number> revisions = auditReader.getRevisions(User.class, userId);
		
		for (Number n : revisions) {
			User user = auditReader.find(User.class, userId, n);
			System.out.printf("\t[Rev #%1$s] > %2$s\n", n, user);
			
		}
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
