package com.umg.helpdesk.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.umg.helpdesk.model.TicketComment;

public interface ITicketCommentRepository extends JpaRepository<TicketComment, Long> {

	List<TicketComment> findAllByTicketId(Long ticketId, Pageable pageable);
	
}
