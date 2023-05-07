package com.question.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.question.api.DTO.RequestQuestionDTO;
import com.question.api.DTO.ResponseQuestionDTO;
import com.question.api.entity.Question;
import com.question.api.exception.ResourceNotFoundException;
import com.question.api.repository.QuestionRepository;
import com.question.api.service.QuestionService;


@Service
public class QuestionServiceImpl implements QuestionService {
	
	@Autowired
	private QuestionRepository questionRepository;

	@Override
	public ResponseQuestionDTO createQuestion(RequestQuestionDTO questionDto) {
		
		Question question = convertToEntity(questionDto);
		question.setCreatedAt(new Date());
		question.setModifiedAt(new Date());
		
		return convertToDTO(questionRepository.save(question));
	}

	private Question convertToEntity(RequestQuestionDTO questionDto) {
		return Question.builder()
				.title(questionDto.getTitle())
				.content(questionDto.getContent())
				.codes(questionDto.getCodes())
				.userId(questionDto.getUserId())
				.imageUrls(questionDto.getImageUrls())
				.build();
	}

	@Override
	public List<ResponseQuestionDTO> getAllQuestions() {
		
		List<ResponseQuestionDTO> responseQuestionDTOs = new ArrayList<>();
		List<Question> questions = questionRepository.findAll();
		for(Question question: questions) {
			responseQuestionDTOs.add(convertToDTO(question));
		}
		
		return responseQuestionDTOs;
	}

	private ResponseQuestionDTO convertToDTO(Question question) {
		return ResponseQuestionDTO.builder()
				.title(question.getTitle())
				.content(question.getContent())
				.codes(question.getCodes())
				.imageUrls(question.getImageUrls())
				.ratings(question.getRatingUserIds() == null ? 0 : question.getRatingUserIds().size())
				.id(question.getId())
				.userId(question.getUserId())
				.createdAt(question.getCreatedAt())
				.modifiedAt(question.getModifiedAt())
				.build();
	}

	@Override
	public ResponseQuestionDTO getQuestionById(String id) {
		Question question = questionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Question", "id", id));
		return convertToDTO(question);
	}

	@Override
	public ResponseQuestionDTO updateQuestion(RequestQuestionDTO questionDto, String id) {
		Question question = questionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Question", "id", id));
		question.setTitle(questionDto.getTitle());
		question.setContent(questionDto.getContent());
		question.setCodes(questionDto.getCodes());
		question.setUserId(questionDto.getUserId());
		question.setImageUrls(questionDto.getImageUrls());
		question.setModifiedAt(new Date());
		questionRepository.save(question);
		return convertToDTO(question);
	}

	@Override
	public void deleteQuestion(String id) {
		
		Question question = questionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Question", "id", id));
		if(question == null) throw new RuntimeException("question not found");
		questionRepository.deleteById(id);
		
	}

}
