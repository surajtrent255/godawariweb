package com.ishanitech.ipalikawebapp.serviceImpl;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ishanitech.ipalikawebapp.dto.Response;
import com.ishanitech.ipalikawebapp.dto.UserDTO;
import com.ishanitech.ipalikawebapp.service.UserService;
import com.ishanitech.ipalikawebapp.utilities.HttpUtils;

@Service
public class UserServiceImpl implements UserService {
	private final RestTemplate restTemplate;
	
	public UserServiceImpl(RestTemplate restTemplate) {
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		this.restTemplate = restTemplate;
		this.restTemplate.setRequestFactory(requestFactory);
	}

	@Override
	public Response<?> addUser(UserDTO user, String token) {
		restTemplate.postForEntity("http://localhost:8888/user/", 
				HttpUtils.createRequestEntityWithHeadersAndToken(user, MediaType.APPLICATION_JSON, token), null);
		return new Response<String>("Successful");
	}

	@Override
	public void deleteUser(int userId, String token) {
		restTemplate.exchange(String.format("http://localhost:8888/user/%d", userId), HttpMethod.DELETE, null, String.class);
	}

	@Override
	public void changePassword(String newPassword, int userId, String token) {
		System.out.println("Token: " + token);
		restTemplate.put("http://localhost:8888/user/" + userId + "/password",
				HttpUtils.createRequestEntityWithHeadersAndToken(newPassword, MediaType.APPLICATION_JSON, token));
	}

	@Override
	public void updateUserInfoByUserId(Map<String, Object> updates, int userId, String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);
		headers.setContentType(MediaType.APPLICATION_JSON);
		System.out.println(updates.toString());
		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(updates, headers);
		//restTemplate.pat
		//restTemplate.patchForObject("http://localhost:8888/user/" + userId, requestEntity, String.class);
		restTemplate.exchange("http://localhost:8888/user/" + userId, HttpMethod.PATCH, requestEntity, String.class);
	}

	@Override
	public void disableUser(int userId, String token) {
		restTemplate.put("http://localhost:8888/user/" + userId + "/disable",
				HttpUtils.createRequestEntityWithHeadersAndToken(null, MediaType.APPLICATION_JSON, token));
	}
}
