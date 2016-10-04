package com.interfac.usermanager.config;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.interfac.usermanager.user.model.User;
import com.interfac.usermanager.user.repositories.UserRepository;

@Configuration
public class AuditorAwareImp implements AuditorAware<String> {
	
	@Autowired
	private UserRepository userRepository;
	@Override
	public String getCurrentAuditor() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		
	    if (auth == null || !auth.isAuthenticated()) {
	        return null;
	    }

	    return auth.getName();
	}

}
