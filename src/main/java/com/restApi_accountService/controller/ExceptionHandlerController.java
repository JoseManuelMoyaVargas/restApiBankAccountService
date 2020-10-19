package com.restApi_accountService.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import restApi_accountService.exceptions.AccountNotFoundException;

/*
@RestControllerAdvice
public class ExceptionHandlerController {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Error handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        BindingResult result = ex.getBindingResult();
        
        List<String> errorList = new ArrayList<>();
        result.getFieldErrors().forEach((fieldError) -> {
        	errorList.add("Error in object '"+fieldError.getObjectName()+"' on field '"+fieldError.getField()+"' : rejected value [" +fieldError.getRejectedValue() +"]" );
        });
        result.getGlobalErrors().forEach((fieldError) -> {
        	errorList.add(fieldError.getObjectName()+" : " +fieldError.getDefaultMessage() );
        });
        
        return new Error(HttpStatus.BAD_REQUEST, "Bad values given!", errorList);
       
    }
	

    public static class Error{
    	private int errorCode;
    	private String error;
    	private String errorMessage;
    	private List<String> fieldErrors = new ArrayList<>();
    	
    	public Error(HttpStatus status, String message, List<String> fieldErrors ) {
    		this.errorCode = status.value();
    		this.error = status.name();
    		this.errorMessage = message;
    		this.fieldErrors = fieldErrors;
    	}

		public int getErrorCode() {
			return errorCode;
		}

		public void setErrorCode(int errorCode) {
			this.errorCode = errorCode;
		}

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}

		public String getErrorMessage() {
			return errorMessage;
		}

		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}

		public List<String> getFieldErrors() {
			return fieldErrors;
		}

		public void setFieldErrors(List<String> fieldErrors) {
			this.fieldErrors = fieldErrors;
		}
	  }
	
    
}
*/
@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

		
	//@ExceptionHandler(HttpMessageNotReadableException.class)
	@Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, 
    		HttpHeaders headers, HttpStatus status, WebRequest request) { 
    	

    	Map<String, Object> body = new LinkedHashMap<>();
    	body.put("timestamp", LocalDate.now());
        body.put("status", 400);
        body.put("message", "Invalid data" + ex.getMessage());
        body.put("errors", "BAD_DATA. "+ ex.getLocalizedMessage());
        
        
    	return new ResponseEntity<>(body,HttpStatus.BAD_REQUEST );
    }
	@ExceptionHandler(AccountNotFoundException.class)
    protected ResponseEntity<Object> handleMethodAccountNotFound(AccountNotFoundException ex) { 
   
    	Map<String, Object> body = new LinkedHashMap<>();
    	body.put("timestamp", LocalDate.now());
        body.put("status", 400);
        body.put("message", "Account not found");
        
        
    	return new ResponseEntity<>(body,HttpStatus.BAD_REQUEST );
    }
	
	
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, 
        HttpStatus status, WebRequest request) {

        BindingResult result = ex.getBindingResult();

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("status", status.value());
        body.put("message", "Invalid data.");
        
        List<String> errorList = new ArrayList<>();
        result.getFieldErrors().forEach((fieldError) -> {
        	errorList.add("Error in object '"+fieldError.getObjectName()+"' on field '"+fieldError.getField()+"' : rejected value [" +fieldError.getRejectedValue() +"]" );
        });
        result.getGlobalErrors().forEach((fieldError) -> {
        	errorList.add(fieldError.getObjectName()+" : " +fieldError.getDefaultMessage() );
        });

       
        body.put("errors", errorList);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    

    
}
