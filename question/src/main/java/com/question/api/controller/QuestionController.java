package com.question.api.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.question.api.DTO.RequestQuestionDTO;
import com.question.api.DTO.ResponseQuestionDTO;
import com.question.api.service.QuestionService;

@RestController
@RequestMapping("/v1/questions")
public class QuestionController {

	// loose coupling as we are using interface
	@Autowired
	private QuestionService questionService;


	// Create question and save it to the database
	@PostMapping
	public ResponseEntity<ResponseQuestionDTO> createQuestion(@RequestBody RequestQuestionDTO questionDto) {

		return new ResponseEntity<>(questionService.createQuestion(questionDto), HttpStatus.CREATED);
	}

	// get all questions
//	@ApiOperation(value = "REST API to fetch all the questions. You can also provide query parameters for pagination and sorting")
	@GetMapping
	public ResponseEntity<List<ResponseQuestionDTO>> findAll() {
		return new ResponseEntity<>(questionService.getAllQuestions(),HttpStatus.OK);
	}

	// search questions
//	@ApiOperation(value = "REST API to search questions. You can also provide query parameters for pagination and sorting")
//	@GetMapping("/search")
//	public ResponseEntity<QuestionsResponse> findAll(
//			@RequestParam(value = "searchBy") String searchBy,
//			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
//			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
//			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
//			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir) {
//		return new ResponseEntity<QuestionsResponse>(questionService.searchQuestions("%" + searchBy + "%", pageNo, pageSize, sortBy, sortDir),
//				HttpStatus.OK);
//	}

	// get question by id
//	@ApiOperation(value = "REST API to fetch question by ID")
	@GetMapping("/{id}")
	public ResponseEntity<ResponseQuestionDTO> getQuestionById(@PathVariable String id) {
		return new ResponseEntity<>(questionService.getQuestionById(id), HttpStatus.OK);
	}

	// update question by id
//	@ApiOperation(value = "REST API to update the fields of the question by ID")
	@PutMapping("/{id}")
	public ResponseEntity<ResponseQuestionDTO> updateQuestion(@RequestBody RequestQuestionDTO questionDto,
			@PathVariable String id) {
		return new ResponseEntity<>(questionService.updateQuestion(questionDto, id), HttpStatus.OK);
	}

	// delete question by id
//	@ApiOperation(value = "REST API to delete the question by ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable String id) {
		questionService.deleteQuestion(id);
		return new ResponseEntity<String>("Question deleted successfully", HttpStatus.OK);
	}
}