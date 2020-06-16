package com.ishanitech.ipalikawebapp.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ishanitech.ipalikawebapp.dto.FavouritePlaceDTO;
import com.ishanitech.ipalikawebapp.dto.Response;

public interface FavouritePlacesService {

	Response<List<FavouritePlaceDTO>> getAllFavouritePlaces();

	Response<FavouritePlaceDTO> getFavouritePlaceByPlaceId(String placeId);

	void deleteFavouritePlacebyPlaceId(String favPlaceId, String token);

	void addFavouritePlaceInfo(FavouritePlaceDTO favouritePlaceInfo, String token);

	void addFavouritePlaceImage(MultipartFile file, String imageName, String token);

	List<String> getTypesofFavourtiePlaces();

	void editFavouritePlaceInfo(FavouritePlaceDTO favPlaceDTO, String favPlaceId, String token);

}
