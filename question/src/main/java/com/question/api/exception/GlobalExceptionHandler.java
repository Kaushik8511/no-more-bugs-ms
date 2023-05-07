package com.question.api.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.question.api.DTO.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	

	// specific exception handler
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> resourceNotFoundHandler(ResourceNotFoundException exception,
			WebRequest webRequest) {
		ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage(),
				new Date().toString(), webRequest.getDescription(false));

		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
	}

	// specific exception handler
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ErrorResponse> apiExceptionHandler(ApiException exception,
			WebRequest webRequest) {
		ErrorResponse error = new ErrorResponse(exception.getStatus().value(), exception.getMessage(),
				new Date().toString(), webRequest.getDescription(false));

		return new ResponseEntity<ErrorResponse>(error, exception.getStatus());
	}

	// generic exception handler
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> globalExceptionHandler(Exception exception,
			WebRequest webRequest) {
		ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(),
				new Date().toString(), webRequest.getDescription(false));

		return new ResponseEntity<ErrorResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
}
