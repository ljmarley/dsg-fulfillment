package com.dcsg.fulfillment.packshack.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	  @ExceptionHandler(ResourceNotFoundException.class)
	  public final ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
	    ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
	        request.getDescription(false));
	    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	  }
	  
	   @Override
	   public final ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
			   HttpStatus status, WebRequest request) {
		    ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
			        request.getDescription(false));
		    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	   }

	   @ExceptionHandler(Exception.class)
	  public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {
	    ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
	        request.getDescription(false));
	    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
}
