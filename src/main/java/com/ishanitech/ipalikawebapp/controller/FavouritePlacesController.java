package com.ishanitech.ipalikawebapp.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ishanitech.ipalikawebapp.dto.FavouritePlaceDTO;
import com.ishanitech.ipalikawebapp.dto.UserDTO;
import com.ishanitech.ipalikawebapp.service.FavouritePlacesService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/favourite")
@Controller
public class FavouritePlacesController {

	private final FavouritePlacesService favouritePlacesService;
	
	
	
	
	public FavouritePlacesController(FavouritePlacesService favouritePlacesService) {
		this.favouritePlacesService = favouritePlacesService;
	}


	@DeleteMapping("/{favPlaceId}")
	public void deleteFavouritePlaceByPlaceId(@PathVariable("favPlaceId") String favPlaceId, @AuthenticationPrincipal UserDTO user) {
		log.info(user.getToken());
		favouritePlacesService.deleteFavouritePlacebyPlaceId(favPlaceId, user.getToken());
	}
	
	@PostMapping()
	public void addFavouritePlace(@RequestParam(value = "favPhoto") MultipartFile file,
			@ModelAttribute(value = "favPlaceObj") FavouritePlaceDTO favouritePlaceInfo, @AuthenticationPrincipal UserDTO user) {
		final String captureId = "1001";
		
		Date presentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        favouritePlaceInfo.setFilledId(dateFormat.format(presentDate));

        
        String imageFileName = "JPEG_" + favouritePlaceInfo.getFilledId() + "_" + captureId + ".JPG";
        favouritePlaceInfo.setPlaceImage(imageFileName);
     
		log.info("#################################################");
		log.info(file.getOriginalFilename().toString());
		
		
		file.getOriginalFilename().concat(imageFileName);
		
		log.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		log.info(file.getOriginalFilename());
		
		
		log.info(favouritePlaceInfo.getPlaceName());
		log.info(favouritePlaceInfo.getPlaceDescription());
		log.info(favouritePlaceInfo.getPlaceWard());
		log.info(favouritePlaceInfo.getPlaceImage());
		log.info(favouritePlaceInfo.getPlaceGPS());
		
		favouritePlacesService.addFavouritePlaceInfo(favouritePlaceInfo, user.getToken());
		
		favouritePlacesService.addFavouritePlaceImage(file, user.getToken());
		
	}
	
}
