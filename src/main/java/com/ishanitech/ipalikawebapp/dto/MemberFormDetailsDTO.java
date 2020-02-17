package com.ishanitech.ipalikawebapp.dto;

import java.util.List;
import lombok.Data;

@Data
public class MemberFormDetailsDTO {

	private List<String> relation;
	private List<String> gender;
	private List<String> education;
	private List<String> maritalStatus;
	
}