package com.ishanitech.ipalikawebapp.serviceImpl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

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



	@Override
	public Response<FavouritePlaceDTO> getFavouritePlaceByPlaceId(String placeId) {
		Response<FavouritePlaceDTO> favouritePlaceInfo = restTemplate.getForObject("http://localhost:8888/favourite-place/detail/" + placeId, Response.class);
		return favouritePlaceInfo;
	}



	@Override
	public void deleteFavouritePlacebyPlaceId(String favPlaceId) {
		restTemplate.delete("http://localhost:8888/favourite-place/" + favPlaceId);
	}



	@Override
	public void addFavouritePlaceInfo(FavouritePlaceDTO favouritePlaceInfo) {
		restTemplate.postForObject("http://localhost:8888/favourite-place/single", favouritePlaceInfo, String.class);
	}



	@Override
	public void addFavouritePlaceImage(MultipartFile file) {
		
		final String captureId = "1001";
		
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		
		Date presentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");

        
        String imageFileName = "JPEG_" + dateFormat.format(presentDate) + "_" + captureId + ".JPG";;
		
		map.add("name", imageFileName);
		map.add("filename", imageFileName);
		ByteArrayResource contentsAsResource = null;
		try {
			contentsAsResource = new ByteArrayResource(file.getBytes()){
			            @Override
			            public String getFilename(){
			                return imageFileName;
			            }
			        };
		} catch (IOException e) {
			e.printStackTrace();
		}
		map.add("picture", contentsAsResource);
		restTemplate.postForObject("http://localhost:8888/favourite-place/image", map, String.class);
		
//		restTemplate.postForObject("http://localhost:8888/favourite-place/image", file, String.class);
		
	}

}
