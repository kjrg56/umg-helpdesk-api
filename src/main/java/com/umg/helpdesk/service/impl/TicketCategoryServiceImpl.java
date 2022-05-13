package com.umg.helpdesk.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.umg.helpdesk.model.TicketCategory;
import com.umg.helpdesk.repository.ITicketCategoryRepository;
import com.umg.helpdesk.rest.gen.dto.TicketCategoryCreationDto;
import com.umg.helpdesk.rest.gen.dto.TicketCategoryDto;
import com.umg.helpdesk.rest.gen.dto.TicketCategoryUpdateDto;
import com.umg.helpdesk.service.ITicketCategoryService;
import com.umg.helpdesk.service.mapper.TicketCategoryMapper;
import com.umg.helpdesk.service.utils.ModelUtils;

@Service
public class TicketCategoryServiceImpl implements ITicketCategoryService {
	
	@Autowired
	private ITicketCategoryRepository ticketCategoryRepository;

	@Autowired
	private TicketCategoryMapper ticketCategoryMapper;
	
	@Override
	public TicketCategoryDto createTicketCategory(TicketCategoryCreationDto ticketCategory) {
		TicketCategory ticketCat = new TicketCategory();
		ticketCat.setName(ticketCategory.getName());
		ticketCat.setDescription(ticketCategory.getDescription());
		
		ticketCat = ticketCategoryRepository.save(ticketCat);
		return ticketCategoryMapper.toDto(ticketCat);
	}

	@Override
	public TicketCategoryDto updateTicketCategory(String ticketCategoryId, TicketCategoryUpdateDto ticketCategory) {
		Optional<TicketCategory> opTicketCategory = ticketCategoryRepository.findById(Long.parseLong(ticketCategoryId));
		if (!opTicketCategory.isPresent())
			return null;
		
		TicketCategory ticketCat = opTicketCategory.get();
		ticketCat.setName(ticketCategory.getName());
		ticketCat.setDescription(ticketCategory.getDescription());
		
		ticketCat = ticketCategoryRepository.save(ticketCat);
		return ticketCategoryMapper.toDto(ticketCat);
	}

	@Override
	public TicketCategoryDto getTicketCategoryById(String ticketCategoryId) {
		Optional<TicketCategory> ticketCat = ticketCategoryRepository.findById(Long.parseLong(ticketCategoryId));
		if (ticketCat.isPresent())
			return ticketCategoryMapper.toDto(ticketCat.get());
			
		return null;
	}

	@Override
	public List<TicketCategoryDto> listTicketCategories(Integer offset, Integer limit) {
		int pageSize = ModelUtils.getPageLimit(limit);
		int pageNumber = ModelUtils.getPageNumber(offset, limit);
		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Direction.ASC, "name"));
		return ticketCategoryRepository.findAll(pageable).getContent().stream()
				.map(ticketCategoryMapper::toDto)
				.collect(Collectors.toList());
	}

}
