package com.lab4.saigonwaterbus.DAO;

import com.lab4.saigonwaterbus.Model.Role;
import com.lab4.saigonwaterbus.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDao extends JpaRepository<User, Integer>{
		User findByEmail(String email);
	 User findByRole(Role role);
	 @Query("SELECT u.role FROM User u WHERE u.email = :email")
	  String findRoleByEmail(@Param("email") String email);
}
