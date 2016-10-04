package com.interfac.usermanager.user.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * this class represents the <i>Role</i> entity. It contains the roles/authorities 
 * available for users.
 * 
 * Is mapped ManyToMany with both {@link User} and {@link Privilege} entities.
 * @author Ali Abdalla
 *
 */
@Entity
public class Privilege {
	/**
     * represents the privilege ID and is the primary key of the entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
	/**
	 * represents the privilege name. 
     * 
     * Example: (READ_PRIVILEGE)
	 */
	private String name;
 
    /**
     * 
     * represent a collection of {@link Role}s who have a specific Privilege name.
     */
    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;
    
    /**
     *  No arg constructor
     */
    public Privilege() {
	}

	/**
	 * Parameterized constructor
	 * @param name
	 */
	public Privilege(String name) {
		this.name = name;
	}

	/**
	 * getter method
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * setter method
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * getter method
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * setter method
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * getter method
	 * @return
	 */
	public Collection<Role> getRoles() {
		return roles;
	}

	/**
	 * setter method
	 * @param roles
	 */
	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
}
