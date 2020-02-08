/**
 * @author Umesh Bhujel <yoomesbhujel@gmail.com>
 * Since Feb 1, 2020
 */
package com.ishanitech.ipalikawebapp.serviceImpl;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ishanitech.ipalikawebapp.dto.FormDetail;
import com.ishanitech.ipalikawebapp.dto.Response;
import com.ishanitech.ipalikawebapp.dto.UserDTO;
import com.ishanitech.ipalikawebapp.service.FormService;
import com.ishanitech.ipalikawebapp.utilities.LoggedInUser;

@Service
public class FormServiceImpl implements FormService {
	@LoggedInUser UserDTO user;
	RestTemplate restTemplate;
	
	public FormServiceImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	@Override
	public List<FormDetail> getFullFormDetailById(int id) {
		Response<List<FormDetail>> formDetail = restTemplate.getForObject("http://localhost:8888/form-detail/1", Response.class);
		return formDetail.getData();
	}

}
