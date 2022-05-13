package com.umg.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umg.helpdesk.model.TicketCategory;

public interface ITicketCategoryRepository extends JpaRepository<TicketCategory, Long> {

}
