package com.ishanitech.ipalikawebapp.controller;

import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ishanitech.ipalikawebapp.dto.Response;
import com.ishanitech.ipalikawebapp.dto.UserDTO;
import com.ishanitech.ipalikawebapp.dto.WardDTO;
import com.ishanitech.ipalikawebapp.service.WardService;


@RequestMapping("/ward")
@Controller
public class WardController {
	private final WardService wardService;

	public WardController(WardService wardService) {
		this.wardService = wardService;
	}

	
	@Secured({"ROLE_CENTRAL_ADMIN"})
	@GetMapping
	public String getWardListView(@AuthenticationPrincipal UserDTO user, Model model) {
		Response<List<WardDTO>> wardResponse = wardService.getAllWardInfo(user.getToken());
		model.addAttribute("wardList", wardResponse.getData());
		return "private/central-admin/ward-list";
	}
	
	@Secured({"ROLE_CENTRAL_ADMIN"})
	@GetMapping("/add")
	public String getWardEntryView() {
		return "private/central-admin/add-ward";
	}

	@Secured({"ROLE_CENTRAL_ADMIN"})
	@PostMapping
	public @ResponseBody Response<String> addWard(@RequestBody WardDTO wardInfo, @AuthenticationPrincipal UserDTO user) {
		wardService.addWard(wardInfo, user.getToken());
		return new Response<String>("Ward successfully added!");
	}
	
	@GetMapping("/{wardNumber}")
	public String getWardInfoByWardNumber(@PathVariable("wardNumber") int wardNo, @AuthenticationPrincipal UserDTO user, Model model) {
		if(user.getRoles().contains("WARD_ADMIN")) {
			Response<WardDTO> wardResponse = wardService.getWardByWardNumber(user.getWardNo(), user.getToken());
			model.addAttribute("wardInfo", wardResponse.getData());
		} else {
			Response<WardDTO> wardResponse = wardService.getWardByWardNumber(wardNo, user.getToken());
			model.addAttribute("wardInfo", wardResponse.getData());
		}
		return "private/common/ward-details";
	}
	
	@DeleteMapping("/{wardNumber}")
	public @ResponseBody Response<String> deleteWard(@PathVariable("wardNumber") int wardNo, @AuthenticationPrincipal UserDTO user) {
		wardService.deleteWard(wardNo, user.getToken());  
		return new Response<String>("Ward removed successfully!");
	}
	
	@Secured({"ROLE_CENTRAL_ADMIN", "ROLE_WARD_ADMIN", "ROLE_SUPER_ADMIN"})
	@GetMapping("/edit/{wardNumber}")
	public String getWardEditView(@PathVariable("wardNumber") int wardNo, @AuthenticationPrincipal UserDTO user, Model model) {
		if(user.getRoles().contains("WARD_ADMIN")) {
			Response<WardDTO> wardResponse = wardService.getWardByWardNumber(user.getWardNo(), user.getToken());
			model.addAttribute("wardInfo", wardResponse.getData());
		} else {
			Response<WardDTO> wardResponse = wardService.getWardByWardNumber(wardNo, user.getToken());
			model.addAttribute("wardInfo", wardResponse.getData());
		}
		return "private/common/edit-ward";
	}
	
	@Secured({"ROLE_CENTRAL_ADMIN", "ROLE_WARD_ADMIN", "ROLE_SUPER_ADMIN"})
	@PutMapping("/{wardNumber}")
	public @ResponseBody Response<String> editWard(@RequestBody WardDTO wardInfo, @PathVariable("wardNumber") int wardNo, @AuthenticationPrincipal UserDTO user) {
		if(user.getRoles().contains("WARD_ADMIN")) {
			wardService.editWard(wardInfo, user.getWardNo(), user.getToken());
		} else {
		wardService.editWard(wardInfo, wardNo, user.getToken());
		}
		return new Response<String>("Ward details edited successfully");
	}
	
}
