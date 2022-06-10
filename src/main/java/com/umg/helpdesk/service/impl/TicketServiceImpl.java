package com.umg.helpdesk.service.impl;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.umg.helpdesk.model.Notification;
import com.umg.helpdesk.model.NotificationStatus;
import com.umg.helpdesk.model.Ticket;
import com.umg.helpdesk.model.TicketCategory;
import com.umg.helpdesk.model.TicketComment;
import com.umg.helpdesk.model.TicketStatus;
import com.umg.helpdesk.model.User;
import com.umg.helpdesk.repository.INotificationRepository;
import com.umg.helpdesk.repository.ITicketCategoryRepository;
import com.umg.helpdesk.repository.ITicketCommentRepository;
import com.umg.helpdesk.repository.ITicketRepository;
import com.umg.helpdesk.repository.IUserRepository;
import com.umg.helpdesk.rest.gen.dto.NotificationDto;
import com.umg.helpdesk.rest.gen.dto.TicketCommentCreationDto;
import com.umg.helpdesk.rest.gen.dto.TicketCommentDto;
import com.umg.helpdesk.rest.gen.dto.TicketCreationDto;
import com.umg.helpdesk.rest.gen.dto.TicketDto;
import com.umg.helpdesk.rest.gen.dto.TicketUpdateDto;
import com.umg.helpdesk.rest.gen.dto.TicketsByStatusDto;
import com.umg.helpdesk.service.ITicketService;
import com.umg.helpdesk.service.mapper.NotificationMapper;
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
	private INotificationRepository notificationRepository;
	
	@Autowired
	private TicketMapper ticketMapper;
	
	@Autowired
	private TicketCommentMapper ticketCommentMapper;
	
	@Autowired
	private NotificationMapper notificationMapper;
	
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
		
		if (ticket.getStatus().compareTo(TicketStatus.Assigned) == 0)
			sendNotification(ticket.getTicketId(), ticket.getUserAssigned().getUserId(), "Nuevo Ticket Asignado - #"+ticket.getTicketId() + ": " + ticket.getName());
		
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
		
		if (ticketCommentCreationDto.getHours() != null && ticketCommentCreationDto.getHours().intValue() > 0) {
			Optional<Ticket> opTicket = ticketRepository.findById(Long.parseLong(ticketId));
			if (opTicket.isPresent()) {
				Ticket ticket = opTicket.get();
				int hours = ticket.getHours() == null ? 0 : ticket.getHours();
				hours += ticketCommentCreationDto.getHours();
				ticket.setHours(hours);
				ticketRepository.save(ticket);
			}
		}
		
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
		
		return ticketCommentRepository.findAllByTicketId(Long.parseLong(ticketId), pageable)
				.stream()
				.map(ticketCommentMapper::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<TicketDto> listTickets(Integer offset, Integer limit, String userId) {
		limit = ModelUtils.getPageLimit(limit);
		int page = ModelUtils.getPageNumber(offset, limit);
		Pageable pageable = PageRequest.of(page, limit, Direction.DESC, "TicketId");
		
		if (userId == null) {
			return ticketRepository.findAll(pageable).stream()
					.map(ticketMapper::toDto)
					.collect(Collectors.toList());	
		} else {
			return ticketRepository.findAll(pageable).stream()
					.filter(i -> (i.getUserCreated().getUserId().equals(Long.parseLong(userId)) || (i.getUserAssigned() != null && i.getUserAssigned().getUserId().equals(Long.parseLong(userId)))) )
					.map(ticketMapper::toDto)
					.collect(Collectors.toList());
		}
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
			
			sendNotification(ticket.getTicketId(), ticket.getUserAssigned().getUserId(), "Nuevo Ticket Asignado - #"+ticket.getTicketId() + ": " + ticket.getName());
		}
		
		if (ticketUpdateDto.getStatus() != null)
			ticket.setStatus(TicketStatus.valueOf(ticketUpdateDto.getStatus()));
		
		ticket = ticketRepository.save(ticket);
		return ticketMapper.toDto(ticket);
	}

	@Override
	public TicketDto closeTicket(String id) {
		Optional<Ticket> opTicket = ticketRepository.findById(Long.parseLong(id));
		if (!opTicket.isPresent())
			return null;
		
		Ticket ticket = opTicket.get();
		ticket.setClosedDate(OffsetDateTime.now());
		ticket.setStatus(TicketStatus.Closed);
		ticket = ticketRepository.save(ticket);
		
		sendNotification(ticket.getTicketId(), ticket.getUserCreated().getUserId(), "Ticket Cerrado - #"+ticket.getTicketId());
		
		return ticketMapper.toDto(ticket);
	}
	
	@Override
	public List<TicketsByStatusDto> getTicketAnalyticsByStatus() {
		List<Ticket> list = ticketRepository.findAll();
		
		Long countPending = list.stream().filter(i -> i.getStatus().compareTo(TicketStatus.Pending) == 0).count();
		Long countAssigned = list.stream().filter(i -> i.getStatus().compareTo(TicketStatus.Assigned) == 0).count();
		Long countProgress = list.stream().filter(i -> i.getStatus().compareTo(TicketStatus.InProgress) == 0).count();
		Long countClosed = list.stream().filter(i -> i.getStatus().compareTo(TicketStatus.Closed) == 0).count();
		
		List<TicketsByStatusDto> listByStatus = new ArrayList<TicketsByStatusDto>();
		
		TicketsByStatusDto t1 = new TicketsByStatusDto();
		t1.setStatus(TicketStatus.Pending.name());
		t1.setQuantity(countPending.intValue());
		listByStatus.add(t1);

		TicketsByStatusDto t2 = new TicketsByStatusDto();
		t2.setStatus(TicketStatus.Assigned.name());
		t2.setQuantity(countAssigned.intValue());
		listByStatus.add(t2);
		
		TicketsByStatusDto t3 = new TicketsByStatusDto();
		t3.setStatus(TicketStatus.InProgress.name());
		t3.setQuantity(countProgress.intValue());
		listByStatus.add(t3);
		
		TicketsByStatusDto t4 = new TicketsByStatusDto();
		t4.setStatus(TicketStatus.Closed.name());
		t4.setQuantity(countClosed.intValue());
		listByStatus.add(t4);
		
		return listByStatus;
	}
	
	@org.springframework.transaction.annotation.Transactional
	@Override
	public void deleteTicket(String id) {
		List<Notification> notifications = notificationRepository.findAllByTicketId(Long.parseLong(id));
		if (!notifications.isEmpty())
			notificationRepository.deleteAll(notifications);
		
		List<TicketComment> comments = ticketCommentRepository.findAllByTicketId(Long.parseLong(id));
		if (!comments.isEmpty())
			ticketCommentRepository.deleteAll(comments);
		
		ticketRepository.deleteById(Long.parseLong(id));
	}
	
	public NotificationDto sendNotification(Long ticketId, Long userId, String message) {
		Notification notification = new Notification();
		notification.setCreationDate(OffsetDateTime.now());
		notification.setStatus(NotificationStatus.NotViewed);
		notification.setTicketId(ticketId);
		notification.setUserId(userId);
		notification.setMessage(message);
		
		notification = notificationRepository.save(notification);
		return notificationMapper.toDto(notification);
	}
	
}
