package com.ishanitech.ipalikawebapp.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ishanitech.ipalikawebapp.dto.FavouritePlaceDTO;
import com.ishanitech.ipalikawebapp.dto.Response;
import com.ishanitech.ipalikawebapp.service.FavouritePlacesService;


@Service
public class FavouritePlacesServiceImpl implements FavouritePlacesService {

	RestTemplate restTemplate;
	
	public FavouritePlacesServiceImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}



	@Override
	public Response<List<FavouritePlaceDTO>> getAllFavouritePlaces() {
		Response<List<FavouritePlaceDTO>> favouritePlaces = restTemplate.getForObject("http://localhost:8888/favourite-place", Response.class);
		return favouritePlaces;
	}

}
