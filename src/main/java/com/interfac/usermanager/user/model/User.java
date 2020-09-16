package com.interfac.usermanager.user.model;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.interfac.usermanager.user.validation.PasswordsMatch;
import com.interfac.usermanager.user.validation.PasswordsMatchValidator;

/**
 *  This class represents the <i>User</i> entity.
 *  
 * This class is:
 * <ul>
 * 		<li>Mapped to the <i>user</i> table in the database.</li>
 * 		<li>Audited by JPA auditing on the fields <code>createdDate</code>, <code>modifiedDate</code> and <code>latestModifier</code>, as annotated @EntityListeners</li>
 * 		
 * 		<li>annotated with the custom @PasswordsMatch for validating that the password field matches the matchingPassword field
 * 			@see {@link PasswordsMatch} and {@link PasswordsMatchValidator}</li>
 * </ul>
 * 
 * 
 * @author Ali Abdalla
 *
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@PasswordsMatch
public class User {
	
	/**
	 *Represents the user's ID property and the primary key 
	 */
	private long userId;
	
	
	/**
	 * Represents the userName property.
	 * Is mapped to the <i>username</i> column in the database.
	 * 
	 */	
	@NotEmpty(message="username field should not be empty")
    @Size(min=2, max=15, message="{username.size}")
	private String userName;
	 
	/**
	 * Represents the firstName property.
	 * Is mapped to the <i>first_name</i> column in the database.
	 * 
	 */

	@NotEmpty(message="First Name field should not be empty")
    @Size(min=2, max=30, message="{firstName.size}")
	private String firstName;


	/**
	 * Represents the lastName property.
	 * Is mapped to the <i>last_name</i> column in the database.
	 * 
	 */
	@NotEmpty(message="Last Name field should not be empty")
    @Size(min=2, max=30, message="{lastName.size}")
	private String lastName;

	/**
	 * Represents the user's email address.
	 * Is mapped to the <i>email</i> column in the database.
	 * 
	 */
	@NotEmpty(message="email field should not be empty}")
	@Email
	private String email;

	/**
	 * Represents the user's phone number.
	 * Is mapped to the <i>phone</i> column in the database.
	 * 
	 */
	@NotEmpty(message="phone field should not be empty")
	@Pattern(regexp="\\b\\d{3}[-.]?\\d{3}[-.]?\\d{4}\\b", message="{phone.pattern}")
	private String phone;

	/**
	 * Represents the password property.
	 * Is mapped to the <i>password</i> column in the database.
	 * can be at least 6 charachters long.
	 */
	@NotEmpty(message="password field should not be empty")
    @Size(min=6, message="{password.size}")
	private String password;

	/**
	 * Represents the <i>matchingPassword</i> property.
	 * Is mapped to the <i>matching_Password</i> column in the database.
	 * used in the @PasswordsMatch validation annotation to validate matching passwords
	 * 
	 */
	private String matchingPassword;


	/**
	 * Represents the boolean isAdmin property.
	 * Is mapped to the <i>is_admin</i> column in the database.
	 * is <code>true</code> if the user has <i>ADMIN</i> role, <code>false</code> otherwise.
	 */
	private boolean isAdmin;
	

	/**
	 * Represents the date and time the user was created.
	 * Is mapped to the <i>date_created</i> column in the database.
	 * Is filled automatically by JPA Spring data auditing.
	 * 
	 */
	@CreatedDate
	private Date dateCreated;

	/**
	 * Represents the state of the user's account, if enabled or disabled.
	 * Is mapped to the <i>is_enabled</i> column in the database.
	 * 
	 */
	private boolean isEnabled;
	
	/**
	 * Represents the <code>username</code> of the last user that modified this specific instance of <code>User</code>.
	 * Is mapped to the <i>modified_by</i> column in the database.
	 * Is filled automatically by JPA Spring data auditing.
	 * 
	 */
	@LastModifiedBy
	private String latestModifier;
	
	/**
	 * Represents the date and time for the most recent modification.
	 * Is mapped to the <i>date_modified</i> column in the database.
	 * Is filled automatically by JPA Spring data auditing.
	 * 
	 */	
	@LastModifiedDate
	private Date dateModified;
	
	
	/**
	 * A collection of authorities the user has.
	 */
	private Collection<Role> roles;
	
	
	/**
	 * getter for the userId field
	 * 
	 * @return userId
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="userid")
	public long getUserId() {
		return userId;
	}
	/**
	 * Setter for the userId field
	 * 
	 * @param id 
	 */
	public void setUserId(long id) {
		this.userId = id;
	}
	
	
	/**
	 * Getter for the userName field
	 * @return userName
	 */
	@Column(name="username")
	public String getUserName() {
		return userName;
	}
	/**
	 * Setter for the userName field
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	/**
	 * Getter for the firstName field
	 * @return firstName
	 */
	@Column
	public String getFirstName() {
		return firstName;
	}
	/**
	 * Setter for the firstName field
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	
	
	/**
	 * Getter for the lastName field
	 * @return
	 */
	@Column
	public String getLastName() {
		return lastName;
	}
	/**
	 * Setter for the lastName field
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
	/**
	 * Getter for the email field
	 * @return email
	 */
	@Column
	public String getEmail() {
		return email;
	}
	/**
	 * Setter for the email field
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	/**
	 * Getter for the phone field
	 * @return phone
	 */
	@Column
	public String getPhone() {
		return phone;
	}
	/**
	 * Setter for the phone field
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	
	/**
	 * 
	 * Getter for the password field
	 * @return password
	 */
	@Column
	public String getPassword() {
		return password;
	}
	/**
	 * Setter for the password field
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	/**
	 * Getter for the matchingPassword field
	 * @return matchingPassword
	 */
	public String getMatchingPassword() {
		return matchingPassword;
	}
	/**
	 * Setter for the matchingPassword field
	 * @param matchingPassword
	 */
	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}
	
	
	/**
	 * Getter for the isAdmin field, for spring framework default use.
	 * @return isAdmin
	 */
	@Column(name="is_admin")
	public boolean getIsAdmin() {
		return isAdmin;
	}
	/**
	 * Setter for the isAdmin field
	 * @param isAdmin
	 */
	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	

	/**
	 * Getter for the isAdmin field
	 * @return isAdmin
	 */
	public boolean isAdmin() {
		return isAdmin;
	}
	/**
	 * Setter for the phone field
	 * @param isAdmin
	 */
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	
	/**
	 * Getter for the dateCreated field
	 * @return dateCreated
	 */
	@Column(name="date_created")
	public Date getDateCreated() {
		return dateCreated;
	}
	/**
	 * Setter for the dateCreated field
	 * @param createdDated
	 */
	public void setDateCreated(Date createdDated) {
		this.dateCreated = createdDated;
	}

	
	/**
	 * Getter for the latestModifier field
	 * @return latestModifier
	 */
	@Column(name="modified_by")
	public String getLatestModifier() {
		return latestModifier;
	}
	/**
	 * setter for the latestModifier field
	 * @param latestModifier
	 */
	public void setLatestModifier(String latestModifier) {
		this.latestModifier = latestModifier;
	}
	
	
	/**
	 * getter for the dateModified field
	 * @return dateModified
	 */
	@Column
	public Date getDateModified() {
		return dateModified;
	}
	/**
	 * setter for the dateModified field
	 * @param dateModified
	 */
	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
	
	
	/**
	 * getter for the isEnabled field
	 * @return isEnabled
	 */
	public boolean isEnabled() {
		return isEnabled;
	}
	/**
	 * setter for the isEnabled field
	 * @param isEnabled
	 */
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	
	/**
	 * getter for the roles field.
	 * mapped ManyToMany to the <i>roles</i> table.
	 * @return roles
	 */
	@ManyToMany
	@JoinTable( 
		name = "users_roles", 
		joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"),
		inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	
	public Collection<Role> getRoles() {
		return roles;
	}
	/**
	 * setter for the roles field
	 * @param roles
	 */
	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
	
	
	
	
	
	/**
	 * no arg constructor
	 */
	public User(){
		
	}
	
	/**
	 * Parameterized constructor
	 * @param firstName
	 * @param lastName
	 * @param password
	 */
	public User(String firstName, String lastName, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}
	
	
	
	
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", phone=" + phone + ", password=" + password + ", matchingPassword="
				+ matchingPassword + ", isAdmin=" + isAdmin + ", dateCreated="
				+ dateCreated + ", isEnabled=" + isEnabled + ", latestModifier=" + latestModifier + ", dateModified="
				+ dateModified + ", roles=" + roles + "]";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateCreated == null) ? 0 : dateCreated.hashCode());
		result = prime * result + ((dateModified == null) ? 0 : dateModified.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + (isAdmin ? 1231 : 1237);
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((latestModifier == null) ? 0 : latestModifier.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + (int) (userId ^ (userId >>> 32));
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
		User other = (User) obj;
		if (dateCreated == null) {
			if (other.dateCreated != null)
				return false;
		} else if (!dateCreated.equals(other.dateCreated))
			return false;
		if (dateModified == null) {
			if (other.dateModified != null)
				return false;
		} else if (!dateModified.equals(other.dateModified))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (isAdmin != other.isAdmin)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (latestModifier == null) {
			if (other.latestModifier != null)
				return false;
		} else if (!latestModifier.equals(other.latestModifier))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (userId != other.userId)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	
	
	
	
}
