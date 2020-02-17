package com.ishanitech.ipalikawebapp.serviceImpl;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ishanitech.ipalikawebapp.dto.FamilyMemberDTO;
import com.ishanitech.ipalikawebapp.dto.ResidentDTO;
import com.ishanitech.ipalikawebapp.dto.ResidentDetailDTO;
import com.ishanitech.ipalikawebapp.dto.Response;
import com.ishanitech.ipalikawebapp.service.ResidentService;
import com.ishanitech.ipalikawebapp.utilities.HttpUtils;

import lombok.extern.slf4j.Slf4j;

@Service
public class ResidentServiceImpl implements ResidentService {

	RestTemplate restTemplate;
	
	public ResidentServiceImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public Response<?> getResidentDataList(String token) {
		RequestEntity requestEntity = HttpUtils.createRequestEntity(HttpMethod.GET, null, MediaType.APPLICATION_JSON, token, "http://localhost:8888/resident");
		ParameterizedTypeReference<Response<List<ResidentDTO>>> responseType = new ParameterizedTypeReference<Response<List<ResidentDTO>>>() {};
		Response<List<ResidentDTO>> residents = restTemplate.exchange(requestEntity, responseType).getBody();
		return residents;
	}



	@Override
	public Response<?> getResidentFullDetail(String filledId, String token) {
		RequestEntity requestEntity = HttpUtils.createRequestEntity(HttpMethod.GET, null, MediaType.APPLICATION_JSON, token, "http://localhost:8888/resident/detail/" + filledId);
		ParameterizedTypeReference<Response<ResidentDetailDTO>> responseType = new ParameterizedTypeReference<Response<ResidentDetailDTO>>() {};
		Response<ResidentDetailDTO> fullDetail = restTemplate.exchange(requestEntity, responseType).getBody();
		return fullDetail;
	}

	@Override
	public void addFamilyMember(FamilyMemberDTO familyMemberInfo, String token) {
		RequestEntity requestEntity = HttpUtils.createRequestEntity(HttpMethod.POST, familyMemberInfo, MediaType.APPLICATION_JSON, token, "http://localhost:8888/resident/member/");
		restTemplate.postForObject("http://localhost:8888/resident/member/", requestEntity, String.class);
		
	}

}
