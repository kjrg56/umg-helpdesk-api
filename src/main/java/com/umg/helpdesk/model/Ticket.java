package com.umg.helpdesk.model;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TBL_TICKET")
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TICKET_ID")
	private Long ticketId;
	
	@Column(name = "TICKET_NAME")
	private String name;
	
	@Column(name = "TICKET_DESC")
	private String description;
	
	@Column(name = "STATUS")
	private TicketStatus status;
	
	@Column(name = "CREATION_DATE")
	private OffsetDateTime creationDate;
	
	@Column(name = "ASSIGNATION_DATE")
	private OffsetDateTime assignationDate;
	
	@Column(name = "CLOSED_DATE")
	private OffsetDateTime closedDate;
	
	@Column(name = "HOURS")
	private Integer hours;
	
	@ManyToOne
	@JoinColumn(name = "TICKET_CATEGORY_ID")
	private TicketCategory ticketCategory;
	
	@ManyToOne
	@JoinColumn(name = "USER_CREATED_ID")
	private User userCreated;
	
	@ManyToOne
	@JoinColumn(name = "ASSIGNED_USER_ID")
	private User userAssigned;
	
}
