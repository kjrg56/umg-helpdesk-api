package com.umg.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umg.helpdesk.model.User;

public interface IUserRepository extends JpaRepository<User, Long> {

}
