package com.example.cms.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.cms.exception.BlogAlreadyExistsByTitleException;
import com.example.cms.exception.BlogNotAvailableByTitleException;
import com.example.cms.exception.BlogNotFoundByIdException;
import com.example.cms.exception.IllegalAccessRequestException;
import com.example.cms.exception.PanelNotFoundByIdException;
import com.example.cms.exception.TopicNotSpecifiedException;
import com.example.cms.exception.UserAlreadyExistByEmailException;
import com.example.cms.exception.UserNotFoundByIdException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestControllerAdvice
public class ApplicationExceptionHandler {

	private ErrorStructure<String> errorStructure;
	
	private ResponseEntity<ErrorStructure<String>> errorResonse(HttpStatus status, String message, String rootCause){
		return new ResponseEntity<ErrorStructure<String>>(errorStructure.setStatusCode(status.value())
				.setMessage(message)
				.setRootCause(rootCause),status);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleUserAlreadyExistByEmail(UserAlreadyExistByEmailException ex){
		return errorResonse(HttpStatus.BAD_REQUEST, ex.getMessage(), "User already exist with the email");
	}
	

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleUserNotFoundById(UserNotFoundByIdException ex){
		return errorResonse(HttpStatus.BAD_REQUEST, ex.getMessage(), "The id you are trying to get is not available plese try again with different id");
	}
 
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleBlogAlreadyExistByTitle(BlogAlreadyExistsByTitleException ex){
		return errorResonse(HttpStatus.BAD_REQUEST, ex.getMessage(), "The blog title you entered already exist by the title");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleBlogNotAvailableByTitle(BlogNotAvailableByTitleException ex){
		return errorResonse(HttpStatus.BAD_REQUEST, ex.getMessage(), "The blog title is not available please search for some other or try adding it out");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleBlogNotFoundById(BlogNotFoundByIdException ex){
		return errorResonse(HttpStatus.BAD_REQUEST, ex.getMessage(), "The id you are trying to get is not available plese try again with different id");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleTopicNotSpecified(TopicNotSpecifiedException ex){
		return errorResonse(HttpStatus.BAD_REQUEST, ex.getMessage(), "The topic you are trying to get is not available");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleIllegalAccessRequest(IllegalAccessRequestException ex){
		return errorResonse(HttpStatus.BAD_REQUEST, ex.getMessage(), "You dont have right to add the user");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handlePanelNotFoundById(PanelNotFoundByIdException ex){
		return errorResonse(HttpStatus.BAD_REQUEST,ex.getMessage(), "The id you are trying to get is not available plese try again with different id");
	}
}
