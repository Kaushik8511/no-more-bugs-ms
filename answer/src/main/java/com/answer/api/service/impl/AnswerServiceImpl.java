package com.answer.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.answer.api.DTO.RequestAnswerDTO;
import com.answer.api.DTO.ResponseAnswerDTO;
import com.answer.api.entity.Answer;
import com.answer.api.repository.AnswerRepository;
import com.answer.api.service.AnswerService;
import com.question.api.exception.ApiException;
import com.question.api.exception.ResourceNotFoundException;

@Service
public class AnswerServiceImpl implements AnswerService {

	
	@Autowired
	private AnswerRepository answerRepository;
	
	@Override
	public ResponseAnswerDTO createAnswer(String questionId, RequestAnswerDTO requestAnswerDTO) {
		//check if the question id exist else throw error
		Answer answer = convertToEntity(requestAnswerDTO);
		answer.setQuestionId(questionId);
		return convertToDTO(answerRepository.save(answer));
	}

	private ResponseAnswerDTO convertToDTO(Answer answer) {
		return ResponseAnswerDTO.builder()
				.id(answer.getId())
				.content(answer.getContent())
				.codes(answer.getCodes())
				.imageUrl(answer.getImageUrl())
				.userId(answer.getUserId())
				.questionId(answer.getQuestionId())
				.ratings(answer.getRatingUserIds() == null ? 0 : answer.getRatingUserIds().size())
				.createdAt(new Date())
				.modifiedAt(new Date())
				.build();
	}

	private Answer convertToEntity(RequestAnswerDTO requestAnswerDTO) {
		return Answer.builder()
				.content(requestAnswerDTO.getContent())
				.codes(requestAnswerDTO.getCodes())
				.imageUrl(requestAnswerDTO.getImageUrl())
				.userId(requestAnswerDTO.getUserId())
				.build();
	}

	@Override
	public List<ResponseAnswerDTO> getAnswersByQuestionId(String questionId) {
		List<Answer> answers = answerRepository.findByQuestionId(questionId);
		if(answers == null) answers = new ArrayList<>();
		List<ResponseAnswerDTO> responseAnswerDTOs = new ArrayList<>();
		for(Answer answer: answers) responseAnswerDTOs.add(convertToDTO(answer));
		return responseAnswerDTOs;
	}

	@Override
	public ResponseAnswerDTO getAnswerById(String questionId, String answerId) {
		Answer answer = answerRepository.findById(answerId).orElseThrow(() -> new ResourceNotFoundException("Answer", "id", answerId));
		if(!answer.getQuestionId().equals(questionId)) throw new ApiException(HttpStatus.NOT_FOUND, "Answer not found for the given question");
		return convertToDTO(answer);
	}

	@Override
	public ResponseAnswerDTO updateAnswer(String questionId, String answerId, RequestAnswerDTO requestAnswerDTO) {
		Answer answer = answerRepository.findById(answerId).orElseThrow(() -> new ResourceNotFoundException("Answer", "id", answerId));
		if(!answer.getQuestionId().equals(questionId)) throw new ApiException(HttpStatus.NOT_FOUND, "Answer not found for the given question");
		answer.setContent(requestAnswerDTO.getContent());
		answer.setCodes(requestAnswerDTO.getCodes());
		answer.setImageUrl(requestAnswerDTO.getImageUrl());
		answer.setUserId(requestAnswerDTO.getUserId());
		return convertToDTO(answerRepository.save(answer));
	}

	@Override
	public void deleteById(String questionId, String answerId) {
		Answer answer = answerRepository.findById(answerId).orElseThrow(() -> new ResourceNotFoundException("Answer", "id", answerId));
		if(!answer.getQuestionId().equals(questionId)) throw new ApiException(HttpStatus.NOT_FOUND, "Answer not found for the given question");
		answerRepository.deleteById(answerId);
	}

}
