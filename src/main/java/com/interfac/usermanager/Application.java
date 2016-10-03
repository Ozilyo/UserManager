package com.interfac.usermanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.TemplateResolver;

import com.interfac.usermanager.config.AuditorAwareImp;
import com.interfac.usermanager.user.model.User;
import com.interfac.usermanager.user.repositories.UserRepository;


//@ComponentScan
//@EnableAutoConfiguration
//@EnableWebMvc
@SpringBootApplication
@EnableJpaAuditing
public class Application {
	@Autowired
	UserRepository repo;
//	@Autowired
//	TemplateResolver templateResolver;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new StandardPasswordEncoder();
		
	}
	
//	 @Bean
//	 public SpringTemplateEngine templateEngine() {
//	     SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//	     templateEngine.setTemplateResolver(templateResolver);
////	     templateEngine.addDialect(new TilesDialect());
//	     templateEngine.addDialect(new SpringSecurityDialect());
//	     return templateEngine;
//	 }
	
	
}
