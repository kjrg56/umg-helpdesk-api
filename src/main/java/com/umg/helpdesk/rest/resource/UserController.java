package com.umg.helpdesk.rest.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.umg.helpdesk.rest.gen.dto.NotificationDto;
import com.umg.helpdesk.rest.gen.dto.UserCreationDto;
import com.umg.helpdesk.rest.gen.dto.UserDto;
import com.umg.helpdesk.rest.gen.dto.UserUpdateDto;
import com.umg.helpdesk.rest.gen.spec.UsersApi;
import com.umg.helpdesk.service.IUserService;

@RestController
public class UserController implements UsersApi {

	@Autowired
	private IUserService userService;
	
	@Override
	public ResponseEntity<UserDto> createUser( @Valid @RequestBody(required = true) UserCreationDto userCreationDto) {
		UserDto user = userService.createUser(userCreationDto);
		return new ResponseEntity<UserDto>(user, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id", required = true) String id) {
		UserDto user = userService.getUserById(id);
		if (user == null)
			return ResponseEntity.notFound().build();
			
		return new ResponseEntity<UserDto>(user, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<NotificationDto>> listNotificationsByUserId(@PathVariable(name = "id", required = true) String userId, @Valid @RequestParam(value = "offset", required = false) Integer offset, @Valid @RequestParam(value = "limit", required = false) Integer limit) {
		return UsersApi.super.listNotificationsByUserId(userId, offset, limit);
	}

	@Override
	public ResponseEntity<List<UserDto>> listUsers(@Valid @RequestParam(value = "offset", required = false) Integer offset, @Valid @RequestParam(value = "limit", required = false) Integer limit) {
		List<UserDto> listUsers = userService.listUsers(offset, limit);
		return ResponseEntity.ok(listUsers);
	}

	@Override
	public ResponseEntity<UserDto> loginUser(@NotNull @Valid String username, @NotNull @Valid String password) {
		// TODO Auto-generated method stub
		return UsersApi.super.loginUser(username, password);
	}

	@Override
	public ResponseEntity<UserDto> updateUser(@PathVariable(name = "id", required = true) String id,  @Valid @RequestBody(required = true) UserUpdateDto userUpdateDto) {
		UserDto user = userService.updateUser(id, userUpdateDto);
		if (user == null)
			return ResponseEntity.notFound().build();
			
		return new ResponseEntity<UserDto>(user, HttpStatus.OK);
	}

}
