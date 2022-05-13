package com.umg.helpdesk.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TBL_USER")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name = "USER_NAME")
	private String username;
	
	@Column(name = "PASSWD")
	private String password;
	
	@Column(name = "FULL_NAME")
	private String fullName;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "DATE_OF_BIRTH")
	private LocalDate dateOfBirth;
	
	@Column(name = "USER_ROLE")
	private UserRole userRole;
	
	@ManyToOne
	@JoinColumn(name = "DEPARTMENT_ID")
	private Department department;
	
}
