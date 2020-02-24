package com.ishanitech.ipalikawebapp.serviceImpl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.ishanitech.ipalikawebapp.configs.properties.RestApiProperties;
import com.ishanitech.ipalikawebapp.dto.FavouritePlaceDTO;
import com.ishanitech.ipalikawebapp.dto.Response;
import com.ishanitech.ipalikawebapp.service.FavouritePlacesService;
import com.ishanitech.ipalikawebapp.utilities.HttpUtils;


@Service
public class FavouritePlacesServiceImpl implements FavouritePlacesService {
	private final RestTemplate restTemplate;
	private final RestApiProperties restApiProperties;
	private final String FAVOURITE_PLACE_BASE_URL = "/favourite-place";
	
	public FavouritePlacesServiceImpl(RestTemplate restTemplate, RestApiProperties restApiProperties) {
		this.restTemplate = restTemplate;
		this.restApiProperties = restApiProperties;
	}

	@Override
	public Response<List<FavouritePlaceDTO>> getAllFavouritePlaces() {
		String template = FAVOURITE_PLACE_BASE_URL; 
		String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
		RequestEntity<List<FavouritePlaceDTO>> requestEntity = HttpUtils.createRequestEntity(HttpMethod.GET, MediaType.APPLICATION_JSON, url);
		ParameterizedTypeReference<Response<List<FavouritePlaceDTO>>> responseType = new ParameterizedTypeReference<Response<List<FavouritePlaceDTO>>>() {};
		Response<List<FavouritePlaceDTO>> favouritePlaces = restTemplate.exchange(requestEntity, responseType).getBody();
		return favouritePlaces;
	}

	@Override
	public Response<FavouritePlaceDTO> getFavouritePlaceByPlaceId(String placeId) {
		String template = FAVOURITE_PLACE_BASE_URL + "/detail/" + placeId;
		String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
		RequestEntity<FavouritePlaceDTO> requestEntity = HttpUtils.createRequestEntity(HttpMethod.GET, MediaType.APPLICATION_JSON, url);
		ParameterizedTypeReference<Response<FavouritePlaceDTO>> responseType = new ParameterizedTypeReference<Response<FavouritePlaceDTO>>() {};
		Response<FavouritePlaceDTO> favouritePlaceInfo = restTemplate.exchange(requestEntity, responseType).getBody();
		return favouritePlaceInfo;
	}

	@Override
	public void deleteFavouritePlacebyPlaceId(String favPlaceId, String token) {
		String template = FAVOURITE_PLACE_BASE_URL + "/" + favPlaceId;
		String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
		RequestEntity<String> requestEntity = HttpUtils.createRequestEntity(HttpMethod.DELETE, MediaType.APPLICATION_JSON, token , url);
		restTemplate.exchange(requestEntity, String.class);
//		restTemplate.delete("http://localhost:8888/favourite-place/" + favPlaceId);
	}

	@Override
	public void addFavouritePlaceInfo(FavouritePlaceDTO favouritePlaceInfo, String token) {
		String template = FAVOURITE_PLACE_BASE_URL + "/single";
		String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
		RequestEntity<FavouritePlaceDTO> requestEntity = HttpUtils.createRequestEntity(HttpMethod.POST, favouritePlaceInfo, MediaType.APPLICATION_JSON, token, url);
		restTemplate.exchange(requestEntity, String.class);
	}

	@Override
	public void addFavouritePlaceImage(MultipartFile file, String token) {
		String template = FAVOURITE_PLACE_BASE_URL + "/image";
		String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
	
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
	    headers.add("Authorization", token);
		
		
		final String captureId = "1001";
		
		LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		
		Date presentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");

        
        String imageFileName = "JPEG_" + dateFormat.format(presentDate) + "_" + captureId + ".JPG";
		
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
		
		
	    HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
	    restTemplate.postForObject(url, requestEntity, String.class);
		
		
		
//	      HttpHeaders headers = new HttpHeaders();
//	      headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//	      headers.add("Authorization", token);

//	      LinkedMultiValueMap<String, Object> map =  

//	                  new LinkedMultiValueMap<>();
//	      map.add("your_param_key", "your_param_value");
//	      map.add("files", your_file_content_in_byte_array);


		
		
		
//		HttpHeaders httpHeaders = new HttpHeaders();
//	    httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
		
//		HttpUtils.createRequestEntity(HttpMethod.POST, entity, mediaType, token, url)
	    
//		restTemplate.postForObject("http://localhost:8888/favourite-place/image", map, String.class);
//		RequestEntity<MultiValueMap<String, Object>> requestEntity = HttpUtils.createRequestEntity(HttpMethod.POST, map, MediaType.MULTIPART_FORM_DATA, token, url);
//		restTemplate.exchange(requestEntity, String.class);
//		restTemplate.postForObject(url, requestEntity, String.class);
	}

	@Override
	public List<String> getTypesofFavourtiePlaces() {
		String template = FAVOURITE_PLACE_BASE_URL + "/types";
		String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
		RequestEntity<List<String>> requestEntity = HttpUtils.createRequestEntity(HttpMethod.GET, MediaType.APPLICATION_JSON, url);
		ParameterizedTypeReference<Response<List<String>>> responseType = new ParameterizedTypeReference<Response<List<String>>>() {};
		Response<List<String>> favPlaceTypes = restTemplate.exchange(requestEntity, responseType).getBody();
		return favPlaceTypes.getData();
	}

}
