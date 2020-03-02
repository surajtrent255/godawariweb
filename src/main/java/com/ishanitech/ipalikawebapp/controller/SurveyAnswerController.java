package com.ishanitech.ipalikawebapp.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import com.ishanitech.ipalikawebapp.dto.AnswerDTO;
import com.ishanitech.ipalikawebapp.dto.UserDTO;
import com.ishanitech.ipalikawebapp.service.FormService;
import com.ishanitech.ipalikawebapp.service.SurveyAnswerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/survey-answer")
@Controller
public class SurveyAnswerController {
	private final FormService formService;
	private final SurveyAnswerService surveyAnswerService;

	public SurveyAnswerController(SurveyAnswerService surveyAnswerService,
			FormService formService) {
		this.surveyAnswerService = surveyAnswerService;
		this.formService = formService;
	}
	
	@GetMapping("/household")
	public String getHouseholdEntryForm(Model model, @AuthenticationPrincipal UserDTO user) {
		model.addAttribute("answerObj", new AnswerDTO());
		model.addAttribute("questionAndOptions", formService.getFullFormDetailById(1, user.getToken()));
		model.addAttribute("districts", formService.getListofDistricts(user.getToken()).getData());
		model.addAttribute("wards", formService.getListOfWards(user.getToken()).getData());
		return "private/common/add-household";
	}
	
	@PostMapping
	public void addSurveyAnswer(@RequestParam(value = "ownerPhoto") MultipartFile ownerPhotoFile, @RequestParam(value="documentPhoto") MultipartFile documentPhotoFile, @ModelAttribute(value= "answerObj") AnswerDTO surveyAnswerInfo) {
		Date presentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        surveyAnswerInfo.setFilledId(dateFormat.format(presentDate));
        
        String houseOnwerPhoto = "JPEG_" + surveyAnswerInfo.getFilledId() + "1_house_owner_photo" + ".JPG";  
        surveyAnswerInfo.setAnswer47(houseOnwerPhoto);
        
        String documentPhoto = "JPEG_" + surveyAnswerInfo.getFilledId() + "1_document_photo" + ".JPG"; 
        surveyAnswerInfo.setAnswer49(documentPhoto);
	}
	
	@PostMapping("/household")
	public @ResponseBody
    int addHouseHold(@RequestBody AnswerDTO answerDto, @AuthenticationPrincipal UserDTO user) {
        System.out.println(answerDto.toString());
        Date presentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        answerDto.setFilledId(dateFormat.format(presentDate));
        answerDto.setEntryDate(LocalDateTime.now().toString());
       
        try {
        	surveyAnswerService.addHouseholdSurveyAnswer(answerDto, user.getToken());
        	return 1;
        }catch(Exception e) {
        	e.printStackTrace();
        	log.info(e.getMessage());
        	return 0;
        }
        //return 1;
    }
}
