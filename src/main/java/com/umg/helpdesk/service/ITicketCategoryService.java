package com.umg.helpdesk.service;

import java.util.List;

import com.umg.helpdesk.rest.gen.dto.TicketCategoryCreationDto;
import com.umg.helpdesk.rest.gen.dto.TicketCategoryDto;
import com.umg.helpdesk.rest.gen.dto.TicketCategoryUpdateDto;

public interface ITicketCategoryService {
	
	TicketCategoryDto createTicketCategory(TicketCategoryCreationDto ticketCategory);
	
	TicketCategoryDto updateTicketCategory(String ticketCategoryId, TicketCategoryUpdateDto ticketCategory);
	
	TicketCategoryDto getTicketCategoryById(String ticketCategoryId);
	
	List<TicketCategoryDto> listTicketCategories(Integer offset, Integer limit);
	
}
