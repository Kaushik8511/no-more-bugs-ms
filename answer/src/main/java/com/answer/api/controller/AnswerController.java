package com.answer.api.controller;

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

import com.answer.api.DTO.RequestAnswerDTO;
import com.answer.api.DTO.ResponseAnswerDTO;
import com.answer.api.service.AnswerService;

//@Api("REST API for the answers and exposes the endpoints related to answers")
@RestController
@RequestMapping("/v1/answers")
public class AnswerController {
	
	@Autowired
	private AnswerService answerService;

	// create new answer
//	@ApiOperation(value = "REST API for creating the answer for the question")
	@PostMapping("/questions/{questionId}")
	public ResponseEntity<ResponseAnswerDTO> createAnswer(@PathVariable("questionId") String questionId, @RequestBody RequestAnswerDTO answerDto) {
		ResponseAnswerDTO responseDto = answerService.createAnswer(questionId, answerDto);
		return new ResponseEntity<ResponseAnswerDTO>(responseDto, HttpStatus.CREATED);
	}

	// get all the answers of the question
//	@ApiOperation(value = "REST API to fetch all the answers of the particular question")
	@GetMapping("/questions/{questionId}")
	public ResponseEntity<List<ResponseAnswerDTO>> getAnswersByQuestionId(@PathVariable(value = "questionId") String questionId) {
		return new ResponseEntity<List<ResponseAnswerDTO>>(answerService.getAnswersByQuestionId(questionId), HttpStatus.OK);
	}

	// get answer by id
//	@ApiOperation(value = "REST API to fetch the answer by ID")
	@GetMapping("/{answerId}/questions/{questionId}")
	public ResponseEntity<ResponseAnswerDTO> getAnswerById(@PathVariable(value = "questionId") String questionId,
			@PathVariable(value = "answerId") String answerId) {

		return new ResponseEntity<ResponseAnswerDTO>(answerService.getAnswerById(questionId, answerId), HttpStatus.OK);
	}

	// update answer by id
//	@ApiOperation(value = "REST API to update the fields of the answer by ID")
	@PutMapping("/{answerId}/questions/{questionId}")
	public ResponseEntity<ResponseAnswerDTO> updateAnswer(@PathVariable(value = "questionId") String questionId,
			@PathVariable(value = "answerId") String answerId, @RequestBody RequestAnswerDTO answerDto) {
		return new ResponseEntity<ResponseAnswerDTO>(answerService.updateAnswer(questionId, answerId, answerDto),
				HttpStatus.OK);
	}

	// delete answer by id
//	@ApiOperation(value = "REST API to delete the answer by ID")
	@DeleteMapping("/{answerId}/questions/{questionId}")
	public ResponseEntity<String> deleteAnswer(@PathVariable(value = "questionId") String questionId,
			@PathVariable(value = "answerId") String answerId) {
		answerService.deleteById(questionId, answerId);
		return new ResponseEntity<String>("Answer deleted successfully", HttpStatus.OK);
	}

//	// rating the answer
//	@ApiOperation(value = "REST API to vote the answer. User can either upvote or downvote the question only once.")
//	@PutMapping("/questions/{questionId}/answers/{answerId}/rate")
//	public ResponseEntity<String> updateRating(@PathVariable(value = "questionId") long questionId,
//			@PathVariable(value = "answerId") long answerId,
//			@Valid @RequestBody RatingDto ratingDto) {
//		answerService.updateRating(questionId, answerId, ratingDto);
//		return new ResponseEntity<String> ("rating updated successully", HttpStatus.OK);
//	}
}
