package com.Bikkadit.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.Bikkadit.blog.payloads.ApiResponse;

@ControllerAdvice     //For declaring As Exception handler across multiple controller
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
public ResponseEntity<ApiResponse> ResourceNotFoundExceptionHandler(ResourceNotFoundException ex)
{
		String message = ex.getMessage();
		ApiResponse api=new ApiResponse(message,false);
	return new ResponseEntity<ApiResponse>(api,HttpStatus.NOT_FOUND);
	
}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> MethodArgumentNotValidException(MethodArgumentNotValidException ex)
	{
	Map<String, String> map=new HashMap<>();
	ex.getBindingResult().getAllErrors().forEach((error)->{
		String field = ((FieldError)error).getField();
		String defaultMessage = error.getDefaultMessage();
	map.put(field, defaultMessage);
		
	});
		return new ResponseEntity<Map<String, String>>(map,HttpStatus.BAD_REQUEST);
		
	}
	@ExceptionHandler(InvalidUserNameAndPasswordException.class)
public ResponseEntity<ApiResponse> InvalidUserNameAndPasswordExceptionhandler(InvalidUserNameAndPasswordException ex)
{
		String message = ex.getMessage();
		ApiResponse api=new ApiResponse(message,false);
	return new ResponseEntity<ApiResponse>(api,HttpStatus.BAD_REQUEST);
	
}
}
