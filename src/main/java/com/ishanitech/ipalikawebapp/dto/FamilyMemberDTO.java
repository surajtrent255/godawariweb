package com.ishanitech.ipalikawebapp.dto;

import lombok.Data;

@Data
public class FamilyMemberDTO {
	private String memberId;
    private String mainId;			//family Id (Form field id)
    private String name;
    private String occupation;
    private String relation;
    private String education;
    private String age;
    private String gender;
    private String maritalStatus;
    private String voterCard;
    private String address;
    private String healthCondition;
    private String dateOfBirth;
    private Boolean isDead;
    private Boolean submitStatus;
}
