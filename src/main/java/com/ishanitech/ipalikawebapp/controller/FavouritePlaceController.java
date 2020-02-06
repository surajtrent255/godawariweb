package com.ishanitech.ipalikawebapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ishanitech.ipalikawebapp.service.FavouritePlacesService;

@RequestMapping("/favourite")
@Controller
public class FavouritePlaceController {

	private final FavouritePlacesService favouritePlacesService;
	
	
	
	
	public FavouritePlaceController(FavouritePlacesService favouritePlacesService) {
		this.favouritePlacesService = favouritePlacesService;
	}




	@DeleteMapping("/{favPlaceId}")
	public void deleteFavouritePlaceByPlaceId(@PathVariable("favPlaceId") String favPlaceId) {
		favouritePlacesService.deleteFavouritePlacebyPlaceId(favPlaceId);
	}
	
	
}
