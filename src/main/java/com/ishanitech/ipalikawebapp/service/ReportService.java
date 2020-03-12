package com.ishanitech.ipalikawebapp.service;

import java.util.List;

import com.ishanitech.ipalikawebapp.dto.PopulationReport;
import com.ishanitech.ipalikawebapp.dto.QuestionReport;

public interface ReportService {
	List<PopulationReport> getPopulationReport(String token);
	List<QuestionReport> getQuestionReport(String token);
	void generateReport(String token);
}
