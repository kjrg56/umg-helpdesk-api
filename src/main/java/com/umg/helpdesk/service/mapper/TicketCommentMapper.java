package com.umg.helpdesk.service.mapper;

import org.springframework.stereotype.Component;

import com.umg.helpdesk.model.TicketComment;
import com.umg.helpdesk.rest.gen.dto.TicketCommentDto;

@Component
public class TicketCommentMapper {
	
	public TicketCommentDto toDto(TicketComment entity) {
		TicketCommentDto dto = new TicketCommentDto();
		dto.setComment(entity.getComment());
		dto.setCreationDate(entity.getCreationDate());
		
		if (entity.getTicketId() != null)
			dto.setTicketId(entity.getTicketId().toString());
		
		if (entity.getTicketCommentId() != null)
			dto.setId(entity.getTicketCommentId().toString());
		
		if (entity.getUserCreatedId() != null)
			dto.setUserCreatedId(entity.getUserCreatedId().toString());
		
		return dto;
	}

	public TicketComment toEntity(TicketCommentDto dto) {
		TicketComment entity = new TicketComment();
		entity.setComment(dto.getComment());
		entity.setCreationDate(dto.getCreationDate());
		
		if (dto.getTicketId() != null)
			entity.setTicketId(Long.parseLong(dto.getTicketId()));
		
		if (dto.getId() != null)
			entity.setTicketCommentId(Long.parseLong(dto.getId()));
		
		if (dto.getUserCreatedId() != null)
			entity.setUserCreatedId(Long.parseLong(dto.getUserCreatedId()));
		
		return entity;
	}
	
}
