package com.umg.helpdesk.service.impl;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.umg.helpdesk.model.Ticket;
import com.umg.helpdesk.model.TicketCategory;
import com.umg.helpdesk.model.TicketComment;
import com.umg.helpdesk.model.TicketStatus;
import com.umg.helpdesk.model.User;
import com.umg.helpdesk.repository.ITicketCategoryRepository;
import com.umg.helpdesk.repository.ITicketCommentRepository;
import com.umg.helpdesk.repository.ITicketRepository;
import com.umg.helpdesk.repository.IUserRepository;
import com.umg.helpdesk.rest.gen.dto.TicketCommentCreationDto;
import com.umg.helpdesk.rest.gen.dto.TicketCommentDto;
import com.umg.helpdesk.rest.gen.dto.TicketCreationDto;
import com.umg.helpdesk.rest.gen.dto.TicketDto;
import com.umg.helpdesk.rest.gen.dto.TicketUpdateDto;
import com.umg.helpdesk.service.ITicketService;
import com.umg.helpdesk.service.mapper.TicketCommentMapper;
import com.umg.helpdesk.service.mapper.TicketMapper;
import com.umg.helpdesk.service.utils.ModelUtils;

@Service
public class TicketServiceImpl implements ITicketService {

	@Autowired
	private ITicketRepository ticketRepository;
	
	@Autowired
	private ITicketCommentRepository ticketCommentRepository;
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private ITicketCategoryRepository ticketCategoryRepository;
	
	@Autowired
	private TicketMapper ticketMapper;
	
	@Autowired
	private TicketCommentMapper ticketCommentMapper;
	
	@Transactional
	@Override
	public TicketDto createTicket(TicketCreationDto ticketCreationDto) {
		Optional<User> userCreated = userRepository.findById(Long.parseLong(ticketCreationDto.getUserCreatedId()));
		Optional<TicketCategory> category = ticketCategoryRepository.findById(Long.parseLong(ticketCreationDto.getCategoryId()));
		
		Optional<User> userAssigned = Optional.empty();
		if (ticketCreationDto.getAssignedUserId() != null)
			userAssigned = userRepository.findById(Long.parseLong(ticketCreationDto.getAssignedUserId()));
		
		Ticket ticket = new Ticket();
		ticket.setName(ticketCreationDto.getName());
		ticket.setDescription(ticketCreationDto.getDescription());
		ticket.setTicketCategory(category.get());
		ticket.setUserCreated(userCreated.get());
		ticket.setCreationDate(OffsetDateTime.now());
		ticket.setHours(0);

		if (userAssigned.isPresent()) {
			ticket.setUserAssigned(userAssigned.get());
			ticket.setAssignationDate(OffsetDateTime.now());
			ticket.setStatus(TicketStatus.Assigned);
		} else {
			ticket.setStatus(TicketStatus.Pending);
		}
		
		ticket = ticketRepository.save(ticket);
		//TODO send notification
		return ticketMapper.toDto(ticket);
	}

	@Override
	public TicketCommentDto createTicketComment(String ticketId, TicketCommentCreationDto ticketCommentCreationDto) {
		TicketComment comment = new TicketComment();
		comment.setComment(ticketCommentCreationDto.getComment());
		comment.setTicketId(Long.parseLong(ticketId));
		comment.setUserCreatedId(Long.parseLong(ticketCommentCreationDto.getUserCreatedId()));
		comment.setCreationDate(OffsetDateTime.now());
		
		comment = ticketCommentRepository.save(comment);
		return ticketCommentMapper.toDto(comment);
	}

	@Override
	public void deleteTicketComment(String ticketId, String commentId) {
		Optional<TicketComment> comment = ticketCommentRepository.findById(Long.parseLong(commentId));
		if (comment.isPresent()) {
			ticketCommentRepository.delete(comment.get());;
		}
	}

	@Override
	public TicketDto getTicketById(String id) {
		Optional<Ticket> ticket = ticketRepository.findById(Long.parseLong(id));
		if (ticket.isPresent())
			return ticketMapper.toDto(ticket.get());
		
		return null;
	}

	@Override
	public List<TicketCommentDto> listTicketCommentsById(String ticketId, Integer offset, Integer limit) {
		limit = ModelUtils.getPageLimit(limit);
		int page = ModelUtils.getPageNumber(offset, limit);
		Pageable pageable = PageRequest.of(page, limit, Direction.DESC, "TicketCommentId");
		
		return ticketCommentRepository.findAll(pageable).stream()
				.map(ticketCommentMapper::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<TicketDto> listTickets(Integer offset, Integer limit) {
		limit = ModelUtils.getPageLimit(limit);
		int page = ModelUtils.getPageNumber(offset, limit);
		Pageable pageable = PageRequest.of(page, limit, Direction.DESC, "TicketId");
		
		return ticketRepository.findAll(pageable).stream()
				.map(ticketMapper::toDto)
				.collect(Collectors.toList());
	}

	@Transactional
	@Override
	public TicketDto updateTicket(String id, TicketUpdateDto ticketUpdateDto) {
		Optional<Ticket> opTicket = ticketRepository.findById(Long.parseLong(id));
		if (!opTicket.isPresent())
			return null;
		
		Optional<TicketCategory> category = Optional.empty();
		if (ticketUpdateDto.getCategoryId() != null)
			category = ticketCategoryRepository.findById(Long.parseLong(ticketUpdateDto.getCategoryId()));
		
		Optional<User> userAssigned = Optional.empty();
		if (ticketUpdateDto.getAssignedUserId() != null) {
			if (opTicket.get().getUserAssigned() == null || !opTicket.get().getUserAssigned().getUserId().toString().equals(ticketUpdateDto.getAssignedUserId())) {
				userAssigned = userRepository.findById(Long.parseLong(ticketUpdateDto.getAssignedUserId()));	
			}
		}
		
		Ticket ticket = opTicket.get();
		
		if (ticketUpdateDto.getName() != null)
			ticket.setName(ticketUpdateDto.getName());
		
		if (ticketUpdateDto.getDescription() != null)
			ticket.setDescription(ticketUpdateDto.getDescription());
		
		if (category.isPresent())
			ticket.setTicketCategory(category.get());

		if (userAssigned.isPresent()) {
			ticket.setUserAssigned(userAssigned.get());
			ticket.setAssignationDate(OffsetDateTime.now());
			ticket.setStatus(TicketStatus.Assigned);
		}
		
		//TODO be able to update status, by PATCH
		
		ticket = ticketRepository.save(ticket);
		//TODO send notification
		return ticketMapper.toDto(ticket);
	}

}
