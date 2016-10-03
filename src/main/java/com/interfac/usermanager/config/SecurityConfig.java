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
//				.withUser("root").password("root").roles("ADMIN");
				.authoritiesByUsernameQuery("select user.username, role.name from role, user "
						+ "inner join users_roles on users_roles.role_id "
						+ "where user.username=? and users_roles.user_id=user.userid");
				
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/login*").anonymous()
				.antMatchers("/users").hasAuthority("ROLE_USER")
				.anyRequest().authenticated()
			  .and()
			.formLogin()
				.loginPage("/login")
		        .defaultSuccessUrl("/",true)
		        
			  .and()
			.logout()
				.logoutSuccessUrl("/")
			  .and()
			.authorizeRequests()
				.anyRequest().authenticated();
		
		http.csrf().disable();
	}
	
}


//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.context.SecurityContextHolder;

//@EnableAutoConfiguration
//@ComponentScan
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
//public class SampleSecureApplication implements CommandLineRunner {
//
//	@Autowired
//	private SampleService service;
//
//	@Override
//	public void run(String... args) throws Exception {
//		SecurityContextHolder.getContext()
//				.setAuthentication(new UsernamePasswordAuthenticationToken("user", "N/A",
//						AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER")));
//		try {
//			System.out.println(this.service.secure());
//		}
//		finally {
//			SecurityContextHolder.clearContext();
//		}
//	}
//
//	public static void main(String[] args) throws Exception {
//		SpringApplication.run(SampleSecureApplication.class, "--debug");
//	}
//
//}