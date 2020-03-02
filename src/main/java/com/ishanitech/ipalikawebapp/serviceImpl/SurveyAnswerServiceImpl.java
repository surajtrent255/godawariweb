package com.ishanitech.ipalikawebapp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ishanitech.ipalikawebapp.configs.properties.RestApiProperties;
import com.ishanitech.ipalikawebapp.dto.AnswerDTO;
import com.ishanitech.ipalikawebapp.dto.FavouritePlaceDTO;
import com.ishanitech.ipalikawebapp.service.SurveyAnswerService;
import com.ishanitech.ipalikawebapp.utilities.HttpUtils;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class SurveyAnswerServiceImpl implements SurveyAnswerService {
	
	private final RestTemplate restTemplate;
	private final RestApiProperties restApiProperties;
	private final String ADD_HOUSEHOLD_BASE_URL = "/survey-answer";
		

	public SurveyAnswerServiceImpl(RestTemplate restTemplate, RestApiProperties restApiProperties) {
		super();
		this.restTemplate = restTemplate;
		this.restApiProperties = restApiProperties;
	}


	@Override
	public void addHouseholdSurveyAnswer(AnswerDTO answerDto, String token) {
		String template = String.format("%s/new", ADD_HOUSEHOLD_BASE_URL);
		String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
		List<AnswerDTO> answerDtos = new ArrayList<AnswerDTO>();
		answerDtos.add(answerDto);
		RequestEntity<List<AnswerDTO>> requestEntity = HttpUtils.createRequestEntity(HttpMethod.POST, answerDtos, MediaType.APPLICATION_JSON, token, url);
		restTemplate.exchange(requestEntity, String.class);
		
	}

}
