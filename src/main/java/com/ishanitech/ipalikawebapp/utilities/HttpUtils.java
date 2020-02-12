package com.ishanitech.ipalikawebapp.utilities;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;

public class HttpUtils {
	public static HttpHeaders createHeader(@Nullable MediaType mediaType, @Nullable String token) {
		HttpHeaders headers = new HttpHeaders();
		if(mediaType != null) {
			headers.setContentType(mediaType);
		}
		
		if(token != null) {
			headers.set("Authorization", token);
		}
		
		return headers;
	}
	
	public static <T> HttpEntity<T> createRequestEntityWithHeadersAndToken(T entity, MediaType mediaType, @Nullable String token) {
		HttpEntity<T> requestEntity = new HttpEntity<T>(entity, createHeader(mediaType, token));
		return requestEntity;
	}
}
