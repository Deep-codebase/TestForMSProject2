package com.restapi.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{

	
	  @ExceptionHandler(Exception.class) public ResponseEntity
	  handlAllException(Exception ex, WebRequest webreq){
	  
	  ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
	  ex.toString(), webreq.getDescription(false));
	  
	  return new ResponseEntity(exceptionResponse,
	  HttpStatus.INTERNAL_SERVER_ERROR); }
	 
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity handleuserNotFoundException(UserNotFoundException ex, WebRequest webreq){
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), webreq.getDescription(false));
	
		return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
	}
}
