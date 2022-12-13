package com.codehelper.nomorebugs.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codehelper.nomorebugs.payload.QuestionDto;
import com.codehelper.nomorebugs.payload.QuestionsResponse;
import com.codehelper.nomorebugs.service.QuestionService;
import com.codehelper.nomorebugs.utils.AppConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("REST API for the questions and exposes the endpoints related to questions")
@RestController
@RequestMapping("/v1/questions")
public class QuestionController {

	// loose coupling as we are using interface
	private QuestionService questionService;

	@Autowired
	public QuestionController(QuestionService questionService) {
		this.questionService = questionService;
	}

	// Create question and save it to the database
	@ApiOperation(value = "REST API for creating the question")
	@PostMapping
	public ResponseEntity<QuestionDto> createQuestion(@Valid @RequestBody QuestionDto questionDto) {

		return new ResponseEntity<QuestionDto>(questionService.createQuestion(questionDto), HttpStatus.CREATED);
	}

	// get all questions
	@ApiOperation(value = "REST API to fetch all the questions. You can also provide query parameters for pagination and sorting")
	@GetMapping
	public ResponseEntity<QuestionsResponse> findAll(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir) {
		return new ResponseEntity<QuestionsResponse>(questionService.getAllQuestions(pageNo, pageSize, sortBy, sortDir),
				HttpStatus.OK);
	}

	// search questions
	@ApiOperation(value = "REST API to search questions. You can also provide query parameters for pagination and sorting")
	@GetMapping("/search")
	public ResponseEntity<QuestionsResponse> findAll(
			@RequestParam(value = "searchBy") String searchBy,
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir) {
		return new ResponseEntity<QuestionsResponse>(questionService.searchQuestions("%" + searchBy + "%", pageNo, pageSize, sortBy, sortDir),
				HttpStatus.OK);
	}

	// get question by id
	@ApiOperation(value = "REST API to fetch question by ID")
	@GetMapping("/{id}")
	public ResponseEntity<QuestionDto> getQuestionById(@PathVariable long id) {
		return new ResponseEntity<QuestionDto>(questionService.getQuestionById(id), HttpStatus.OK);
	}

	// update question by id
	@ApiOperation(value = "REST API to update the fields of the question by ID")
	@PutMapping("/{id}")
	public ResponseEntity<QuestionDto> updateQuestion(@Valid @RequestBody QuestionDto questionDto,
			@PathVariable long id) {
		return new ResponseEntity<QuestionDto>(questionService.updateQuestion(questionDto, id), HttpStatus.OK);
	}

	// delete question by id
	@ApiOperation(value = "REST API to delete the question by ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable long id) {
		questionService.deletePost(id);
		return new ResponseEntity<String>("Question deleted successfully", HttpStatus.OK);
	}
}
