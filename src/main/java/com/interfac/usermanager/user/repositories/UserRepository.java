package com.interfac.usermanager.user.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.interfac.usermanager.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	public List<User> findUserByUserName(String username);
}
