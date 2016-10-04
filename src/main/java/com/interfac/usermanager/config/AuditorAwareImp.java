package com.interfac.usermanager.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * This class implements the {@link AuditorAware} interface. It exposes the current user's username to the 
 * for JPA auditing @{@link CreatedBy} and @{@link LastModifiedBy} annotations.
 * 
 * @author Ali Abdalla
 *
 */
@Configuration
public class AuditorAwareImp implements AuditorAware<String> {
	

	/* (non-Javadoc)
	 * @see org.springframework.data.domain.AuditorAware#getCurrentAuditor()
	 */
	@Override
	public String getCurrentAuditor() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		
	    if (auth == null || !auth.isAuthenticated()) {
	        return null;
	    }

	    return auth.getName();
	}

}
