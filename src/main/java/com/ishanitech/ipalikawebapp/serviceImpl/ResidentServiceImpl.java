package com.ishanitech.ipalikawebapp.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ishanitech.ipalikawebapp.dto.AnswerDTO;
import com.ishanitech.ipalikawebapp.dto.ResidentDTO;
import com.ishanitech.ipalikawebapp.dto.Response;
import com.ishanitech.ipalikawebapp.service.ResidentService;
import com.ishanitech.ipalikawebapp.service.RestClientService;

@Service
public class ResidentServiceImpl implements ResidentService {

	@Autowired
	RestTemplate restTemplate;
	
	private RestClientService restClientService;
	public ResidentServiceImpl(RestClientService restClientService) {
		super();
		this.restClientService = restClientService;
	}



	@Override
	public Response<?> getResidentDataList() {
		Response<List<ResidentDTO>> residents = restTemplate.getForObject("http://192.168.0.101:8888/resident", Response.class);
		return residents;
	}



	@Override
	public Response<?> getResidentFullDetail(String filledId) {
		Response<AnswerDTO> fullDetail = restTemplate.getForObject("http://192.168.0.101:8888/resident/detail/" + filledId, Response.class);
		return fullDetail;
	}

}
