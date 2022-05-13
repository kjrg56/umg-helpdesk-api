package com.umg.helpdesk.model;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TBL_NOTIFICATION")
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NOTIFICATION_ID")
	private Long notificationId;
	
	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name = "TICKET_ID")
	private Long ticketId;

	@Column(name = "CREATION_DATE")
	private OffsetDateTime creationDate;
	
	@Column(name = "STATUS")
	private NotificationStatus status;
	
}
