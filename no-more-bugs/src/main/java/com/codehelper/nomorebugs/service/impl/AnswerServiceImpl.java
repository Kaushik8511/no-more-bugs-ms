package com.codehelper.nomorebugs.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.codehelper.nomorebugs.entity.Answer;
import com.codehelper.nomorebugs.entity.Question;
import com.codehelper.nomorebugs.entity.Rating;
import com.codehelper.nomorebugs.entity.User;
import com.codehelper.nomorebugs.exception.ApiException;
import com.codehelper.nomorebugs.exception.ResourceNotFoundException;
import com.codehelper.nomorebugs.payload.AnswerDto;
import com.codehelper.nomorebugs.payload.RatingDto;
import com.codehelper.nomorebugs.repository.AnswerRepository;
import com.codehelper.nomorebugs.repository.QuestionRepository;
import com.codehelper.nomorebugs.repository.RatingRepository;
import com.codehelper.nomorebugs.repository.UserRepository;
import com.codehelper.nomorebugs.service.AnswerService;

@Service
public class AnswerServiceImpl implements AnswerService {

	private AnswerRepository answerRepository;
	private QuestionRepository questionRepository;
	private UserRepository userRepository;
	private RatingRepository ratingRepository;

	@Autowired
	public AnswerServiceImpl(AnswerRepository answerRepository, QuestionRepository questionRepository,
			UserRepository userRepository, RatingRepository ratingRepository) {
		this.answerRepository = answerRepository;
		this.questionRepository = questionRepository;
		this.userRepository = userRepository;
		this.ratingRepository = ratingRepository;
	}

