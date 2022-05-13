package com.umg.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umg.helpdesk.model.TicketComment;

public interface ITicketCommentRepository extends JpaRepository<TicketComment, Long> {

}
