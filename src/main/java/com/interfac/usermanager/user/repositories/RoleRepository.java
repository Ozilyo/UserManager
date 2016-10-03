package com.interfac.usermanager.user.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.interfac.usermanager.user.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
//	@Query("select a.role from UserRole a, User b where b.userName=?1 and a.userid=b.userId")
//	public List<String> findRoleByUserName(String username);
	
	public Role findByName(String name);
//	public List<String> findRoleByUsername(String username);
}
