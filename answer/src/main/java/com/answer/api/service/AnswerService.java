package com.answer.api.service;

import java.util.List;


import com.answer.api.DTO.RequestAnswerDTO;
import com.answer.api.DTO.ResponseAnswerDTO;

public interface AnswerService {

	ResponseAnswerDTO createAnswer(String questionId, RequestAnswerDTO requestAnswerDTO);
	
	List<ResponseAnswerDTO> getAnswersByQuestionId(String questionId);
	
	ResponseAnswerDTO getAnswerById(String questionId, String answerId);
	
	ResponseAnswerDTO updateAnswer(String questionId, String answerId, RequestAnswerDTO answerDto);
	
	void deleteById(String questionId, String answerId);
	
//	void updateRating(long questionId, long answerId, RatingDto ratingDto);
	
}
