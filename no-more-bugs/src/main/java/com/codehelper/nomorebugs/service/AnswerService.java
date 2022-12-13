package com.codehelper.nomorebugs.service;

import java.util.List;

import com.codehelper.nomorebugs.payload.AnswerDto;
import com.codehelper.nomorebugs.payload.RatingDto;

public interface AnswerService {

	AnswerDto createAnswer(long questionId, AnswerDto answerDto);
	
	List<AnswerDto> getAnswersByQuestionId(long questionId, String sortBy, String sortDir);
	
	AnswerDto getAnswerById(long questionId, long answerId);
	
	AnswerDto updateAnswer(long questionId, long answerId, AnswerDto answerDto);
	
	void deleteById(long questionId, long answerId);
	
	void updateRating(long questionId, long answerId, RatingDto ratingDto);
}
