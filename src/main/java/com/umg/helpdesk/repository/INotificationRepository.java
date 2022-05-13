package com.umg.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umg.helpdesk.model.Notification;

public interface INotificationRepository extends JpaRepository<Notification, Long> {

}
