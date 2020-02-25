package com.ishanitech.ipalikawebapp.serviceImpl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
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

import lombok.extern.slf4j.Slf4j;


@Slf4j
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
		String template = String.format("%s/%s", FAVOURITE_PLACE_BASE_URL, placeId);
		String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
		RequestEntity<FavouritePlaceDTO> requestEntity = HttpUtils.createRequestEntity(HttpMethod.GET, MediaType.APPLICATION_JSON, url);
		ParameterizedTypeReference<Response<FavouritePlaceDTO>> responseType = new ParameterizedTypeReference<Response<FavouritePlaceDTO>>() {};
		Response<FavouritePlaceDTO> favouritePlaceInfo = restTemplate.exchange(requestEntity, responseType).getBody();
		return favouritePlaceInfo;
	}

	@Override
	public void deleteFavouritePlacebyPlaceId(String favPlaceId, String token) {
		String template = String.format("%s/%s", FAVOURITE_PLACE_BASE_URL, favPlaceId);
		String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
		RequestEntity<String> requestEntity = HttpUtils.createRequestEntity(HttpMethod.DELETE, MediaType.APPLICATION_JSON, token , url);
		restTemplate.exchange(requestEntity, String.class);
	}

	@Override
	public void addFavouritePlaceInfo(FavouritePlaceDTO favouritePlaceInfo, String token) {
		String template = String.format("%s/single", FAVOURITE_PLACE_BASE_URL);
		String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
		RequestEntity<FavouritePlaceDTO> requestEntity = HttpUtils.createRequestEntity(HttpMethod.POST, favouritePlaceInfo, MediaType.APPLICATION_JSON, token, url);
		restTemplate.exchange(requestEntity, String.class);
	}

	@Override
	public void addFavouritePlaceImage(MultipartFile file, String token) {
		String template = String.format("%s/image", FAVOURITE_PLACE_BASE_URL);
		String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("picture", file.getResource());
		HttpHeaders headers = HttpUtils.createHeader(MediaType.MULTIPART_FORM_DATA, token);
	    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
		restTemplate.postForLocation(url, requestEntity);
	}

	@Override
	public List<String> getTypesofFavourtiePlaces() {
		String template = String.format("%s/type", FAVOURITE_PLACE_BASE_URL);
		String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
		RequestEntity<List<String>> requestEntity = HttpUtils.createRequestEntity(HttpMethod.GET, MediaType.APPLICATION_JSON, url);
		ParameterizedTypeReference<Response<List<String>>> responseType = new ParameterizedTypeReference<Response<List<String>>>() {};
		Response<List<String>> favPlaceTypes = restTemplate.exchange(requestEntity, responseType).getBody();
		return favPlaceTypes.getData();
	}

}
