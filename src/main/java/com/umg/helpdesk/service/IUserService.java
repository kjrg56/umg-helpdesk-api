package com.umg.helpdesk.service;

import java.util.List;

import com.umg.helpdesk.rest.gen.dto.NotificationDto;
import com.umg.helpdesk.rest.gen.dto.UserCreationDto;
import com.umg.helpdesk.rest.gen.dto.UserDto;
import com.umg.helpdesk.rest.gen.dto.UserUpdateDto;

public interface IUserService {

	List<UserDto> listUsers(Integer offset, Integer limit);
	
	UserDto createUser(UserCreationDto userCreationDto);
	
	UserDto updateUser(String userId, UserUpdateDto userCreationDto);
	
	UserDto getUserById(String userId);
	
	UserDto login(String username, String pwd);
	
	List<NotificationDto> listNotificationsByUserId(String userId);
	
}
