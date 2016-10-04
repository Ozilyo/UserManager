package com.interfac.usermanager.user.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.interfac.usermanager.user.model.User;

/**
 * This interface extends the {@link JpaRepository} interface. 
 * It handles the data access to the {@link User} by its default methods.
 * has two extra custom methods: <i><code>findByUserName</code></i> and <i><code>findUserByUserName</code></i>
 * 
 * @author Ali
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	/**
	 * queries the database to find a single user object by userName
	 * @param username must not be null
	 * @return Single User object
	 */
	public User findByUserName(String username);
	/**
	 * queries the database to find user objects by userName
	 * @param username must not be null
	 * @return List of User objects
	 */
	public List<User> findUserByUserName(String username);
}
