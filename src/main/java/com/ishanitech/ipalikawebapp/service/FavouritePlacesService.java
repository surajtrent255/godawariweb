package com.ishanitech.ipalikawebapp.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ishanitech.ipalikawebapp.dto.FavouritePlaceDTO;
import com.ishanitech.ipalikawebapp.dto.Response;

public interface FavouritePlacesService {

	Response<List<FavouritePlaceDTO>> getAllFavouritePlaces();

	Response<FavouritePlaceDTO> getFavouritePlaceByPlaceId(String placeId);

	void deleteFavouritePlacebyPlaceId(String favPlaceId);

	void addFavouritePlaceInfo(FavouritePlaceDTO favouritePlaceInfo);

	void addFavouritePlaceImage(MultipartFile file);

	List<String> getTypesofFavourtiePlaces();

}
