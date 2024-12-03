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
	List<PopulationReport> getPopulationReport(int wardNo);
	List<QuestionReport> getQuestionReport(int wardNo);
	List<FavouritePlaceReport> getFavPlaceReport(int wardNo);
	void generateReport(int wardNo, String token);
	List<ExtraReport> getExtraReport(int wardNo);
	Response<List<BeekeepingDTO>> getBeekeepingInfo(int wardNo);
	Response<List<AgriculturalFarmDTO>> getAgriculturalFarmInfo(int wardNo);
}
