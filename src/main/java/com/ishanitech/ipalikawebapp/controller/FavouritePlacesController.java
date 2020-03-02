package com.ishanitech.ipalikawebapp.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.ishanitech.ipalikawebapp.dto.FavouritePlaceDTO;
import com.ishanitech.ipalikawebapp.dto.Response;
import com.ishanitech.ipalikawebapp.dto.UserDTO;
import com.ishanitech.ipalikawebapp.service.FavouritePlacesService;
import com.ishanitech.ipalikawebapp.service.WardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/favourite-place")
@Controller
public class FavouritePlacesController {

	private final FavouritePlacesService favouritePlacesService;
	private final WardService wardService;
	
	public FavouritePlacesController(FavouritePlacesService favouritePlacesService, WardService wardService) {
		this.favouritePlacesService = favouritePlacesService;
		this.wardService = wardService;
	}
	
	@GetMapping
	public String getFavouritePlaces(Model model) {
		model.addAttribute("favouritePlaceList", favouritePlacesService.getAllFavouritePlaces().getData());
		return "public/favourite-place";
	}
	
	@Secured({"ROLE_CENTRAL_ADMIN", "ROLE_WARD_ADMIN", "ROLE_SURVEYOR"})
	@PostMapping
	public String addFavouritePlace(@RequestParam(value = "favPhoto") MultipartFile file,
			@ModelAttribute(value = "favPlaceObj") FavouritePlaceDTO favouritePlaceInfo,
			@AuthenticationPrincipal UserDTO user) {
		Date presentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        favouritePlaceInfo.setFilledId(dateFormat.format(presentDate));
        String imageFileName = "JPEG_" + favouritePlaceInfo.getFilledId() +".JPG";
        favouritePlaceInfo.setPlaceImage(imageFileName);
		file.getOriginalFilename().concat(imageFileName);
		try {
			favouritePlacesService.addFavouritePlaceInfo(favouritePlaceInfo, user.getToken());
			favouritePlacesService.addFavouritePlaceImage(file, user.getToken());
			return "redirect:favourite-place/";
		} catch (Exception ex) {
			log.info(ex.getMessage());
			return "";
		}
	}

	@Secured({"ROLE_CENTRAL_ADMIN", "ROLE_WARD_ADMIN", "ROLE_SURVEYOR"})
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/{favPlaceId}")
	public void deleteFavouritePlaceByPlaceId(@PathVariable("favPlaceId") String favPlaceId, @AuthenticationPrincipal UserDTO user) {
		favouritePlacesService.deleteFavouritePlacebyPlaceId(favPlaceId, user.getToken());
	}
	
	@GetMapping("/{favPlaceId}")
	public String getFavouritePlaceByPlaceId(@PathVariable("favPlaceId") String favPlaceId,
			@AuthenticationPrincipal UserDTO user, Model model) {
		Response<FavouritePlaceDTO> favouritePlaceResponse = (Response<FavouritePlaceDTO>) favouritePlacesService.getFavouritePlaceByPlaceId(favPlaceId);
		model.addAttribute("favouritePlaceInfo", favouritePlaceResponse.getData());
		return "public/favourite-place-details";
	}
	
	@Secured({"ROLE_CENTRAL_ADMIN", "ROLE_WARD_ADMIN", "ROLE_SURVEYOR"})
	@GetMapping("/add")
	public String getFavouritePlaceEntryView(Model model, @AuthenticationPrincipal UserDTO user) {
		model.addAttribute("wardList", wardService.getAllWards(user.getToken()));
		model.addAttribute("placeTypes", favouritePlacesService.getTypesofFavourtiePlaces());
		model.addAttribute("favPlaceObj", new FavouritePlaceDTO());
		return "private/common/add-favourite-place";
	}
	
}
