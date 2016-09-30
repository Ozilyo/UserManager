package com.interfac.usermanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

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
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	public void test(){
		User root = new User("root", "root", "toor");
		root.setUserName("root");
		repo.save(root);
	}
	
	@Bean
    AuditorAware<User> auditorProvider() {
        return new AuditorAwareImp();
    }
}
