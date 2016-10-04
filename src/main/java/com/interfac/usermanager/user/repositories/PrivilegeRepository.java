package com.interfac.usermanager.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.interfac.usermanager.user.model.Privilege;
import com.interfac.usermanager.user.model.Role;

/**
 * This interface extends the {@link JpaRepository} interface. 
 * It handles the data access to the {@link Privilege} by its default methods.
 * has one extra custom methods: <code>findByName()</code>
 * 
 * 
 * @author Ali
 *
 */
@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
	
	/**
	 * queries the database to find a single {@link Privilege} object by name
	 * @param name must not be null
	 * @return Single Privilege object
	 */
	public Privilege findByName(String name);
}
