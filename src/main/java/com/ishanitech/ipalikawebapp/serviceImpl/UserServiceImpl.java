package com.ishanitech.ipalikawebapp.serviceImpl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ishanitech.ipalikawebapp.configs.properties.RestApiProperties;
import com.ishanitech.ipalikawebapp.dto.Response;
import com.ishanitech.ipalikawebapp.dto.RoleDTO;
import com.ishanitech.ipalikawebapp.dto.UserDTO;
import com.ishanitech.ipalikawebapp.dto.UserRegistrationDTO;
import com.ishanitech.ipalikawebapp.service.UserService;
import com.ishanitech.ipalikawebapp.utilities.HttpUtils;

@Service
public class UserServiceImpl implements UserService {
	private final RestTemplate restTemplate;
	private final RestApiProperties restApiProperties;
	private final String USER_BASE_URL = "/user";
	public UserServiceImpl(RestTemplate restTemplate, RestApiProperties restApiProperties) {
		this.restTemplate = restTemplate;
		this.restApiProperties = restApiProperties;
	}

	@Override
	public Response<?> addUser(UserRegistrationDTO user, String token) {
		String template = String.format("%s", USER_BASE_URL);
		String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
		RequestEntity<UserRegistrationDTO> requestEntity = HttpUtils.createRequestEntity(HttpMethod.POST, user, MediaType.APPLICATION_JSON, token, url);
		restTemplate.exchange(requestEntity, String.class);
		return new Response<String>("Successful");
	}

	@Override
	public void deleteUser(int userId, String token) {
		String template =  USER_BASE_URL + "{userId}";
		String url = HttpUtils.createRequestUrl(restApiProperties, template, Collections.singletonMap("userId", userId));
		RequestEntity requestEntity = HttpUtils.createRequestEntity(HttpMethod.DELETE, MediaType.APPLICATION_JSON, token, url);
		restTemplate.exchange(requestEntity, String.class);
	}

	@Override
	public void changePassword(String newPassword, int userId, String token) {
		Map<String, Object> urlVariables = new HashMap<>();
		urlVariables.put("userId", userId);
		urlVariables.put("password", "password");
		String template =  USER_BASE_URL + "/{userId}/{password}";
		String url = HttpUtils.createRequestUrl(restApiProperties, template, urlVariables);
		RequestEntity<String> requestEntity = HttpUtils.createRequestEntity(HttpMethod.PUT, newPassword, MediaType.APPLICATION_JSON, token, url);
		restTemplate.exchange(requestEntity, String.class).getBody();
	}

	@Override
	public void updateUserInfoByUserId(Map<String, Object> updates, int userId, String token) {
		/*
		 * restTemplate.patchForObject("http://localhost:8888/user/" + userId,
		 * HttpUtils.createRequestEntityWithHeadersAndToken(updates,
		 * MediaType.APPLICATION_JSON, token), String.class);
		 */
		RequestEntity<Map<String, Object>> requestEntity = HttpUtils.createRequestEntity(HttpMethod.PATCH, updates, MediaType.APPLICATION_JSON, token, "http://localhost:8888/user/" + userId);
		restTemplate.exchange(requestEntity, String.class);
	}

	@Override
	public void disableUser(int userId, String token) {
		/*
		 * restTemplate.put("http://localhost:8888/user/" + userId + "/disable",
		 * HttpUtils.createRequestEntityWithHeadersAndToken(null,
		 * MediaType.APPLICATION_JSON, token));
		 */
		RequestEntity requestEntity = HttpUtils.createRequestEntity(HttpMethod.PUT, MediaType.APPLICATION_JSON, token, "http://localhost:8888/user/" + userId + "/disable");
		restTemplate.exchange(requestEntity, String.class).getBody();
	}

	@Override
	public Response<List<RoleDTO>> getAllRoles(String token) {
		
		/*
		 * return restTemplate.exchange("http://localhost:8888/role/", HttpMethod.GET,
		 * HttpUtils.createRequestEntityWithHeadersAndToken(null,
		 * MediaType.APPLICATION_JSON, token), Response.class).getBody();
		 */
		
		RequestEntity requestEntity = HttpUtils.createRequestEntity(HttpMethod.GET, MediaType.APPLICATION_JSON, token, "http://localhost:8888/role/");
		ParameterizedTypeReference<Response<List<RoleDTO>>> responseType = new ParameterizedTypeReference<Response<List<RoleDTO>>>(){};
		return restTemplate.exchange(requestEntity, responseType).getBody();
	}
}
