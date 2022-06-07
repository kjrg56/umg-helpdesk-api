package com.umg.helpdesk.service;

import java.util.List;

import com.umg.helpdesk.rest.gen.dto.TicketCommentCreationDto;
import com.umg.helpdesk.rest.gen.dto.TicketCommentDto;
import com.umg.helpdesk.rest.gen.dto.TicketCreationDto;
import com.umg.helpdesk.rest.gen.dto.TicketDto;
import com.umg.helpdesk.rest.gen.dto.TicketUpdateDto;
import com.umg.helpdesk.rest.gen.dto.TicketsByStatusDto;

public interface ITicketService {

	TicketDto createTicket(TicketCreationDto ticketCreationDto);
	
	TicketCommentDto createTicketComment(String ticketId, TicketCommentCreationDto ticketCommentCreationDto);
	
	void deleteTicketComment(String ticketId, String commentId);
	
	TicketDto getTicketById(String id);
	
	List<TicketCommentDto> listTicketCommentsById(String ticketId, Integer offset, Integer limit);
	
	List<TicketDto> listTickets(Integer offset, Integer limit, String userId);
	
	TicketDto updateTicket(String id, TicketUpdateDto ticketUpdateDto);
	
	TicketDto closeTicket(String id);
	
	List<TicketsByStatusDto> getTicketAnalyticsByStatus();
	
	void deleteTicket(String id);
	
}
