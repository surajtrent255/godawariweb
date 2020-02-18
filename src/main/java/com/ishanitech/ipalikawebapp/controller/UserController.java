package com.ishanitech.ipalikawebapp.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ishanitech.ipalikawebapp.dto.Response;
import com.ishanitech.ipalikawebapp.dto.UserDTO;
import com.ishanitech.ipalikawebapp.dto.UserRegistrationDTO;
import com.ishanitech.ipalikawebapp.service.UserService;
import com.ishanitech.ipalikawebapp.utilities.UserDetailsUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/user")
@RestController
public class UserController {
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@Secured({"ROLE_SUPER_ADMIN", "ROLE_CENTRAL_ADMIN"})
	@PostMapping
	public Response<String> addUser(@RequestBody UserRegistrationDTO user, @AuthenticationPrincipal UserDTO loggedInUser) {
		userService.addUser(user, UserDetailsUtil.getToken(loggedInUser));
		return new Response<String>("User is created!");
	}
	
	@Secured({"ROLE_SUPER_ADMIN", "ROLE_CENTRAL_ADMIN"})
	@DeleteMapping("/{userId}")
	public Response<String> deleteUserById(@PathVariable("userId") int userId, @AuthenticationPrincipal UserDTO loggedInUser) {
		userService.deleteUser(userId, UserDetailsUtil.getToken(loggedInUser));
		return new Response<String>("Successfully deleted the user.");
	}
	
	@Secured({"ROLE_SUPER_ADMIN", "ROLE_CENTRAL_ADMIN"})
	@PutMapping("/{userId}/disable")
	public Response<String> disableUserById(@PathVariable("userId") int userId, @AuthenticationPrincipal UserDTO loggedInUser) {
		userService.disableUser(userId, UserDetailsUtil.getToken(loggedInUser));
		return new Response<String>("Successfully disabled user.");
	}
	
	@PutMapping("/{userId}/password")
	public Response<String> changePassword(@RequestBody String newPassword, 
			@PathVariable("userId") int userId, 
			@AuthenticationPrincipal UserDTO loggedInUser) {
		log.info("Called");
		userService.changePassword(newPassword, userId, UserDetailsUtil.getToken(loggedInUser));
		return new Response<String>("Successfully changed the password");
	}
	
	@PatchMapping(value = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Response<String> changeUserInfo(@PathVariable("userId") int userId,
			@RequestBody Map<String, Object> updates,
			@AuthenticationPrincipal UserDTO loggedInUser) {
		userService.updateUserInfoByUserId(updates, userId, UserDetailsUtil.getToken(loggedInUser));
		return new Response<String>("Successfully updated information");
	}
}
