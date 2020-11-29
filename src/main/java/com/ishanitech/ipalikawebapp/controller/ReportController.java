/**
 * @author Umesh Bhujel <yoomesbhujel@gmail.com>
 * Since Feb 26, 2020
 */
package com.ishanitech.ipalikawebapp.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ishanitech.ipalikawebapp.dto.AgriculturalFarmDTO;
import com.ishanitech.ipalikawebapp.dto.BeekeepingDTO;
import com.ishanitech.ipalikawebapp.dto.PopulationReport;
import com.ishanitech.ipalikawebapp.dto.Response;
import com.ishanitech.ipalikawebapp.dto.UserDTO;
import com.ishanitech.ipalikawebapp.service.ReportService;

@RequestMapping("/report")
@Controller
public class ReportController {
	private final ReportService reportService;
	
	public ReportController(ReportService reportService) {
		this.reportService = reportService;
	}

	@GetMapping
	public String getDashboardView(Model model, @AuthenticationPrincipal UserDTO user) {
		List<PopulationReport> populationReport = reportService.getPopulationReport(user.getToken());
				
		if(!populationReport.isEmpty() && populationReport.size() > 0) {
			// get(0) point to infants, totalChildrens, totalYouths, totalMidage, totalOldAge, and totalSeniorCitizens
			double totalInfants = populationReport.get(0).getOption1();
			double totalChildrens = populationReport.get(0).getOption2();
			double totalYouths = populationReport.get(0).getOption3();
			double totalMidage = populationReport.get(0).getOption4();;
			double totalOldage = populationReport.get(0).getOption5();
			double totalSeniorCitizens = populationReport.get(0).getOption6();
			
			// get(1) always point to male female and total population 
			double totalMale = populationReport.get(1).getOption1();
			double totalFemale = populationReport.get(1).getOption2();
			double totalOthers = populationReport.get(1).getOption3(); 
			
			model.addAttribute("populationReport", populationReport);
			model.addAttribute("totalInfants", totalInfants);
			model.addAttribute("totalChildrens", totalChildrens);
			model.addAttribute("totalYouths", totalYouths);
			model.addAttribute("totalMidage", totalMidage);
			model.addAttribute("totalOldage", totalOldage);
			model.addAttribute("totalSeniorCitizens", totalSeniorCitizens);
			model.addAttribute("totalMale", totalMale);
			model.addAttribute("totalFemale", totalFemale);
			model.addAttribute("totalOthers", totalOthers);
		}
		model.addAttribute("questionReport", reportService.getQuestionReport(user.getToken()));
		model.addAttribute("extraReport", reportService.getExtraReport(user.getToken()));
		return "private/common/dashboard";
	}
	
	@PostMapping
	public String generateReport(@AuthenticationPrincipal UserDTO user) {
		reportService.generateReport(user.getToken());
		return "private/common/dashboard";
	}
	
	
	@GetMapping("/beekeeping")
	public String getBeekeepingReportView(@AuthenticationPrincipal UserDTO user, Model model) {
		Response<List<BeekeepingDTO>> reportResponse = reportService.getBeekeepingInfo(user.getToken());
		model.addAttribute("beekeepingList", reportResponse.getData());
		return "private/common/report-beekeeping";
	}
	
	@GetMapping("/agriculturalFarm")
	public String getAgriculturalFarmReportView(@AuthenticationPrincipal UserDTO user, Model model) {
		Response<List<AgriculturalFarmDTO>> reportResponse = reportService.getAgriculturalFarmInfo(user.getToken());
		model.addAttribute("agriculturalFarmList", reportResponse.getData());
		return "private/common/report-agricultural-farm";
	}
	
	@GetMapping("/agriculturalCrop")
	public String getAgriculturalCropReportView(@AuthenticationPrincipal UserDTO user, Model model) {
		model.addAttribute("agriculturalCropReport", reportService.getQuestionReport(user.getToken()));
		return "private/common/report-agricultural-crop";
	}
	
	@GetMapping("/animals")
	public String getAnimalsReportView(@AuthenticationPrincipal UserDTO user, Model model) {
		model.addAttribute("animalsReport", reportService.getQuestionReport(user.getToken()));
		return "private/common/report-animal";
	}
	
}
