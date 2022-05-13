package com.umg.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umg.helpdesk.model.Department;

public interface IDepartmentRepository extends JpaRepository<Department, Long> {

}
