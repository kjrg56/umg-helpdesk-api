package com.umg.helpdesk.rest.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.umg.helpdesk.rest.gen.dto.TicketCategoryCreationDto;
import com.umg.helpdesk.rest.gen.dto.TicketCategoryDto;
import com.umg.helpdesk.rest.gen.dto.TicketCategoryUpdateDto;
import com.umg.helpdesk.rest.gen.spec.TicketCategoriesApi;
import com.umg.helpdesk.service.ITicketCategoryService;

@RestController
public class TicketCategoryController implements TicketCategoriesApi {

	@Autowired
	private ITicketCategoryService ticketCategoryService;
	
	@Override
	public ResponseEntity<TicketCategoryDto> createTicketCategory(@Valid @RequestBody(required = true) TicketCategoryCreationDto ticketCategoryCreationDto) {
		TicketCategoryDto cat = ticketCategoryService.createTicketCategory(ticketCategoryCreationDto);
		return new ResponseEntity<TicketCategoryDto>(cat, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<TicketCategoryDto> getTicketCategoryById(@PathVariable(name = "id", required = true) String id) {
		TicketCategoryDto cat = ticketCategoryService.getTicketCategoryById(id);
		if (cat == null)
			return ResponseEntity.notFound().build();
			
		return new ResponseEntity<TicketCategoryDto>(cat, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<TicketCategoryDto>> listTicketCategories(@Valid @RequestParam(value = "offset", required = false) Integer offset, @Valid @RequestParam(value = "limit", required = false) Integer limit) {
		List<TicketCategoryDto> listCategories = ticketCategoryService.listTicketCategories(offset, limit);
		return new ResponseEntity<List<TicketCategoryDto>>(listCategories, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<TicketCategoryDto> updateTicketCategory(@PathVariable(name = "id", required = true) String id, @Valid @RequestBody(required = true) TicketCategoryUpdateDto ticketCategoryUpdateDto) {
		TicketCategoryDto cat = ticketCategoryService.updateTicketCategory(id, ticketCategoryUpdateDto);
		if (cat == null)
			return ResponseEntity.notFound().build();
		
		return new ResponseEntity<TicketCategoryDto>(cat, HttpStatus.OK);
	}

}
