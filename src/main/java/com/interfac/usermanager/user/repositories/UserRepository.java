package com.interfac.usermanager.user.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.interfac.usermanager.user.model.User;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
	
	public User findByUserName(String username);
	public List<User> findUserByUserName(String username);
}
