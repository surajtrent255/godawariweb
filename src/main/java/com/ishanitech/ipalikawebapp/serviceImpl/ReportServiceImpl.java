package com.ishanitech.ipalikawebapp.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ishanitech.ipalikawebapp.dto.PopulationReport;
import com.ishanitech.ipalikawebapp.dto.QuestionReport;
import com.ishanitech.ipalikawebapp.dto.Response;
import com.ishanitech.ipalikawebapp.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {
	private final RestTemplate restTemplate;

	public ReportServiceImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public List<PopulationReport> getPopulationReport() {
		return (List<PopulationReport>) restTemplate.getForObject("http://localhost:8888/report/population", Response.class).getData();
	}

	@Override
	public List<QuestionReport> getQuestionReport() {
		return (List<QuestionReport>) restTemplate.getForObject("http://localhost:8888/report/question", Response.class).getData();
	}
	
	
}
