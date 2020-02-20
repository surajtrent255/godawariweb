package com.ishanitech.ipalikawebapp.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ishanitech.ipalikawebapp.dto.AnswerDTO;
import com.ishanitech.ipalikawebapp.service.SurveyAnswerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("survey-answer")
@Controller
public class SurveyAnswerController {

	private final SurveyAnswerService surveyAnswerService;

	public SurveyAnswerController(SurveyAnswerService surveyAnswerService) {
		this.surveyAnswerService = surveyAnswerService;
	}
	
	@PostMapping()
	public void addSurveyAnswer(@RequestParam(value = "ownerPhoto") MultipartFile ownerPhotoFile, @RequestParam(value="documentPhoto") MultipartFile documentPhotoFile, @ModelAttribute(value= "answerObj") AnswerDTO surveyAnswerInfo) {
		Date presentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        surveyAnswerInfo.setFilledId(dateFormat.format(presentDate));
        
        String houseOnwerPhoto = "JPEG_" + surveyAnswerInfo.getFilledId() + "1_house_owner_photo" + ".JPG";  
        surveyAnswerInfo.setAnswer47(houseOnwerPhoto);
        
        String documentPhoto = "JPEG_" + surveyAnswerInfo.getFilledId() + "1_document_photo" + ".JPG"; 
        surveyAnswerInfo.setAnswer49(documentPhoto);
	}
}
