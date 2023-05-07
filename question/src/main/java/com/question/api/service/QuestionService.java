package com.question.api.service;

import java.util.List;

import com.question.api.DTO.RequestQuestionDTO;
import com.question.api.DTO.ResponseQuestionDTO;

public interface QuestionService {
	
	ResponseQuestionDTO createQuestion(RequestQuestionDTO questionDto);
	
	List<ResponseQuestionDTO> getAllQuestions();
	
//	QuestionsResponse searchQuestions(String query, int pageNo, int pageSize, String soryBy, String sortDir);
	
	ResponseQuestionDTO getQuestionById(String id);
	
	ResponseQuestionDTO updateQuestion(RequestQuestionDTO questionDto, String id);
	
	void deleteQuestion(String id);
}
