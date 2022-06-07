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

import com.umg.helpdesk.model.Department;
import com.umg.helpdesk.model.User;
import com.umg.helpdesk.model.UserRole;
import com.umg.helpdesk.repository.IDepartmentRepository;
import com.umg.helpdesk.repository.INotificationRepository;
import com.umg.helpdesk.repository.IUserRepository;
import com.umg.helpdesk.rest.gen.dto.NotificationDto;
import com.umg.helpdesk.rest.gen.dto.UserCreationDto;
import com.umg.helpdesk.rest.gen.dto.UserDto;
import com.umg.helpdesk.rest.gen.dto.UserUpdateDto;
import com.umg.helpdesk.service.IUserService;
import com.umg.helpdesk.service.mapper.NotificationMapper;
import com.umg.helpdesk.service.mapper.UserMapper;
import com.umg.helpdesk.service.utils.ModelUtils;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IDepartmentRepository departmentRepository;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private INotificationRepository notificationRepository;
	
	@Autowired
	private NotificationMapper notificationMapper;
	
	@Override
	public List<UserDto> listUsers(Integer offset, Integer limit) {
		int pageSize = ModelUtils.getPageLimit(limit);
		int pageNumber = ModelUtils.getPageNumber(offset, limit);
		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Direction.ASC, "fullName"));
		return userRepository.findAll(pageable).getContent().stream()
				.map(userMapper::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public UserDto createUser(UserCreationDto userCreationDto) {
		Optional<Department> department = departmentRepository.findById(Long.parseLong(userCreationDto.getDepartmentId()));
		if (!department.isPresent())
			throw new RuntimeException("Department not found for id: " + userCreationDto.getDepartmentId());
		
		User user = new User();
		user.setDateOfBirth(userCreationDto.getDateOfBirth());
		user.setEmail(userCreationDto.getEmail());
		user.setFullName(userCreationDto.getFullName());
		user.setUsername(userCreationDto.getUsername());
		user.setUserRole(UserRole.valueOf(userCreationDto.getUserRole()));
		user.setPassword(userCreationDto.getPasswd());
		user.setDepartment(department.get());
		
		user = userRepository.save(user);
		return userMapper.toDto(user);
	}

	@Override
	public UserDto updateUser(String userId, UserUpdateDto userCreationDto) {
		Optional<User> opUser = userRepository.findById(Long.parseLong(userId));
		if (!opUser.isPresent())
			return null;
		
		User user = opUser.get();
		
		if (!user.getDepartment().getDepartmentId().toString().equals(userCreationDto.getDepartmentId())) {
			if (userCreationDto.getDepartmentId() != null) {
				Optional<Department> department = departmentRepository.findById(Long.parseLong(userCreationDto.getDepartmentId()));
				if (!department.isPresent())
					throw new RuntimeException("Department not found for id: " + userCreationDto.getDepartmentId());
				
				user.setDepartment(department.get());
			}
		}
		
		user.setDateOfBirth(userCreationDto.getDateOfBirth());
		user.setEmail(userCreationDto.getEmail());
		user.setFullName(userCreationDto.getFullName());
		user.setUserRole(UserRole.valueOf(userCreationDto.getUserRole()));
		
		user = userRepository.save(user);
		return userMapper.toDto(user);
	}

	@Override
	public UserDto getUserById(String userId) {
		Optional<User> opUser = userRepository.findById(Long.parseLong(userId));
		if (opUser.isPresent())
			return userMapper.toDto(opUser.get());

		return null;
	}

	@Override
	public UserDto login(String username, String pwd) {
		Optional<User> user = userRepository.getFirstByUsername(username);
		if (!user.isPresent())
			return null;
		
		if (user.get().getPassword().equals(pwd))
			return userMapper.toDto(user.get());
			
		return null;
	}

	@Override
	public List<NotificationDto> listNotificationsByUserId(String userId) {
		return notificationRepository.findAllByUserIdOrderByNotificationIdDesc	(Long.parseLong(userId)).stream()
				.map(notificationMapper::toDto).collect(Collectors.toList());
	}

}
