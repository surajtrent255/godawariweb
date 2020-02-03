package com.ishanitech.ipalikawebapp.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ishanitech.ipalikawebapp.dto.ResidentDTO;
import com.ishanitech.ipalikawebapp.dto.ResidentDetailDTO;
import com.ishanitech.ipalikawebapp.dto.Response;
import com.ishanitech.ipalikawebapp.service.ResidentService;

@Service
public class ResidentServiceImpl implements ResidentService {

	RestTemplate restTemplate;
	
	public ResidentServiceImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public Response<?> getResidentDataList() {
		Response<List<ResidentDTO>> residents = restTemplate.getForObject("http://localhost:8888/resident", Response.class);
		return residents;
	}



	@Override
	public Response<?> getResidentFullDetail(String filledId) {
		Response<ResidentDetailDTO> fullDetail = restTemplate.getForObject("http://localhost:8888/resident/detail/" + filledId, Response.class);
		return fullDetail;
	}

}
