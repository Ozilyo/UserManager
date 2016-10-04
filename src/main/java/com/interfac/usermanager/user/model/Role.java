package com.interfac.usermanager.user.model;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
public class Role {
    /**
     * represents the role ID and is the primary key of the entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
    /**
     * represents the role name. 
     * 
     * Example: (ROLE_ADMIN)
     */
    private String name;
    
    /**
     * represent a collection of {@link User}s who have a specific role name.
     */
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;
 
    /**
     * represents a collection of {@link Privilege}s. Mapped ManyToMany with {@link Role}
     */
    @ManyToMany
    @JoinTable(
        name = "roles_privileges", 
        joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
    private Collection<Privilege> privileges;

    

	/**
	 * No arg constructor
	 */
	public Role() {
	
	}
    
	/**
	 * Parameterized constructor
	 * @param name
	 */
	public Role(String name) {
		this.name = name;
	}

	/**
	 * Getter method
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Setter method
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter method
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter method
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter method
	 * @return users
	 */
	public Collection<User> getUsers() {
		return users;
	}

	/**
	 * Setter method
	 * @param users
	 */
	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	/**
	 * Getter method
	 * @return privileges
	 */
	public Collection<Privilege> getPrivileges() {
		return privileges;
	}

	/**
	 * Setter method
	 * @param privileges
	 */
	public void setPrivileges(Collection<Privilege> privileges) {
		this.privileges = privileges;
	}   
	
	
	
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", users=" + users + ", privileges=" + privileges + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((privileges == null) ? 0 : privileges.hashCode());
		result = prime * result + ((users == null) ? 0 : users.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (privileges == null) {
			if (other.privileges != null)
				return false;
		} else if (!privileges.equals(other.privileges))
			return false;
		if (users == null) {
			if (other.users != null)
				return false;
		} else if (!users.equals(other.users))
			return false;
		return true;
	}
    
    
}