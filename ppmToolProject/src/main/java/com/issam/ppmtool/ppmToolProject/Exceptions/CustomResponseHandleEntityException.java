package com.issam.ppmtool.ppmToolProject.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseHandleEntityException extends ResponseEntityExceptionHandler{

	@ExceptionHandler
	public final ResponseEntity<Object> handelProjectIdException(ProjectIdException ex , WebRequest request){
		
		ProjectIdExceptionResponse exceptionResponse = new ProjectIdExceptionResponse(ex.getMessage());
		return new ResponseEntity<>(exceptionResponse , HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public final ResponseEntity<Object> handelProjectNotFoundException(ProjectNotFoundException ex , WebRequest request){
		
		ProjectNotFoundExceptionResponse exceptionResponse = new ProjectNotFoundExceptionResponse(ex.getMessage());
		return new ResponseEntity<>(exceptionResponse , HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public final ResponseEntity<Object> handelUniqueLoginException(UniqueLoginException ex, WebRequest request){
		UniqueLoginExceptionResponse exceptionResponse = new UniqueLoginExceptionResponse(ex.getMessage());
		return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
	}
}
