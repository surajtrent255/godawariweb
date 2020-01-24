package com.ishanitech.ipalikawebapp.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ishanitech.ipalikawebapp.dto.ResidentDTO;
import com.ishanitech.ipalikawebapp.dto.Response;
import com.ishanitech.ipalikawebapp.service.ResidentService;
import com.ishanitech.ipalikawebapp.service.RestClientService;

@Service
public class ResidentServiceImpl implements ResidentService {

	@Autowired
	RestTemplate restTemplate;
	
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
//		Response<?> response = null;
		Response<List<ResidentDTO>> residents = restTemplate.getForObject("http://localhost:8888/resident", Response.class);
		
		return residents;
	}

}
