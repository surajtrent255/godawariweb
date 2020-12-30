package com.ishanitech.ipalikawebapp.service;

import java.util.List;

import com.ishanitech.ipalikawebapp.dto.AgriculturalFarmDTO;
import com.ishanitech.ipalikawebapp.dto.BeekeepingDTO;
import com.ishanitech.ipalikawebapp.dto.ExtraReport;
import com.ishanitech.ipalikawebapp.dto.FavouritePlaceReport;
import com.ishanitech.ipalikawebapp.dto.PopulationReport;
import com.ishanitech.ipalikawebapp.dto.QuestionReport;
import com.ishanitech.ipalikawebapp.dto.Response;

public interface ReportService {
	List<PopulationReport> getPopulationReport(String token);
	List<QuestionReport> getQuestionReport(String token);
	List<FavouritePlaceReport> getFavPlaceReport(String token);
	void generateReport(String token);
	List<ExtraReport> getExtraReport(String token);
	Response<List<BeekeepingDTO>> getBeekeepingInfo(String token);
	Response<List<AgriculturalFarmDTO>> getAgriculturalFarmInfo(String token);
}
