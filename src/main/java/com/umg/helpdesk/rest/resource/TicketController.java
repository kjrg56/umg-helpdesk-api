package com.umg.helpdesk.rest.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.umg.helpdesk.rest.gen.dto.TicketCommentCreationDto;
import com.umg.helpdesk.rest.gen.dto.TicketCommentDto;
import com.umg.helpdesk.rest.gen.dto.TicketCreationDto;
import com.umg.helpdesk.rest.gen.dto.TicketDto;
import com.umg.helpdesk.rest.gen.dto.TicketUpdateDto;
import com.umg.helpdesk.rest.gen.spec.TicketsApi;
import com.umg.helpdesk.service.ITicketService;

@RestController
public class TicketController implements TicketsApi {

	@Autowired
	private ITicketService ticketService;
	
	@Override
	public ResponseEntity<TicketDto> createTicket(@Valid TicketCreationDto ticketCreationDto) {
		TicketDto ticket = ticketService.createTicket(ticketCreationDto);
		return new ResponseEntity<TicketDto>(ticket, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<TicketCommentDto> createTicketComment(String ticketId,
			@Valid TicketCommentCreationDto ticketCommentCreationDto) {
		TicketCommentDto comment = ticketService.createTicketComment(ticketId, ticketCommentCreationDto);
		return new ResponseEntity<TicketCommentDto>(comment, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<Void> deleteTicketComment(String ticketId, String commentId) {
		ticketService.deleteTicketComment(ticketId, commentId);
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<TicketDto> getTicketById(String id) {
		TicketDto ticket = ticketService.getTicketById(id);
		if (ticket == null)
			return ResponseEntity.notFound().build();
		
		return new ResponseEntity<TicketDto>(ticket, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<TicketCommentDto>> listTicketCommentsById(String ticketId, @Valid Integer offset,
			@Valid Integer limit) {
		List<TicketCommentDto> comments = ticketService.listTicketCommentsById(ticketId, offset, limit);
		return new ResponseEntity<List<TicketCommentDto>>(comments, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<TicketDto>> listTickets(@Valid Integer offset, @Valid Integer limit) {
		List<TicketDto> tickets = ticketService.listTickets(offset, limit);
		return new ResponseEntity<List<TicketDto>>(tickets, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<TicketDto> updateTicket(String id, @Valid TicketUpdateDto ticketUpdateDto) {
		TicketDto ticket = ticketService.updateTicket(id, ticketUpdateDto);
		if (ticket == null)
			return ResponseEntity.notFound().build();
		
		return new ResponseEntity<TicketDto>(ticket, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<TicketDto> closeTicket(@PathVariable("ticketId") String ticketId) {
		TicketDto ticket = ticketService.closeTicket(ticketId);
		if (ticket == null)
			return ResponseEntity.notFound().build();
		
		return new ResponseEntity<TicketDto>(ticket, HttpStatus.OK);
	}

}
