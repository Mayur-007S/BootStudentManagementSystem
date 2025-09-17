package com.api.handle;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import com.api.exceptions.DepartmentNotFoundException;
import com.api.exceptions.ObjectNotValideException;
import com.api.exceptions.StudentNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(IllegalStateException.class)
	public String handleException(IllegalStateException e) {
		return e.getMessage();
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public String handleException(EntityNotFoundException e) {
		return e.getMessage();

	}

	@ExceptionHandler(ObjectNotValideException.class)
	public String handleException(ObjectNotValideException e) {
		return e.getErrorMessages().toString();

	}
	
	@ExceptionHandler(StudentNotFoundException.class)
	public String handleException(StudentNotFoundException e) {
		return e.getMessage();
	}
	
	@ExceptionHandler(DepartmentNotFoundException.class)
	public String handleException(DepartmentNotFoundException e) {
		return e.getMessage();
	}
	
	@ExceptionHandler(InternalServerError.class)
	public String handleException(InternalServerError e) {
		return e.getMessage();
	}

}
