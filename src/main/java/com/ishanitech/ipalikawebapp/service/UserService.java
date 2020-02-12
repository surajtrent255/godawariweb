package com.ishanitech.ipalikawebapp.service;


import java.util.Map;

import com.ishanitech.ipalikawebapp.dto.Response;
import com.ishanitech.ipalikawebapp.dto.UserDTO;

public interface UserService {
	Response<?> addUser(UserDTO user, String token);
	public void deleteUser(int userId, String token);
	public void changePassword(String newPassword, int userId, String token);
	public void updateUserInfoByUserId(Map<String, Object> updates, int userId, String token);
	public void disableUser(int userId, String token);
}
