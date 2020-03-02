package com.ishanitech.ipalikawebapp.service;

import com.ishanitech.ipalikawebapp.dto.AnswerDTO;

public interface SurveyAnswerService {

	void addHouseholdSurveyAnswer(AnswerDTO answerDto, String token);

}
