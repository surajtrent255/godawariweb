/**
 * @author Umesh Bhujel <yoomesbhujel@gmail.com>
 * Since Feb 18, 2020
 */
package com.ishanitech.ipalikawebapp.serviceImpl;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ishanitech.ipalikawebapp.configs.properties.RestApiProperties;
import com.ishanitech.ipalikawebapp.dto.Response;
import com.ishanitech.ipalikawebapp.service.WardService;
import com.ishanitech.ipalikawebapp.utilities.HttpUtils;

@Service
public class WardServiceImpl implements WardService {
	private final RestApiProperties restApiProperties;
	private final RestTemplate restTemplate;
	private final String BASE_WARD_ADDRESS = "/ward";
	public WardServiceImpl(RestTemplate restTemplate, RestApiProperties restApiProperties) {
		this.restTemplate = restTemplate;
		this.restApiProperties = restApiProperties;
	}

	@Override
	public List<Integer> getAllWards(String token) {
		String url = HttpUtils.createRequestUrl(restApiProperties, BASE_WARD_ADDRESS, null);
		RequestEntity requestEntity = HttpUtils.createRequestEntity(HttpMethod.GET, MediaType.APPLICATION_JSON, token, url);
		ParameterizedTypeReference<Response<List<Integer>>> responseType = new ParameterizedTypeReference<Response<List<Integer>>>() {
		};
		return restTemplate.exchange(requestEntity, responseType).getBody().getData();
	}

}
