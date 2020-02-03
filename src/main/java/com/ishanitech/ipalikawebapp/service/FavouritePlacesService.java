package com.ishanitech.ipalikawebapp.service;

import java.util.List;

import com.ishanitech.ipalikawebapp.dto.FavouritePlaceDTO;
import com.ishanitech.ipalikawebapp.dto.Response;

public interface FavouritePlacesService {

	Response<List<FavouritePlaceDTO>> getAllFavouritePlaces();

}
