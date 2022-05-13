package com.umg.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umg.helpdesk.model.Ticket;

public interface ITicketRepository extends JpaRepository<Ticket, Long> {

}
