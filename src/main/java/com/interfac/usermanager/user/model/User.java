package com.interfac.usermanager.user.model;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {
	private long userId;
	private String userName;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String password;
	private Date BirthDate;
	private boolean isAdmin;
	
	@CreatedDate
	private Date dateCreated;
	
	
	
	private User latestModifier;
	
	
	private Set<User> usersChanged = new HashSet<User>();
	
	@LastModifiedDate
	private Date dateModified;
	
	
	@Column(name="userid")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
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
	
	
	
	@Column(name="is_admin")
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	
	@Temporal(TemporalType.DATE)
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

	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="modified_by")
	@LastModifiedBy
	public User getLatestModifier() {
		return latestModifier;
	}
	public void setLatestModifier(User latestModifier) {
		this.latestModifier = latestModifier;
	}
	
	
	@Column(name="last_modified")
	public Date getDateModified() {
		return dateModified;
	}
	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
	
	
	
	@OneToMany(mappedBy="latestModifier")
	public Set<User> getUsersChanged() {
		return usersChanged;
	}
	public void setUsersChanged(Set<User> usersChanged) {
		this.usersChanged = usersChanged;
	}
	
	
	public User(){
		
	}
	
	public User(String firstName, String lastName, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}
	
	
	
}