	// Save answer to database
	@Override
	public AnswerDto createAnswer(long questionId, AnswerDto answerDto) {

		// get the question
		Question question = questionRepository.findById(questionId)
				.orElseThrow(() -> new ResourceNotFoundException("Question", "id", questionId));

		// get the user
		User user = userRepository.findById(answerDto.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", answerDto.getUserId()));

		// convert to entity
		Answer answer = mapToEntity(answerDto);
		answer.setQuestion(question);
		answer.setUser(user);

		// save to database
		answer.setRating(0);
		Answer dbAnswer = answerRepository.save(answer);

		// convert to dto
		AnswerDto responseDto = mapToDto(dbAnswer, questionId, answerDto.getUserId());

		// return
		return responseDto;
	}

	private Answer mapToEntity(AnswerDto answerDto) {
		Answer answer = new Answer();
		answer.setCode(answerDto.getCode());
		answer.setContent(answerDto.getContent());
		answer.setImageUrl(answerDto.getImageUrl());
		answer.setRating(answerDto.getRating());
		return answer;
	}

	private AnswerDto mapToDto(Answer answer, long questionId, long userId) {
		AnswerDto answerDto = new AnswerDto();
		answerDto.setId(answer.getId());
		answerDto.setCode(answer.getCode());
		answerDto.setContent(answer.getContent());
		answerDto.setImageUrl(answer.getImageUrl());
		answerDto.setRating(answer.getRating());
		answerDto.setQuestionId(questionId);
		answerDto.setUserId(userId);
		return answerDto;
	}

	// get all the answers belong to question
	@Override
	public List<AnswerDto> getAnswersByQuestionId(long questionId, String sortBy, String sortDir) {

		// retrieve the list of answers by questionId
		List<Answer> answers = answerRepository.findByQuestionId(questionId);

		if (sortDir.equalsIgnoreCase("asc")) {
			if (sortBy.equalsIgnoreCase("rating")) {
				Collections.sort(answers, (answer1, answer2) -> {
					return (int) (answer1.getRating() - answer2.getRating());
				});
			} else {
				Collections.sort(answers, (answer1, answer2) -> {
					return (int) (answer1.getId() - answer2.getId());
				});
			}
		} else {
			if (sortBy.equalsIgnoreCase("rating")) {
				Collections.sort(answers, (answer1, answer2) -> {
					return (int) (answer2.getRating() - answer1.getRating());
				});
			} else {
				Collections.sort(answers, (answer1, answer2) -> {
					return (int) (answer2.getId() - answer1.getId());
				});
			}
		}

		// convert it to list of answerDto and return
		return answers.stream().map(answer -> mapToDto(answer, questionId, answer.getUser().getId()))
				.collect(Collectors.toList());
	}

	// get answer by id
	@Override
	public AnswerDto getAnswerById(long questionId, long answerId) {
		Question question = questionRepository.findById(questionId)
				.orElseThrow(() -> new ResourceNotFoundException("Question", "id", questionId));

		Answer answer = answerRepository.findById(answerId)
				.orElseThrow(() -> new ResourceNotFoundException("Answer", "id", answerId));

		if (answer.getQuestion().getId() != question.getId())
			throw new ApiException(HttpStatus.NOT_FOUND, "Answer does not found for the question with given id");

		return mapToDto(answer, questionId, answer.getUser().getId());
	}

	// update answer by id
	@Override
	public AnswerDto updateAnswer(long questionId, long answerId, AnswerDto answerDto) {

		// get the answer
		Answer answer = answerRepository.findById(answerId)
				.orElseThrow(() -> new ResourceNotFoundException("Answer", "id", answerId));

		// check if answer belong to the given question id
		Question question = questionRepository.findById(questionId)
				.orElseThrow(() -> new ResourceNotFoundException("Question", "id", questionId));

		if (answer.getQuestion().getId() != question.getId())
			throw new ApiException(HttpStatus.NOT_FOUND, "Answer does not found for the question with given id");

		// check if user is authorized to update the answer
		if (answerDto.getUserId() != answer.getUser().getId())
			throw new ApiException(HttpStatus.FORBIDDEN, "User not authorized to update the answer");

		answer.setCode(answerDto.getCode());
		answer.setContent(answerDto.getContent());
		answer.setImageUrl(answerDto.getImageUrl());

		Answer dbAnswer = answerRepository.save(answer);

		return mapToDto(dbAnswer, questionId, answerDto.getUserId());
	}

	@Override
	public void deleteById(long questionId, long answerId) {
		// get the answer
		Answer answer = answerRepository.findById(answerId)
				.orElseThrow(() -> new ResourceNotFoundException("Answer", "id", answerId));

		// get the question
		Question question = questionRepository.findById(questionId)
				.orElseThrow(() -> new ResourceNotFoundException("Question", "id", questionId));

		// check if answer belong to the given question id
		if (answer.getQuestion().getId() != question.getId())
			throw new ApiException(HttpStatus.NOT_FOUND, "Answer does not found for the question with given id");

		answerRepository.delete(answer);
	}

	// updating the rating
	@Override
	public void updateRating(long questionId, long answerId, RatingDto ratingDto) {
		// get the answer
		Answer answer = answerRepository.findById(answerId)
				.orElseThrow(() -> new ResourceNotFoundException("Answer", "id", answerId));

		// get the question
		Question question = questionRepository.findById(questionId)
				.orElseThrow(() -> new ResourceNotFoundException("Question", "id", questionId));

		// check if answer belong to the given question id
		if (answer.getQuestion().getId() != question.getId())
			throw new ApiException(HttpStatus.NOT_FOUND, "Answer does not found for the question with given id");

		// extracting payload
		long userId = ratingDto.getUserId();
		int action = ratingDto.getValue() > 0 ? 1 : -1;

		// checking if user giving rating to himself
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		if (answer.getUser().getId() == userId)
			throw new ApiException(HttpStatus.BAD_REQUEST, "You can not rate your self");

		Rating rating = ratingRepository.customFindQuery(userId, answerId);
		if (rating == null) {
			answer.setRating(answer.getRating() + action);
			answerRepository.save(answer);
			rating = new Rating();
			rating.setAnswer(answer);
			rating.setUser(user);
			rating.setValue(action);
			ratingRepository.save(rating);
			return;
		}

		if (rating.getValue() == action)
			throw new ApiException(HttpStatus.BAD_REQUEST, "You have allredy performed this operation");

		answer.setRating(answer.getRating() - rating.getValue() + action);
		
		answerRepository.save(answer);
		rating.setValue(action);
		ratingRepository.save(rating);
	}

}
