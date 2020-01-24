package com.ishanitech.ipalikawebapp.service;

import com.fasterxml.jackson.databind.JavaType;
import com.ishanitech.ipalikawebapp.dto.Response;


public interface RestClientService {
    Response<?> login(Object requestObject, JavaType responseType);
	
	 Response<?> getData(String url, JavaType responseType); 
	 /* Response<?>
	 * postData(String url, Object body, JavaType responseType); Response<?>
	 * putData(String url, Object body, JavaType responseType); Response<?>
	 * postMultipartFiles(String url, MultipartFile[] files, String[] fileNames,
	 * JavaType responseType); Response<?> deleteData(String url, Object body,
	 * JavaType responseType);
	 */
    //Resources<?> getPaginationDatas(String url, ParameterizedTypeReference<Resources<NewOrderDto>> responseType, String followAttr);
}
