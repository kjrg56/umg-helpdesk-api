package com.umg.helpdesk.service;

import java.util.List;

import com.umg.helpdesk.rest.gen.dto.DepartmentCreationDto;
import com.umg.helpdesk.rest.gen.dto.DepartmentDto;
import com.umg.helpdesk.rest.gen.dto.DepartmentUpdateDto;

public interface IDepartmentService {
	
	DepartmentDto createDepartment(DepartmentCreationDto department);
	
	DepartmentDto updateDepartment(String departmentId, DepartmentUpdateDto department);
	
	DepartmentDto getDepartmentById(String departmentId);
	
	List<DepartmentDto> listDepartments(Integer offset, Integer limit);
	
}
