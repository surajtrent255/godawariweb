package com.ishanitech.ipalikawebapp.service;

import java.util.List;

import com.ishanitech.ipalikawebapp.dto.FormDetail;
import com.ishanitech.ipalikawebapp.dto.Response;

public interface FormService {
	public List<FormDetail> getFullFormDetailById(int id);

	public Response<List<String>> getListofDistricts();
}
