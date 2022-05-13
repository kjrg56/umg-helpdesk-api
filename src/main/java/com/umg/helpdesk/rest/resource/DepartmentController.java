package com.umg.helpdesk.rest.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.umg.helpdesk.rest.gen.dto.DepartmentCreationDto;
import com.umg.helpdesk.rest.gen.dto.DepartmentDto;
import com.umg.helpdesk.rest.gen.dto.DepartmentUpdateDto;
import com.umg.helpdesk.rest.gen.spec.DepartmentsApi;
import com.umg.helpdesk.service.IDepartmentService;

@RestController
public class DepartmentController implements DepartmentsApi {

	@Autowired
	private IDepartmentService departmentService;
	
	@Override
	public ResponseEntity<DepartmentDto> createDepartment(@Valid @RequestBody(required = true) DepartmentCreationDto departmentCreationDto) {
		DepartmentDto department = departmentService.createDepartment(departmentCreationDto);
		return new ResponseEntity<DepartmentDto>(department, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable(name = "id", required = true) String id) {
		DepartmentDto department = departmentService.getDepartmentById(id);
		if (department == null)
			return ResponseEntity.notFound().build();
		
		return new ResponseEntity<DepartmentDto>(department, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<DepartmentDto>> listDepartments(@Valid @RequestParam(value = "offset", required = false) Integer offset, @Valid @RequestParam(value = "limit", required = false) Integer limit) {
		List<DepartmentDto> list = departmentService.listDepartments(offset, limit);
		return new ResponseEntity<List<DepartmentDto>>(list, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<DepartmentDto> updateDepartment(@PathVariable(name = "id", required = true) String id, @Valid @RequestBody(required = true) DepartmentUpdateDto departmentUpdateDto) {
		DepartmentDto department = departmentService.updateDepartment(id, departmentUpdateDto);
		return new ResponseEntity<DepartmentDto>(department, HttpStatus.OK);
	}

}
