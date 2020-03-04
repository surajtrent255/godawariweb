package com.ishanitech.ipalikawebapp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

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
    int addHouseHold(@RequestBody AnswerDTO answerDto, @AuthenticationPrincipal UserDTO user, HttpServletRequest httpServletRequest) {
        System.out.println(answerDto.toString());
        /*Date presentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String formFilledId = dateFormat.format(presentDate);
        httpServletRequest.getSession().setAttribute("filledId" , formFilledId);*/
       
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
	@RequestMapping(value = "/image", method = RequestMethod.POST)
	public @ResponseBody String addImage(MultipartHttpServletRequest request, @AuthenticationPrincipal UserDTO user) {
		
		String inputTagName = request.getParameter("imgIndex");
		System.out.println("InputTagName----> " + inputTagName);
		String extension = request.getParameter("extension");
		String filledId = request.getParameter("filledId");
		String questionId = request.getParameter("questionId");
		Path rootLocation = Paths.get("C:/upload-dir");
		try {
			MultipartFile ourImage = request.getFile(inputTagName);
			//ourImage.getOriginalFilename().replace(ourImage.getOriginalFilename(), "JPEG_" + filledId + "_" + questionId + "." + extension);
			
			String imageName = "JPEG_" + filledId + "_" + questionId + "." + extension;
			//ourImage.getOriginalFilename().concat(imageName);
			System.out.println("GeneratedImageName----->" + imageName);
			System.out.println(ourImage.getSize());
			System.out.println(ourImage.getOriginalFilename());
			System.out.println("Name----->" + ourImage.getName());
			
			//for copying file to upload directory
			Files.copy(ourImage.getInputStream(), rootLocation.resolve(imageName));
			
			//for retrieving saved multipart file
			File file = new File("C:/upload-dir/" + imageName);
			FileItem fileItem = new DiskFileItem("mainFile", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());

			try {
			    InputStream input = new FileInputStream(file);
			    OutputStream os = fileItem.getOutputStream();
			    IOUtils.copy(input, os);
			    // Or faster..
			    // IOUtils.copy(new FileInputStream(file), fileItem.getOutputStream());
			} catch (IOException ex) {
			    // do something.
			}

			MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
			//ends
			
			surveyAnswerService.addPhoto(multipartFile, imageName, user.getToken());
			
		} catch(Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
		
		
		
		return "1";
	}
}
