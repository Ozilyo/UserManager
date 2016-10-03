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
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.interfac.usermanager.util.PasswordsMatch;
import com.interfac.usermanager.util.PasswordsMatchValidator;

/**
 *  This class represents the <i>User</i> entity.
 *  
 * This class is:
 * <ul>
 * 		<li>Mapped to the <i>user</i> table in the database.</li>
 * 		<li>Audited by JPA auditing on the fields <code>createdDate</code>, <code>modifiedDate</code> and <code>latestModifier</code>, as annotated @EntityListeners</li>
 * 		<li>Audited by Hibernate EnVers and user_AUD audit table is created automatically to show revisions</li>
 * 		<li>annotated with the custom @PasswordsMatch for validating that the password field matches the matchingPassword field
 * 			@see {@link PasswordsMatch} and {@link PasswordsMatchValidator}</li>
 * </ul>
 * 
 * 
 * @author Ali Abdalla
 *
 */
/**
 * @author Ali
 *
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@PasswordsMatch
public class User {
	
	/**
	 *Represents the ID property and the primary key 
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
	 * Represents the Birthdate property.
	 * Is mapped to the <i>birth_date</i> column in the database.
	 * annotated <i>temporalType.DATE</i> for easy conversion to java.sql.Date type.
	 * displays only the date (dd-mm-yyyy)
	 * 
	 */
	@Temporal(TemporalType.DATE)
	private Date BirthDate;


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
	 * A collection of roles and authorities that the user has.
	 * mapped 
	 */
	private Collection<Role> roles;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="userid")
	public long getUserId() {
		return userId;
	}
	public void setUserId(long id) {
		this.userId = id;
	}
	
	
	@Column(name="username")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	@Column
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	
	
	@Column
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
	@Column
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	@Column
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	
	@Column
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	public String getMatchingPassword() {
		return matchingPassword;
	}
	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}
	
	
	@Column(name="is_admin")
	public boolean getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	
	@Column
	public Date getBirthDate() {
		return BirthDate;
	}
	public void setBirthDate(Date birthDate) {
		BirthDate = birthDate;
	}
	
	
	
	@Column(name="date_created")
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date createdDated) {
		this.dateCreated = createdDated;
	}

	
	@Column(name="modified_by")
	public String getLatestModifier() {
		return latestModifier;
	}
	public void setLatestModifier(String latestModifier) {
		this.latestModifier = latestModifier;
	}
	
	
	@Column
	public Date getDateModified() {
		return dateModified;
	}
	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
	
	
	
//	@OneToMany(mappedBy="latestModifier")
//	@NotAudited
//	public Set<User> getUsersChanged() {
//		return usersChanged;
//	}
//	public void setUsersChanged(Set<User> usersChanged) {
//		this.usersChanged = usersChanged;
//	}
	
	
	
	
	public boolean isEnabled() {
		return isEnabled;
	}
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	
	@ManyToMany
	@JoinTable( 
		name = "users_roles", 
		joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"),
		inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	@NotAudited
	public Collection<Role> getRoles() {
		return roles;
	}
	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
	
	
	
	
	
	public User(){
		
	}
	
	public User(String firstName, String lastName, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}
	
	
	
	
	
	
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", phone=" + phone + ", password=" + password + ", matchingPassword="
				+ matchingPassword + ", BirthDate=" + BirthDate + ", isAdmin=" + isAdmin + ", dateCreated="
				+ dateCreated + ", isEnabled=" + isEnabled + ", latestModifier=" + latestModifier + ", dateModified="
				+ dateModified + ", roles=" + roles + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((BirthDate == null) ? 0 : BirthDate.hashCode());
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
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (BirthDate == null) {
			if (other.BirthDate != null)
				return false;
		} else if (!BirthDate.equals(other.BirthDate))
			return false;
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
