package com.api.handle;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import com.api.exceptions.DepartmentNotFoundException;
import com.api.exceptions.ErrorResponse;
import com.api.exceptions.ObjectNotValideException;
import com.api.exceptions.StudentNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ObjectNotValideException.class)
	public String handleException(ObjectNotValideException e) {
		return e.getErrorMessages().toString();

	}
	
	@ExceptionHandler(StudentNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleException(StudentNotFoundException e) {
		ErrorResponse error = new ErrorResponse(
				"Student Not Found",
				e.getMessage()
				);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(DepartmentNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleException(DepartmentNotFoundException e) {
		ErrorResponse error = new ErrorResponse(
				"Department Not Found!",
				e.getMessage()
				);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(InternalServerError.class)
	public ResponseEntity<ErrorResponse> handleException(InternalServerError e) {
		ErrorResponse error = new ErrorResponse(
				"Internal Server Error!",
				e.getMessage()
				);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

}
