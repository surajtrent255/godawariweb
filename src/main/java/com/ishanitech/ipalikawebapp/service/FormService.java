package com.ishanitech.ipalikawebapp.service;

import java.util.List;

import com.ishanitech.ipalikawebapp.dto.FormDetail;

public interface FormService {
	public List<FormDetail> getFullFormDetailById(int id);
}
