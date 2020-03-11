/**
 * @author Umesh Bhujel <yoomesbhujel@gmail.com>
 * Since Feb 26, 2020
 */
package com.ishanitech.ipalikawebapp.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
		model.addAttribute("populationReport", reportService.getPopulationReport(user.getToken()));
		model.addAttribute("questionReport", reportService.getQuestionReport(user.getToken()));
		return "private/common/dashboard";
	}
	
	@PostMapping
	public String generateReport(@AuthenticationPrincipal UserDTO user) {
		reportService.generateReport(user.getToken());
		return "private/common/dashboard";
	}
}
