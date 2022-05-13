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
@Table(name = "TBL_TICKET_COMMENT")
public class TicketComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TICKET_COMMENT_ID")
	private Long ticketCommentId;
	
	@Column(name = "TICKET_ID")
	private Long ticketId;
	
	@Column(name = "USER_CREATED_ID")
	private Long userCreatedId;
	
	@Column(name = "CREATION_DATE")
	private OffsetDateTime creationDate;
	
	@Column(name = "COMMENT")
	private String comment;
	
}
