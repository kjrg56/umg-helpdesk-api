package com.umg.helpdesk.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.umg.helpdesk.model.Department;
import com.umg.helpdesk.repository.IDepartmentRepository;
import com.umg.helpdesk.rest.gen.dto.DepartmentCreationDto;
import com.umg.helpdesk.rest.gen.dto.DepartmentDto;
import com.umg.helpdesk.rest.gen.dto.DepartmentUpdateDto;
import com.umg.helpdesk.service.IDepartmentService;
import com.umg.helpdesk.service.mapper.DepartmentMapper;
import com.umg.helpdesk.service.utils.ModelUtils;

@Service
public class DepartmentServiceImpl implements IDepartmentService {
	
	@Autowired
	private IDepartmentRepository departmentRepository;

	@Autowired
	private DepartmentMapper departmentMapper;
	
	@Override
	public DepartmentDto createDepartment(DepartmentCreationDto department) {
		Department depto = new Department();
		depto.setName(department.getName());
		depto.setDescription(department.getDescription());
		
		depto = departmentRepository.save(depto);
		return departmentMapper.toDto(depto);
	}

	@Override
	public DepartmentDto updateDepartment(String departmentId, DepartmentUpdateDto department) {
		Optional<Department> opDepto = departmentRepository.findById(Long.parseLong(departmentId));
		if (!opDepto.isPresent())
			return null;
		
		Department depto = opDepto.get();
		depto.setName(department.getName());
		depto.setDescription(department.getDescription());
		
		depto = departmentRepository.save(depto);
		return departmentMapper.toDto(depto);
	}

	@Override
	public DepartmentDto getDepartmentById(String departmentId) {
		Optional<Department> opDepto = departmentRepository.findById(Long.parseLong(departmentId));
		if (opDepto.isPresent())
			return departmentMapper.toDto(opDepto.get());
			
		return null;
	}

	@Override
	public List<DepartmentDto> listDepartments(Integer offset, Integer limit) {
		int pageSize = ModelUtils.getPageLimit(limit);
		int pageNumber = ModelUtils.getPageNumber(offset, limit);
		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Direction.ASC, "name"));
		return departmentRepository.findAll(pageable).getContent().stream()
				.map(departmentMapper::toDto)
				.collect(Collectors.toList());
	}

}
