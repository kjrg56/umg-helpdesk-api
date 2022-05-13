package com.umg.helpdesk.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.umg.helpdesk.model.User;
import com.umg.helpdesk.model.UserRole;
import com.umg.helpdesk.rest.gen.dto.UserDto;

@Component
public class UserMapper {

	@Autowired
	private DepartmentMapper departmentMapper;
	
	public UserDto toDto(User entity) {
		UserDto dto = new UserDto();
		dto.setEmail(entity.getEmail());
		dto.setFullName(entity.getFullName());
		dto.setUsername(entity.getUsername());
		dto.setDateOfBirth(entity.getDateOfBirth());
		
		if (entity.getUserId() != null)
			dto.setId(entity.getUserId().toString());
		
		if (entity.getDepartment() != null)
			dto.setDepartment(departmentMapper.toDto(entity.getDepartment()));
		
		if (entity.getUserRole() != null)
			dto.setUserRole(entity.getUserRole().name());
		
		return dto;
	}
	
	public User toEntity(UserDto dto) {
		User entity = new User();
		entity.setEmail(dto.getEmail());
		entity.setFullName(dto.getFullName());
		entity.setUsername(dto.getUsername());
		entity.setDateOfBirth(dto.getDateOfBirth());
		
		if (dto.getId() != null)
			entity.setUserId(Long.parseLong(dto.getId()));
		
		if (dto.getDepartment() != null)
			entity.setDepartment(departmentMapper.toEntity(dto.getDepartment()));
		
		if (dto.getUserRole() != null)
			entity.setUserRole(UserRole.valueOf(dto.getUserRole()));
		
		return entity;
	}
	
}
