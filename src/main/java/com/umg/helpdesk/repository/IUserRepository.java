package com.umg.helpdesk.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umg.helpdesk.model.User;

public interface IUserRepository extends JpaRepository<User, Long> {

	Optional<User> getFirstByUsername(String username);
	
}
