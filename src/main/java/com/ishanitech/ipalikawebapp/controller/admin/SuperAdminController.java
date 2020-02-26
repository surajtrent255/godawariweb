package com.ishanitech.ipalikawebapp.controller.admin;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ishanitech.ipalikawebapp.dto.AnswerDTO;
import com.ishanitech.ipalikawebapp.dto.UserDTO;
import com.ishanitech.ipalikawebapp.service.FormService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/super-admin")
@Controller
public class SuperAdminController {
	private final FormService formService;

	public SuperAdminController(FormService formService) {
		this.formService = formService;
	}
	
	@GetMapping
	public String getAdminPage() {
		return "private/super-admin/index";
	}
	
	@GetMapping("/addHouseholdForm")
	public String getHouseholdEntryForm(Model model, @AuthenticationPrincipal UserDTO user) {
		model.addAttribute("answerObj", new AnswerDTO());
		model.addAttribute("questionAndOptions", formService.getFullFormDetailById(1, user.getToken()));
		model.addAttribute("districts", formService.getListofDistricts(user.getToken()).getData());
		return "private/super-admin/add-household";
	}
}
