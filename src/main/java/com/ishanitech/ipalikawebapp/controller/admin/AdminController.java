package com.ishanitech.ipalikawebapp.controller.admin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/admin")
@Controller
public class AdminController {
	@GetMapping
	public String getDashboardView() {
		return "admin/dashboard";
	}
	
//	@GetMapping("/dataInsert")
//	public String getDataEntry() {
//		return "admin/data-insert";
//	}
	
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
	public String getResidentDataList() {
		return "admin/resident-data";
	}
	
	@GetMapping("/residentMembers")
	public String getResidentMemberList() {
		return "admin/resident-members";
	}
	
}
