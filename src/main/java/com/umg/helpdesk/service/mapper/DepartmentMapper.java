package com.umg.helpdesk.service.mapper;

import org.springframework.stereotype.Component;

import com.umg.helpdesk.model.Department;
import com.umg.helpdesk.rest.gen.dto.DepartmentDto;

@Component
public class DepartmentMapper {

	public DepartmentDto toDto(Department entity) {
		DepartmentDto dto = new DepartmentDto();
		dto.setDescription(entity.getDescription());
		dto.setName(entity.getName());
		
		if (entity.getDepartmentId() != null)
			dto.setId(entity.getDepartmentId().toString());
		
		return dto;
	}
	
	public Department toEntity(DepartmentDto dto) {
		Department entity = new Department();
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		
		if (dto.getId() != null)
			entity.setDepartmentId(Long.parseLong(dto.getId()));
		
		return entity;
	}
	
}
