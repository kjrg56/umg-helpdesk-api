package com.umg.helpdesk.service.mapper;

import org.springframework.stereotype.Component;

import com.umg.helpdesk.model.Notification;
import com.umg.helpdesk.model.NotificationStatus;
import com.umg.helpdesk.rest.gen.dto.NotificationDto;

@Component
public class NotificationMapper {

	public NotificationDto toDto(Notification entity) {
		NotificationDto dto = new NotificationDto();
		dto.setCreationDate(entity.getCreationDate());
		dto.setMessage(entity.getMessage());
		
		if (entity.getNotificationId() != null)
			dto.setId(entity.getNotificationId().toString());
		
		if (entity.getStatus() != null)
			dto.setStatus(entity.getStatus().name());
		
		if (entity.getTicketId() != null)
			dto.setTicketId(entity.getTicketId().toString());
		
		if (entity.getUserId() != null)
			dto.setUserId(entity.getUserId().toString());
		
		return dto;
	}

	public Notification toEntity(NotificationDto dto) {
		Notification entity = new Notification();
		entity.setCreationDate(dto.getCreationDate());
		entity.setMessage(dto.getMessage());
		
		if (dto.getId() != null)
			entity.setNotificationId(Long.parseLong(dto.getId()));
		
		if (dto.getStatus() != null)
			entity.setStatus(NotificationStatus.valueOf(dto.getStatus()));
		
		if (dto.getTicketId() != null)
			entity.setTicketId(Long.parseLong(dto.getTicketId()));
		
		if (dto.getUserId() != null)
			entity.setUserId(Long.parseLong(dto.getUserId()));
		
		return entity;
	}
	
}
