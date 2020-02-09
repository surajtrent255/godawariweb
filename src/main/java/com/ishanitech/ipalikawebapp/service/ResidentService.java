package com.ishanitech.ipalikawebapp.service;


import com.ishanitech.ipalikawebapp.dto.FamilyMemberDTO;
import com.ishanitech.ipalikawebapp.dto.Response;

public interface ResidentService {

	Response<?> getResidentDataList();
	Response<?> getResidentFullDetail(String filledId);
	void addFamilyMember(FamilyMemberDTO familyMemberInfo);
}
