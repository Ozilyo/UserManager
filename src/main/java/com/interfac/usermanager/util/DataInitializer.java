package com.interfac.usermanager.util;

import java.util.Arrays;
import java.util.Collection;
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
import com.interfac.usermanager.user.repositories.PrivilegeRepository;
import com.interfac.usermanager.user.repositories.RoleRepository;
import com.interfac.usermanager.user.repositories.UserRepository;
import com.interfac.usermanager.user.services.UserServiceImp;

@Component
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {
	

    boolean alreadySetup = false;
 
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PrivilegeRepository privilegeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    UserServiceImp userService;
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
 
        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        User user = new User();
        user.setUserName("root");
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setPassword("root");
        user.setMatchingPassword("root");
        user.setEmail("test@test.com");
        user.setIsAdmin(false);
        user.setEnabled(true);
//        userRepository.save(user);
        try {
			userService.registerUser(user);
		} catch (UsernameExistsException e) {
			e.printStackTrace();
		}
        alreadySetup = true;
    }
 
    @Transactional
    private Privilege createPrivilegeIfNotFound(String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }
 
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


