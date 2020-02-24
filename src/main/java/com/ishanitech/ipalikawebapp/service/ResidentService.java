package com.ishanitech.ipalikawebapp.service;

import java.util.List;

import com.ishanitech.ipalikawebapp.dto.FamilyMemberDTO;
import com.ishanitech.ipalikawebapp.dto.ResidentDTO;
import com.ishanitech.ipalikawebapp.dto.Response;

public interface ResidentService {
	Response<?> getResidentDataList(String token);
	List<ResidentDTO> searchResidentByKey(String searchKey, String token);
	Response<?> getResidentFullDetail(String filledId, String token);
	void addFamilyMember(FamilyMemberDTO familyMemberInfo, String token);
	Response<?> getMemberFormDetails(String token);
	Response<?> getMemberByMemberId(String token, String memberId);
}
