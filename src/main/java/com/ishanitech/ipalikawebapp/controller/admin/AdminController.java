package com.ishanitech.ipalikawebapp.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ishanitech.ipalikawebapp.dto.AnswerDTO;
import com.ishanitech.ipalikawebapp.dto.FavouritePlaceDTO;
import com.ishanitech.ipalikawebapp.dto.ResidentDTO;
import com.ishanitech.ipalikawebapp.dto.ResidentDetailDTO;
import com.ishanitech.ipalikawebapp.dto.Response;
import com.ishanitech.ipalikawebapp.service.FormService;
import com.ishanitech.ipalikawebapp.service.FavouritePlacesService;
import com.ishanitech.ipalikawebapp.service.ResidentService;

@RequestMapping("/admin")
@Controller
public class AdminController {

	private final ResidentService residentService;
	private final FormService formService;
	private final FavouritePlacesService favouritePlacesService;

	
	
	public AdminController(ResidentService residentService, FavouritePlacesService favouritePlacesService, FormService formService) {
		this.residentService = residentService;
		this.favouritePlacesService = favouritePlacesService;
		this.formService = formService;
	}

	@GetMapping
	public String getDashboardView() {
		return "admin/dashboard";
	}

	@GetMapping("/addHouseholdForm")
	public String getHouseholdEntryForm(Model model) {
		model.addAttribute("answer", new AnswerDTO());
		model.addAttribute("questionAndOptions", formService.getFullFormDetailById(1));
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
	public String getResidentMemberList(@PathVariable("filledFormId") String filledId, Model model) {
		Response<ResidentDetailDTO> residentResponse = (Response<ResidentDetailDTO>) residentService.getResidentFullDetail(filledId);
		model.addAttribute("residentFullDetail", residentResponse.getData());
		return "admin/resident-details";
	}
	
	@GetMapping("/favouritePlaceView")
	public String getFavouritePlaeView(Model model) {
		Response<List<FavouritePlaceDTO>> favouritePlaceResponse = favouritePlacesService.getAllFavouritePlaces();
		model.addAttribute("favouritePlaceList", favouritePlaceResponse.getData());
		return "admin/favourite-place";
	}

}
