package com.ishanitech.ipalikawebapp.serviceImpl;

import java.util.Collections;
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
import com.ishanitech.ipalikawebapp.dto.PopulationReport;
import com.ishanitech.ipalikawebapp.dto.QuestionReport;
import com.ishanitech.ipalikawebapp.dto.Response;
import com.ishanitech.ipalikawebapp.service.ReportService;
import com.ishanitech.ipalikawebapp.utilities.HttpUtils;

@Service
public class ReportServiceImpl implements ReportService {
	private final RestTemplate restTemplate;
	private final RestApiProperties restApiProperties;
	private final String REPORT_BASE = "report/";

	public ReportServiceImpl(RestTemplate restTemplate, RestApiProperties restApiProperties) {
		this.restTemplate = restTemplate;
		this.restApiProperties = restApiProperties;
	}

	@Override
	public List<PopulationReport> getPopulationReport(String token) {
		String template =  REPORT_BASE + "{populationReport}";
		String url = HttpUtils.createRequestUrl(restApiProperties, template, Collections.singletonMap("populationReport", "population"));
		RequestEntity request = HttpUtils.createRequestEntity(HttpMethod.GET, MediaType.APPLICATION_JSON, token, url);
		ParameterizedTypeReference<Response<List<PopulationReport>>> bean = new ParameterizedTypeReference<Response<List<PopulationReport>>>() {};
		return restTemplate.exchange(request, bean).getBody().getData();
	}

	@Override
	public List<QuestionReport> getQuestionReport(String token) {
		String template =  REPORT_BASE + "{questionReport}";
		String url = HttpUtils.createRequestUrl(restApiProperties, template, Collections.singletonMap("questionReport", "question"));
		RequestEntity request = HttpUtils.createRequestEntity(HttpMethod.GET, null, MediaType.APPLICATION_JSON, token, url);
		ParameterizedTypeReference<Response<List<QuestionReport>>> bean = new ParameterizedTypeReference<Response<List<QuestionReport>>>() {};
		return restTemplate.exchange(request, bean).getBody().getData();
	}

	@Override
	public void generateReport(String token) {
		String template = REPORT_BASE;
		String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
		RequestEntity requestEntity = HttpUtils.createRequestEntity(HttpMethod.POST, MediaType.APPLICATION_JSON, token, url);
		ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {};
		restTemplate.exchange(requestEntity, responseType);
	}
	
	
}
