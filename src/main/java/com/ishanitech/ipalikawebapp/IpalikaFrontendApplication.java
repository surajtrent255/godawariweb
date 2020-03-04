package com.ishanitech.ipalikawebapp;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class IpalikaFrontendApplication {

	public static void main(String[] args) {
		SpringApplication.run(IpalikaFrontendApplication.class, args);
	}
	
	/**
	 * Create bean for rest template. Using rest template we can call remote rest api.
	 * @return restTemplate RestTemplate object
	 */
    @Bean
    RestTemplate restTemplate() {
    	RestTemplate restTemplate = defaultRestTemplate();
    	restTemplate.setRequestFactory(clientHttpRequestFactory());
    	return restTemplate;
    }
    
    /**
	 * @return restTemplate a default rest template with default mapper.
	 */
	private static RestTemplate defaultRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		/*
		 * ObjectMapper objectMapper = new ObjectMapper();
		 * objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
		 * false); MappingJackson2HttpMessageConverter converter = new
		 * MappingJackson2HttpMessageConverter();
		 * converter.setObjectMapper(objectMapper);
		 * restTemplate.setMessageConverters(Arrays.asList(converter));
		 */
		return restTemplate;
	}

	/**
	 * 
	 * @return ClientHttpRequestFacoty custom http client request factory for unsupported http methods like patch, head etc.
	 */
	private ClientHttpRequestFactory clientHttpRequestFactory() {
    	HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
    	factory.setConnectTimeout(30000);
    	factory.setReadTimeout(5000);
    	return factory;
    }
	

}
