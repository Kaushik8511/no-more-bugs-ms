package com.codehelper.nomorebugs.controller;

import java.util.List;

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

import com.codehelper.nomorebugs.payload.AnswerDto;
import com.codehelper.nomorebugs.payload.RatingDto;
import com.codehelper.nomorebugs.service.AnswerService;
import com.codehelper.nomorebugs.utils.AppConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("REST API for the answers and exposes the endpoints related to answers")
@RestController
@RequestMapping("/v1")
public class AnswerController {

	private AnswerService answerService;

	@Autowired
	public AnswerController(AnswerService answerService) {
		this.answerService = answerService;
	}

	// create new answer
	@ApiOperation(value = "REST API for creating the answer for the question")
	@PostMapping("/questions/{questionId}/answers")
	public ResponseEntity<AnswerDto> createAnswer(@PathVariable("questionId") long questionId,
			@Valid @RequestBody AnswerDto answerDto) {
		AnswerDto responseDto = answerService.createAnswer(questionId, answerDto);
		return new ResponseEntity<AnswerDto>(responseDto, HttpStatus.CREATED);
	}

	// get all the answer of the question
	@ApiOperation(value = "REST API to fetch all the answers of the particular question")
	@GetMapping("/questions/{questionId}/answers")
	public List<AnswerDto> getAnswersByQuestionId(@PathVariable(value = "questionId") long questionId,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir) {
		return answerService.getAnswersByQuestionId(questionId, sortBy, sortDir);
	}

	// get answer by id
	@ApiOperation(value = "REST API to fetch the answer by ID")
	@GetMapping("/questions/{questionId}/answers/{answerId}")
	public ResponseEntity<AnswerDto> getAnswerById(@PathVariable(value = "questionId") long questionId,
			@PathVariable(value = "answerId") long answerId) {

		return new ResponseEntity<AnswerDto>(answerService.getAnswerById(questionId, answerId), HttpStatus.OK);
	}

	// update answer by id
	@ApiOperation(value = "REST API to update the fields of the answer by ID")
	@PutMapping("/questions/{questionId}/answers/{answerId}")
	public ResponseEntity<AnswerDto> updateComment(@PathVariable(value = "questionId") long questionId,
			@PathVariable(value = "answerId") long answerId, @Valid @RequestBody AnswerDto answerDto) {
		return new ResponseEntity<AnswerDto>(answerService.updateAnswer(questionId, answerId, answerDto),
				HttpStatus.OK);
	}

	// delete answer by id
	@ApiOperation(value = "REST API to delete the answer by ID")
	@DeleteMapping("/questions/{questionId}/answers/{answerId}")
	public ResponseEntity<String> deleteAnswer(@PathVariable(value = "questionId") long questionId,
			@PathVariable(value = "answerId") long answerId) {
		answerService.deleteById(questionId, answerId);
		return new ResponseEntity<String>("Answer deleted successfully", HttpStatus.OK);
	}

	// rating the answer
	@ApiOperation(value = "REST API to vote the answer. User can either upvote or downvote the question only once.")
	@PutMapping("/questions/{questionId}/answers/{answerId}/rate")
	public ResponseEntity<String> updateRating(@PathVariable(value = "questionId") long questionId,
			@PathVariable(value = "answerId") long answerId,
			@Valid @RequestBody RatingDto ratingDto) {
		answerService.updateRating(questionId, answerId, ratingDto);
		return new ResponseEntity<String> ("rating updated successully", HttpStatus.OK);
	}
}
