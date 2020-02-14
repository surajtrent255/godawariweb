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
import com.ishanitech.ipalikawebapp.service.ReportService;
import com.ishanitech.ipalikawebapp.service.FavouritePlacesService;
import com.ishanitech.ipalikawebapp.service.ResidentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/super-admin")
@Controller
public class SuperAdminController {

	private final ResidentService residentService;
	private final FormService formService;
	private final FavouritePlacesService favouritePlacesService;
	private final ReportService reportService;

	
	
	public SuperAdminController(ResidentService residentService, 
			FavouritePlacesService favouritePlacesService, 
			FormService formService,
			ReportService reportService) {
		this.residentService = residentService;
		this.favouritePlacesService = favouritePlacesService;
		this.formService = formService;
		this.reportService = reportService;
	}

	@GetMapping
	public String getDashboardView(Model model) {
		model.addAttribute("populationReport", reportService.getPopulationReport());
		model.addAttribute("questionReport", reportService.getQuestionReport());
		return "admin/dashboard";
	}

	@GetMapping("/addHouseholdForm")
	public String getHouseholdEntryForm(Model model) {
		model.addAttribute("answer", new AnswerDTO());
		model.addAttribute("questionAndOptions", formService.getFullFormDetailById(1));
		return "admin/add-household";
	}

	@GetMapping("/addMemberForm/{residentFilledId}")
	public String getMemberEntryForm(@PathVariable ("residentFilledId") String residentFilledId, Model model) {
		model.addAttribute("residentFilledId", residentFilledId);
		model.addAttribute("memberFormDetails", residentService.getMemberFormDetails().getData());
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
	public String getFavouritePlaceView(Model model) {
		Response<List<FavouritePlaceDTO>> favouritePlaceResponse = favouritePlacesService.getAllFavouritePlaces();
		model.addAttribute("favouritePlaceList", favouritePlaceResponse.getData());
		return "admin/favourite-place";
	}
	
	@GetMapping("/favouritePlaceDetails/{placeId}")
	public String getFavouritePlaceByPlaceId(Model model, @PathVariable("placeId") String placeId) {
		Response<FavouritePlaceDTO> favouritePlaceResponse = (Response<FavouritePlaceDTO>) favouritePlacesService.getFavouritePlaceByPlaceId(placeId);
		model.addAttribute("favouritePlaceInfo", favouritePlaceResponse.getData());
		return "admin/favourite-place-details";
	}
	
	@GetMapping("/favouritePlaceAdd")
	public String getFavouritePlaceEntryView(Model model) {
		
		model.addAttribute("placeTypes", favouritePlacesService.getTypesofFavourtiePlaces());
		model.addAttribute("favPlaceObj", new FavouritePlaceDTO());
		return "admin/add-favourite-place";
	}

}
