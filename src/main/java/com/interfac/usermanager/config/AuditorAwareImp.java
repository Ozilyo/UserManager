package com.interfac.usermanager.config;

import java.util.List;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.interfac.usermanager.user.model.User;
import com.interfac.usermanager.user.repositories.UserRepository;

public class AuditorAwareImp implements AuditorAware<User> {
	private UserRepository userRepository;
	@Override
	public User getCurrentAuditor() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<User> users = userRepository.findUserByUserName(auth.getName());
		System.out.println("################################################"
				+ "###############################################");
		System.out.println(users.get(0));
//		if (auth == null || !auth.isAuthenticated()) {
//		      return null;
//		}

		if (users.size() > 0) {
            return users.get(0);
        } else {
            throw new IllegalArgumentException();
        }
        
        

	}

}
