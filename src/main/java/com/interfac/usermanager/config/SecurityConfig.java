package com.interfac.usermanager.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


/**
 * This class extends {@link WebSecurityConfigurerAdapter} and overrides two configure methods with signatures 
 * <code>configure(AuthenticationManagerBuilder auth)</code> and <code>configure(HttpSecurity http)</code> 
 * to customize the security configuration for the <i>'UserManager'</i> application.
 * @author Ali Abdalla
 *
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	/**
	 * An instance of the dataSource bean specifies database properties.
	 */
	@Autowired
	DataSource dataSource;
	
	/** 
	 * Configures the {@link AuthenticationManagerBuilder} to use jdbcAuthentication 
	 * to obtain credentials from the database.
	 * 
	 * <p>uses custom queries for the database to retrieve users credentials and authorities.</p>
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder)
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.jdbcAuthentication()
				.dataSource(dataSource).usersByUsernameQuery("select username, password, true "
						+ "from user where username=?")
				.authoritiesByUsernameQuery("select user.username, role.name from user, role "
						+ "join users_roles on (users_roles.role_id = role.id) "
						+ "where user.username=? and users_roles.user_id=user.userid");
				
	}
	
	/**
	 * Configures the {@link HttpSecurity} to:
	 * <ul> 
	 * 		<li>authorize requests matching to "/login*" to non authenticated users</li>
	 * 		<li>authorize requests matching to "/users/**" to users with ROLE_ADMIN authority only</li>
	 * 		<li>authorize any other requests to users that are authenticated (i.e. ROLE_USER or ROLE_ADMIN authority</li>
	 * 
	 * 		<li>override the default login page to "/login" request that is handled by the controller</li>
	 * 		<li>set the defaultSucessUrl to always request the root page "/" after a successful
	 * 			login, regardless of the previous page.</li>
	 * 		<li>provide logout support</li>
	 * 		<li>override CSRF's POST enforcing by making the logout request a GET request</li>
	 * 		<li>configure a custom access denied page</li>
	 * 		
	 * </ul>
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/login*").anonymous()
				.antMatchers("/users/**").hasAuthority("ROLE_ADMIN")
				.anyRequest().authenticated()
			  .and()
			.formLogin()
				.loginPage("/login")
		        .defaultSuccessUrl("/",true)
			  .and()
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			  .and()
			.exceptionHandling().accessDeniedPage("/access_denied");
	
	}
	
}

