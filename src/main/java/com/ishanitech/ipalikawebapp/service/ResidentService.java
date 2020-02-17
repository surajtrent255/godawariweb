package com.ishanitech.ipalikawebapp.service;


import com.ishanitech.ipalikawebapp.dto.FamilyMemberDTO;
import com.ishanitech.ipalikawebapp.dto.Response;

public interface ResidentService {
	Response<?> getResidentDataList(String token);
	Response<?> getResidentFullDetail(String filledId, String token);
	void addFamilyMember(FamilyMemberDTO familyMemberInfo, String token);
	Response<?> getMemberFormDetails();
}
