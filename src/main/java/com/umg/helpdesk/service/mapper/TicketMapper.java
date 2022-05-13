package com.umg.helpdesk.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.umg.helpdesk.model.Ticket;
import com.umg.helpdesk.model.TicketStatus;
import com.umg.helpdesk.rest.gen.dto.TicketDto;

@Component
public class TicketMapper {

	@Autowired
	private TicketCategoryMapper ticketCategoryMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	public TicketDto toDto(Ticket entity) {
		TicketDto dto = new TicketDto();
		dto.setAssignationDate(entity.getAssignationDate());
		dto.setClosedDate(entity.getClosedDate());
		dto.setCreationDate(entity.getCreationDate());
		dto.setHours(entity.getHours());
		dto.setDescription(entity.getDescription());
		dto.setName(entity.getName());
		
		if (entity.getTicketId() != null)
			dto.setId(entity.getTicketId().toString());
		
		if (entity.getTicketCategory() != null)
			dto.setCategory(ticketCategoryMapper.toDto(entity.getTicketCategory()));
		
		if (entity.getStatus() != null)
			dto.setStatus(entity.getStatus().name());
		
		if (entity.getUserAssigned() != null)
			dto.setUserAssigned(userMapper.toDto(entity.getUserAssigned()));

		if (entity.getUserCreated() != null)
			dto.setUserCreated(userMapper.toDto(entity.getUserCreated()));
		
		return dto;
	}
	
	public Ticket toEntity(TicketDto dto) {
		Ticket entity = new Ticket();
		entity.setAssignationDate(dto.getAssignationDate());
		entity.setClosedDate(dto.getClosedDate());
		entity.setCreationDate(dto.getCreationDate());
		entity.setHours(dto.getHours());
		entity.setDescription(dto.getDescription());
		entity.setName(dto.getName());
		
		if (dto.getId() != null)
			entity.setTicketId(Long.parseLong(dto.getId()));
		
		if (dto.getCategory() != null)
			entity.setTicketCategory(ticketCategoryMapper.toEntity(dto.getCategory()));
		
		if (dto.getStatus() != null)
			entity.setStatus(TicketStatus.valueOf(dto.getStatus()));
		
		if (dto.getUserAssigned() != null)
			entity.setUserAssigned(userMapper.toEntity(dto.getUserAssigned()));

		if (dto.getUserCreated() != null)
			entity.setUserCreated(userMapper.toEntity(dto.getUserCreated()));
		
		return entity;
	}
	
}
