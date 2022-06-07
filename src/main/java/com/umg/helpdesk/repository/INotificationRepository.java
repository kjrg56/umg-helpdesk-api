package com.umg.helpdesk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umg.helpdesk.model.Notification;

public interface INotificationRepository extends JpaRepository<Notification, Long> {

	List<Notification> findAllByUserIdOrderByNotificationIdDesc(Long userId);
	
	List<Notification> findAllByTicketId(Long ticketId);
	
}
