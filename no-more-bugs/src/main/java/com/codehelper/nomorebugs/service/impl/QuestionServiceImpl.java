package com.codehelper.nomorebugs.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.codehelper.nomorebugs.entity.Question;
import com.codehelper.nomorebugs.entity.User;
import com.codehelper.nomorebugs.exception.ApiException;
import com.codehelper.nomorebugs.exception.ResourceNotFoundException;
import com.codehelper.nomorebugs.payload.QuestionDto;
import com.codehelper.nomorebugs.payload.QuestionsResponse;
import com.codehelper.nomorebugs.repository.QuestionRepository;
import com.codehelper.nomorebugs.repository.UserRepository;
import com.codehelper.nomorebugs.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {

	private QuestionRepository questionRepository;
	private UserRepository userRepository;

	// inject question repository
	@Autowired
	public QuestionServiceImpl(QuestionRepository questionRepository, UserRepository userRepository) {
		this.questionRepository = questionRepository;
		this.userRepository = userRepository;
	}

	// add question to the database
	@Override
	public QuestionDto createQuestion(QuestionDto questionDto) {
		// convert DTO to entiry
		User user = userRepository.findById(questionDto.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", questionDto.getUserId()));

		Question question = mapToEntity(questionDto, user);
		question.setRating(0);

		Question dbQuestion = questionRepository.save(question);

		// convert entity to dto
		QuestionDto responseQuestion = mapToDto(dbQuestion);

		return responseQuestion;
	}

	// convert entity to dto
	private QuestionDto mapToDto(Question dbQuestion) {
		QuestionDto responseQuestion = new QuestionDto();
		responseQuestion.setId(dbQuestion.getId());
		responseQuestion.setContent(dbQuestion.getContent());
		responseQuestion.setCode(dbQuestion.getCode());
		responseQuestion.setTitle(dbQuestion.getTitle());
		responseQuestion.setImageUrl(dbQuestion.getImageUrl());
		responseQuestion.setUserId(dbQuestion.getUser().getId());
		responseQuestion.setRating(dbQuestion.getRating());
		return responseQuestion;
	}

	// convert DTO to entiry
	private Question mapToEntity(QuestionDto questionDto, User user) {
		Question question = new Question();
		question.setContent(questionDto.getContent());
		question.setCode(questionDto.getCode());
		question.setTitle(questionDto.getTitle());
		question.setImageUrl(questionDto.getImageUrl());
		question.setUser(user);
		question.setRating(questionDto.getRating());
		return question;
	}

	// get all the questions
	@Override
	public QuestionsResponse getAllQuestions(int pageNo, int pageSize, String sortBy, String sortDir) {

		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

		Page<Question> pageContent = questionRepository.findAll(pageable);

		// get content
		List<Question> questions = pageContent.getContent();

		List<QuestionDto> content = questions.stream().map(question -> mapToDto(question)).collect(Collectors.toList());

		QuestionsResponse questionsResponse = new QuestionsResponse(content, pageContent.getNumber(),
				pageContent.getSize(), pageContent.getTotalElements(), pageContent.getTotalPages(),
				pageContent.isLast());

		return questionsResponse;
	}

	// search question
	@Override
	public QuestionsResponse searchQuestions(String query, int pageNo, int pageSize, String sortBy, String sortDir) {

		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

		Page<Question> pageContent = questionRepository.customSearchQuery(query, pageable);

		// get content
		List<Question> questions = pageContent.getContent();

		List<QuestionDto> content = questions.stream().map(question -> mapToDto(question)).collect(Collectors.toList());

		QuestionsResponse questionsResponse = new QuestionsResponse(content, pageContent.getNumber(),
				pageContent.getSize(), pageContent.getTotalElements(), pageContent.getTotalPages(),
				pageContent.isLast());

		return questionsResponse;
	}

	
	// get question by id
	@Override
	public QuestionDto getQuestionById(long id) {
		Question question = questionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Question", "id", id));
		QuestionDto responseDto = mapToDto(question);
		return responseDto;
	}

	// update question
	@Override
	public QuestionDto updateQuestion(QuestionDto questionDto, long id) {
		// get question by id
		Question question = questionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Question", "id", id));

		if (question.getUser().getId() != questionDto.getUserId())
			throw new ApiException(HttpStatus.FORBIDDEN, "User not authorized to update this question");

		question.setTitle(questionDto.getTitle());
		question.setCode(questionDto.getCode());
		question.setContent(questionDto.getContent());
		question.setImageUrl(questionDto.getImageUrl());

		Question updatedQuestion = questionRepository.save(question);

		return mapToDto(updatedQuestion);
	}

	@Override
	public void deletePost(long id) {
		Question question = questionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Question", "id", id));

		questionRepository.delete(question);
	}

}
