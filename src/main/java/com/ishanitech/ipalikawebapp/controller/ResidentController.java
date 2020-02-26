package com.ishanitech.ipalikawebapp.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ishanitech.ipalikawebapp.dto.FamilyMemberDTO;
import com.ishanitech.ipalikawebapp.dto.ResidentDTO;
import com.ishanitech.ipalikawebapp.dto.ResidentDetailDTO;
import com.ishanitech.ipalikawebapp.dto.Response;
import com.ishanitech.ipalikawebapp.dto.UserDTO;
import com.ishanitech.ipalikawebapp.service.ResidentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/resident")
@Controller
public class ResidentController {
	
	private final ResidentService residentService;
	
	public ResidentController(ResidentService residentService) {
		this.residentService = residentService;
	}
	
	@Secured({"ROLE_CENTRAL_ADMIN", "ROLE_WARD_ADMIN", "SURVEYOR"})
	@GetMapping
	public String getResidentDataListView(Model model, @AuthenticationPrincipal UserDTO user) {
		Response<List<ResidentDTO>> residentResponse = (Response<List<ResidentDTO>>) residentService
				.getResidentDataList(user.getToken());
		model.addAttribute("residentList", residentResponse.getData());
		return "private/common/resident-data";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(consumes = "application/json")
	public @ResponseBody Response<String> addFamilyMember(@RequestBody FamilyMemberDTO familyMemberInfo, @AuthenticationPrincipal UserDTO user) {
		Date presentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        familyMemberInfo.setMemberId(dateFormat.format(presentDate));
		residentService.addFamilyMember(familyMemberInfo, user.getToken());
		return new Response<String>("Member successfully added!");
	}
	
	@Secured({"ROLE_CENTRAL_ADMIN", "ROLE_WARD_ADMIN", "SURVEYOR"})
	@GetMapping("/{filledFormId}")
	public String getResidentMemberList(@PathVariable("filledFormId") String filledId, Model model, @AuthenticationPrincipal UserDTO user) {
		Response<ResidentDetailDTO> residentResponse = (Response<ResidentDetailDTO>) residentService.getResidentFullDetail(filledId, user.getToken());
		model.addAttribute("residentFullDetail", residentResponse.getData());
		return "private/common/resident-details";
	}
	
	@Secured({"ROLE_CENTRAL_ADMIN", "ROLE_WARD_ADMIN", "SURVEYOR"})
	@PostMapping("/search")
	public @ResponseBody List<ResidentDTO> getResidentsBySearchKey(@RequestParam("searchKey") String searchKey, @AuthenticationPrincipal UserDTO user) {
		return residentService.searchResidentByKey(searchKey, user.getToken());
	}
	
	@GetMapping("/member/{memberId}")
	public String editMemberInfo(Model model, 
			@PathVariable("memberId") String memberId, 
			@AuthenticationPrincipal UserDTO user) {
		model.addAttribute("member", residentService.getMemberByMemberId(user.getToken(), memberId).getData());
		model.addAttribute("memberFormDetails", residentService.getMemberFormDetails(user.getToken()).getData());
		return "private/common/edit-member";
	}
	
	@Secured({"ROLE_CENTRAL_ADMIN", "ROLE_WARD_ADMIN", "SURVEYOR"})
	@GetMapping("/{residentFilledId}/member")
	public String getMemberEntryForm(@PathVariable ("residentFilledId") String residentFilledId,
			Model model, @AuthenticationPrincipal UserDTO user) {
		model.addAttribute("residentFilledId", residentFilledId);
		model.addAttribute("memberFormDetails", residentService.getMemberFormDetails(user.getToken()).getData());
		return "private/common/add-member";
	}
	
	@PutMapping("/{memberId}")
	public @ResponseBody Response<String> editFamilyMember(@RequestBody FamilyMemberDTO familyMemberInfo,@PathVariable("memberId") String memberId, @AuthenticationPrincipal UserDTO user) {
		residentService.editFamilyMemberInfo(familyMemberInfo, memberId, user.getToken());
		return new Response<String>("Member information update successfully!");
	}

}
