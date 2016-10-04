package com.interfac.usermanager.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;
	
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
				.logoutSuccessUrl("/")
			  .and()
			.exceptionHandling().accessDeniedPage("/access_denied");
		
		
		
//		http.csrf().disable();
	}
	
}

