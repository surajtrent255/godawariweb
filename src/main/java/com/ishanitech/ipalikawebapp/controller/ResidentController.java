package com.ishanitech.ipalikawebapp.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ishanitech.ipalikawebapp.dto.FamilyMemberDTO;
import com.ishanitech.ipalikawebapp.service.ResidentService;

import lombok.experimental.Accessors;
import lombok.experimental.Helper;

@RequestMapping("/resident")
@Controller
public class ResidentController {
	
	private final ResidentService residentService;
	
	public ResidentController(ResidentService residentService) {
		this.residentService = residentService;
	}




	@PostMapping(value = "/add", consumes = "application/json")
	public void addFamilyMember(@RequestBody FamilyMemberDTO familyMemberInfo) {
		
		Date presentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        familyMemberInfo.setMemberId(dateFormat.format(presentDate));
        
		residentService.addFamilyMember(familyMemberInfo);
	}

}
