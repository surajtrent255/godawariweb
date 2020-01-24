package com.ishanitech.ipalikawebapp.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ishanitech.ipalikawebapp.dto.ResidentDTO;
import com.ishanitech.ipalikawebapp.dto.Response;
import com.ishanitech.ipalikawebapp.service.ResidentService;
import com.ishanitech.ipalikawebapp.service.RestClientService;

@Service
public class ResidentServiceImpl implements ResidentService {

	private final String RESIDENT_BASE_URL = "/resident";
	
	private RestClientService restClientService;
	
	
	
	public ResidentServiceImpl(RestClientService restClientService) {
		super();
		this.restClientService = restClientService;
	}



	@Override
	public Response<?> getResidentDataList() {
		ObjectMapper objectMapper = new ObjectMapper();
		JavaType responseType = objectMapper.getTypeFactory().constructParametricType(Response.class, objectMapper.getTypeFactory().constructCollectionType(List.class, ResidentDTO.class));
		Response<?> response = null;
		response = restClientService.getData(RESIDENT_BASE_URL, responseType);
		return response;
	}

}
