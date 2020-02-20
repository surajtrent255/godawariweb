package com.ishanitech.ipalikawebapp.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ishanitech.ipalikawebapp.configs.properties.RestApiProperties;
import com.ishanitech.ipalikawebapp.dto.FamilyMemberDTO;
import com.ishanitech.ipalikawebapp.dto.MemberFormDetailsDTO;
import com.ishanitech.ipalikawebapp.dto.ResidentDTO;
import com.ishanitech.ipalikawebapp.dto.ResidentDetailDTO;
import com.ishanitech.ipalikawebapp.dto.Response;
import com.ishanitech.ipalikawebapp.service.ResidentService;
import com.ishanitech.ipalikawebapp.utilities.HttpUtils;

@Service
public class ResidentServiceImpl implements ResidentService {
	private final String RESIDENT_BASE_URL = "resident";
	private final RestTemplate restTemplate;
	private final RestApiProperties restApiProperties;
	public ResidentServiceImpl(RestTemplate restTemplate, RestApiProperties restApiProperties) {
		this.restTemplate = restTemplate;
		this.restApiProperties = restApiProperties;
	}

	@Override
	public Response<?> getResidentDataList(String token) {
		String template = String.format("%s", RESIDENT_BASE_URL);
		String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
		RequestEntity<?> requestEntity = HttpUtils.createRequestEntity(HttpMethod.GET, null, MediaType.APPLICATION_JSON, token, url);
		ParameterizedTypeReference<Response<List<ResidentDTO>>> responseType = new ParameterizedTypeReference<Response<List<ResidentDTO>>>() {};
		Response<List<ResidentDTO>> residents = restTemplate.exchange(requestEntity, responseType).getBody();
		return residents;
	}

	@Override
	public Response<?> getResidentFullDetail(String filledId, String token) {
		String template = String.format("%s/detail/{filledId}", RESIDENT_BASE_URL);
		Map<String, Object> urlValues = new HashMap<>();
		urlValues.put("filledId", filledId);
		String url = HttpUtils.createRequestUrl(restApiProperties, template, urlValues);
		RequestEntity<?> requestEntity = HttpUtils.createRequestEntity(HttpMethod.GET, null, MediaType.APPLICATION_JSON, token, url);
		ParameterizedTypeReference<Response<ResidentDetailDTO>> responseType = new ParameterizedTypeReference<Response<ResidentDetailDTO>>() {};
		Response<ResidentDetailDTO> fullDetail = restTemplate.exchange(requestEntity, responseType).getBody();
		return fullDetail;
	}

	@Override
	public void addFamilyMember(FamilyMemberDTO familyMemberInfo, String token) {
		String template = String.format("%s/member", RESIDENT_BASE_URL);
		String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
		RequestEntity<?> requestEntity = HttpUtils.createRequestEntity(HttpMethod.POST, familyMemberInfo, MediaType.APPLICATION_JSON, token, url);
		//restTemplate.postForObject(url, requestEntity, String.class);
		ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {
		};
		restTemplate.exchange(requestEntity, responseType);
	}

	@Override
	public Response<?> getMemberFormDetails(String token) {
		String template = String.format("%s/memberFormDetails", RESIDENT_BASE_URL);
		String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
		RequestEntity<?> requestEntity = HttpUtils.createRequestEntity(HttpMethod.GET, MediaType.APPLICATION_JSON, token, url);
		ParameterizedTypeReference<Response<MemberFormDetailsDTO>> responseType = new ParameterizedTypeReference<Response<MemberFormDetailsDTO>>() {
		};
		return restTemplate.exchange(requestEntity, responseType).getBody();
	}

	@Override
	public List<ResidentDTO> searchResidentByKey(String searchKey, String token) {
		String template = String.format("%s/search", RESIDENT_BASE_URL);
		Map<String, Object> uriVariables = new HashMap<String, Object>();
		uriVariables.put("rootAddress", template);
		uriVariables.put("queryParamName", "searchKey");
		uriVariables.put("keyword", searchKey);
		String url = HttpUtils.createRequestUrlWithQueryString(restApiProperties, uriVariables);
		RequestEntity<?> requestEntity = HttpUtils.createRequestEntity(HttpMethod.POST, MediaType.APPLICATION_JSON, token, url);
		ParameterizedTypeReference<Response<List<ResidentDTO>>> responseType = new ParameterizedTypeReference<Response<List<ResidentDTO>>>() {
		};
		List<ResidentDTO> residents = restTemplate.exchange(requestEntity, responseType).getBody().getData();
		return residents;
	}

}
