package com.ishanitech.ipalikawebapp.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ishanitech.ipalikawebapp.dto.ResidentDTO;
import com.ishanitech.ipalikawebapp.dto.Response;
import com.ishanitech.ipalikawebapp.service.ResidentService;

@RequestMapping("/admin")
@Controller
public class AdminController {

	private ResidentService residentService;

	public AdminController(ResidentService residentService) {
		super();
		this.residentService = residentService;
	}

	@GetMapping
	public String getDashboardView() {
		return "admin/dashboard";
	}

	@GetMapping("/addHouseholdForm")
	public String getHouseholdEntryForm() {
		return "admin/add-household";
	}

	@GetMapping("/addMemberForm")
	public String getMemberEntryForm() {
		return "admin/add-member";
	}

	@GetMapping("/userSettings")
	public String getUserSettingsView() {
		return "admin/user-settings";
	}

	@GetMapping("/userProfile")
	public String getUserProfileView() {
		return "admin/user-profile";
	}

	@GetMapping("/residentData")
	public String getResidentDataList(Model model) {
		Response<List<ResidentDTO>> residentResponse = (Response<List<ResidentDTO>>) residentService
				.getResidentDataList();

		model.addAttribute("residentList", residentResponse.getData());
		return "admin/resident-data";
	}

	@GetMapping("/residentMember/{filledFormId}")
	public String getResidentMemberList(@PathVariable("filledFormId") Integer filledFormId, Model model) {
		return "admin/resident-members";
	}

}
