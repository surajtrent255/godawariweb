package com.ishanitech.ipalikawebapp.controller.admin;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ishanitech.ipalikawebapp.dto.AnswerDTO;
import com.ishanitech.ipalikawebapp.dto.ResidentDTO;
import com.ishanitech.ipalikawebapp.dto.ResidentDetailDTO;
import com.ishanitech.ipalikawebapp.dto.Response;
import com.ishanitech.ipalikawebapp.dto.UserDTO;
import com.ishanitech.ipalikawebapp.service.FormService;
import com.ishanitech.ipalikawebapp.service.ReportService;
import com.ishanitech.ipalikawebapp.service.ResidentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/super-admin")
@Controller
public class SuperAdminController {

	private final ResidentService residentService;
	private final FormService formService;
	private final ReportService reportService;

	public SuperAdminController(ResidentService residentService, 
			FormService formService,
			ReportService reportService) {
		this.residentService = residentService;
		this.formService = formService;
		this.reportService = reportService;
	}

	@GetMapping
	public String getDashboardView(Model model, @AuthenticationPrincipal UserDTO user) {
		model.addAttribute("populationReport", reportService.getPopulationReport(user.getToken()));
		model.addAttribute("questionReport", reportService.getQuestionReport(user.getToken()));
		return "private/super-admin/dashboard";
	}

	@GetMapping("/addHouseholdForm")
	public String getHouseholdEntryForm(Model model, @AuthenticationPrincipal UserDTO user) {
		model.addAttribute("answerObj", new AnswerDTO());
		model.addAttribute("questionAndOptions", formService.getFullFormDetailById(1, user.getToken()));
		model.addAttribute("districts", formService.getListofDistricts(user.getToken()).getData());
		
		return "private/super-admin/add-household";
	}

	@GetMapping("/addMemberForm/{residentFilledId}")
	public String getMemberEntryForm(@PathVariable ("residentFilledId") String residentFilledId,
			Model model, @AuthenticationPrincipal UserDTO user) {
		model.addAttribute("residentFilledId", residentFilledId);
		model.addAttribute("memberFormDetails", residentService.getMemberFormDetails(user.getToken()).getData());
		return "private/super-admin/add-member";
	}
	
	@GetMapping("/editMemberView/{memberId}")
	public String editMemberInfo(Model model, 
			@PathVariable("memberId") String memberId, 
			@AuthenticationPrincipal UserDTO user) {
		model.addAttribute("member", residentService.getMemberByMemberId(user.getToken(), memberId).getData());
		model.addAttribute("memberFormDetails", residentService.getMemberFormDetails(user.getToken()).getData());
		return "private/super-admin/edit-member";
	}

}
