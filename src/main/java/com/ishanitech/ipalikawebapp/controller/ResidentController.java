package com.ishanitech.ipalikawebapp.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
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

	@ResponseStatus(HttpStatus.OK)
	@PostMapping(consumes = "application/json")
	public @ResponseBody Response<String> addFamilyMember(@RequestBody FamilyMemberDTO familyMemberInfo, @AuthenticationPrincipal UserDTO user) {
		Date presentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        familyMemberInfo.setMemberId(dateFormat.format(presentDate));
		residentService.addFamilyMember(familyMemberInfo, user.getToken());
		return new Response<String>("Member successfully added!");
	}
	
	@PostMapping("/search")
	public @ResponseBody List<ResidentDTO> getResidentsBySearchKey(@RequestParam("searchKey") String searchKey, @AuthenticationPrincipal UserDTO user) {
		return residentService.searchResidentByKey(searchKey, user.getToken());
	}
	
	@PutMapping("/{memberId}")
	public @ResponseBody Response<String> editFamilyMember(@RequestBody FamilyMemberDTO familyMemberInfo,@PathVariable("memberId") String memberId, @AuthenticationPrincipal UserDTO user) {
		residentService.editFamilyMemberInfo(familyMemberInfo, memberId, user.getToken());
		return new Response<String>("Member information update successfully!");
	}

}
