package com.umg.helpdesk.service.mapper;

import org.springframework.stereotype.Component;

import com.umg.helpdesk.model.TicketCategory;
import com.umg.helpdesk.rest.gen.dto.TicketCategoryDto;

@Component
public class TicketCategoryMapper {

	public TicketCategoryDto toDto(TicketCategory entity) {
		TicketCategoryDto dto = new TicketCategoryDto();
		dto.setDescription(entity.getDescription());
		dto.setName(entity.getName());
		
		if (entity.getTicketCategoryId() != null)
			dto.setId(entity.getTicketCategoryId().toString());
		
		return dto;
	}
	
	public TicketCategory toEntity(TicketCategoryDto dto) {
		TicketCategory entity = new TicketCategory();
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		
		if (dto.getId() != null)
			entity.setTicketCategoryId(Long.parseLong(dto.getId()));
		
		return entity;
	}
	
}
