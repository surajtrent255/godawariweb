package com.ishanitech.ipalikawebapp.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
