package com.interfac.usermanager.user.repositories;

import java.sql.Time;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.interfac.usermanager.user.model.Privilege;
import com.interfac.usermanager.user.model.Role;
import com.interfac.usermanager.user.model.User;
import com.interfac.usermanager.user.services.UserService;
import com.interfac.usermanager.user.validation.UsernameExistsException;

/**
 * This class implements {@link ApplicationListener} interface.
 * It's purpose is:
 *  
 * <ol> 
 * 	<li> populate the user table with an initial user <i>root</i> with default
 * 		<code>username</code> "<i>root</i>", default <code>password</code> "<i>root123</i>" and authority <i>ROLE_ADMIN</i></li>
 * 
 * 	<li> populate the {@link Role} table with values <i>ROLE_ADMIN</i> and <i>ROLE_USER</i></li>
 * 	<li> populate the {@link Privilege} table with values <i>READ_PRIVILEGE</i> and <i>WRITE_PRIVILEGE</i></li>
 * </ol>
 * 
 * @author Ali
 *
 */
@Component
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {
	

    /**
     * used to abort the <i>onApplicationEvent</i> listener if the data base is setup properly.
     */
    boolean alreadySetup = false;
 
    /**
	 * instance of the {@link RoleRepository} interface, handles data access to Role entity.
	 */
    @Autowired
    private RoleRepository roleRepository;
    /**
	 * instance of the {@link PrivilegeRepository} interface, handles data access to Privilege entity.
	 */
    @Autowired
    private PrivilegeRepository privilegeRepository;
    
    /**
	 * instance of the {@link UserService} interface, calls service methods for the User class.
	 */
    @Autowired
    private UserService userService;
    
    /** 
     * this method delegates the user registration to {@link UserService} interface so 
     * it can validate the user's existence in the database.
     * 
     * It also gives <i>READ_PRIVILEGE</i> and <i>WRITE_PRIVILEGE</i> to the <i>ROLE_ADMIN</i>, 
     * and gives <i>READ_PRIVILEGE</i> to the <i>ROLE_USER</i>.
     * 
     * if the database is already populated the <code>alreadySetup</code> exits the method
     * 
     * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
     */
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;
        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);        
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));
 
        User user = new User();
        user.setUserName("root");
        user.setFirstName("root");
        user.setLastName("root");
        user.setPhone("1234567890");
        user.setDateCreated(new Date());
        user.setPassword("root123");
        user.setMatchingPassword("root123");
        user.setEmail("test@test.com");
        user.setIsAdmin(true);
        user.setEnabled(true);
        try {
			userService.registerUser(user);
		} catch (UsernameExistsException e) {
			System.err.println("user already exists in database");
		}
        alreadySetup = true;
    }
 
    /**
     * Creates the privilege with the given <code>name</code> if it does not exist in the database.
     * @param name not null
     * @return privilege object
     */
    @Transactional
    private Privilege createPrivilegeIfNotFound(String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }
    
    
    /**
     * Creates the role with the given <code>name</code> if it does not exist in the database.
     * and sets the privileges of the role.
     * 
     * @param name not null
     * @return Role object
     */
    @Transactional
    private Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}


