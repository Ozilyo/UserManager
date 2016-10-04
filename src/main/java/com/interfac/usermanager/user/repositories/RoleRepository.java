package com.interfac.usermanager.user.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.interfac.usermanager.user.model.Role;
import com.interfac.usermanager.user.model.User;

/**
 * This interface extends the {@link JpaRepository} interface. 
 * It handles the data access to the {@link Role} by its default methods.
 * has one extra custom methods: <code>findByName()</code>
 * 
 * 
 * @author Ali
 *
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	
	/**
	 * queries the database to find a single role object by name
	 * @param name must not be null
	 * @return Single Role object
	 */
	public Role findByName(String name);

}
