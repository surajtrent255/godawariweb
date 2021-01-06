/**
 * @author Umesh Bhujel <yoomesbhujel@gmail.com>
 * Since Feb 26, 2020
 */
package com.ishanitech.ipalikawebapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ishanitech.ipalikawebapp.dto.AgriculturalFarmDTO;
import com.ishanitech.ipalikawebapp.dto.BeekeepingDTO;
import com.ishanitech.ipalikawebapp.dto.PopulationReport;
import com.ishanitech.ipalikawebapp.dto.Response;
import com.ishanitech.ipalikawebapp.dto.UserDTO;
import com.ishanitech.ipalikawebapp.service.ReportService;
import com.ishanitech.ipalikawebapp.service.WardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/report")
@Controller
public class ReportController {
	private final ReportService reportService;
	private final WardService wardService;
	
	public ReportController(ReportService reportService, WardService wardService) {
		this.reportService = reportService;
		this.wardService = wardService;
	}

	
	@Secured({"ROLE_CENTRAL_ADMIN", "ROLE_WARD_ADMIN", "ROLE_SUPER_ADMIN"})
	@GetMapping
	public String getDashboardView(HttpServletRequest request, Model model, @AuthenticationPrincipal UserDTO user) {
		int wardNo = 0;
		String selectedWard = request.getParameter("selectedWard");
		
		if(user.getRoles().contains("WARD_ADMIN")) {
				selectedWard = String.valueOf(user.getWardNo()); 
				wardNo = Integer.parseInt(selectedWard);
		}
		
		if(user.getRoles().contains("CENTRAL_ADMIN") || user.getRoles().contains("SUPER_ADMIN")) {
				if(selectedWard == null) {
					wardNo = 0;
					selectedWard = "0";
				} else {
					wardNo = Integer.parseInt(selectedWard);
			}
		}
		
//		if(selectedWard == null) {
//			if(user.getRoles().contains("WARD_ADMIN")) {
//				wardNo = user.getWardNo();
//			} else if(user.getRoles().contains("SURVEYOR")) {
//				wardNo = user.getWardNo();
//				
//			} else {
//				wardNo = 0;
//			}
//		} else {		
//			wardNo = Integer.parseInt(selectedWard);
//		}

		
		model.addAttribute("wards", wardService.getAllWards());
		model.addAttribute("loggedInUserWard", user.getWardNo());
		model.addAttribute("selectedWard", selectedWard);
		
		List<PopulationReport> populationReport = reportService.getPopulationReport(wardNo, user.getToken());
		
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
		model.addAttribute("questionReport", reportService.getQuestionReport(wardNo, user.getToken()));
		model.addAttribute("extraReport", reportService.getExtraReport(wardNo, user.getToken()));
		model.addAttribute("favPlaceReport", reportService.getFavPlaceReport(wardNo, user.getToken()));
		return "private/common/dashboard";
	}
	
	@PostMapping("/{wardNo}")
	public String generateReport(@AuthenticationPrincipal UserDTO user, @PathVariable("wardNo") int wardNo) {
		log.info("wardNumber sent " + wardNo);
		reportService.generateReport(wardNo, user.getToken());
		return "private/common/dashboard";
	}
	
	
	@GetMapping("/beekeeping/{wardNo}")
	public String getBeekeepingReportView(@PathVariable("wardNo") int wardNo, @AuthenticationPrincipal UserDTO user, Model model) {
		Response<List<BeekeepingDTO>> reportResponse = reportService.getBeekeepingInfo(wardNo, user.getToken());
		model.addAttribute("beekeepingList", reportResponse.getData());
		return "private/common/report-beekeeping";
	}
	
	@GetMapping("/agriculturalFarm/{wardNo}")
	public String getAgriculturalFarmReportView(@PathVariable("wardNo") int wardNo, @AuthenticationPrincipal UserDTO user, Model model) {
		Response<List<AgriculturalFarmDTO>> reportResponse = reportService.getAgriculturalFarmInfo(wardNo, user.getToken());
		model.addAttribute("agriculturalFarmList", reportResponse.getData());
		return "private/common/report-agricultural-farm";
	}
	
	@GetMapping("/agriculturalCrop/{wardNo}")
	public String getAgriculturalCropReportView(@PathVariable("wardNo") int wardNo, @AuthenticationPrincipal UserDTO user, Model model) {
		model.addAttribute("agriculturalCropReport", reportService.getExtraReport(wardNo, user.getToken()));
		return "private/common/report-agricultural-crop";
	}
	
	@GetMapping("/animals/{wardNo}")
	public String getAnimalsReportView(@PathVariable("wardNo") int wardNo, @AuthenticationPrincipal UserDTO user, Model model) {
		model.addAttribute("animalsReport", reportService.getQuestionReport(wardNo, user.getToken()));
		return "private/common/report-animal";
	}
	
}
