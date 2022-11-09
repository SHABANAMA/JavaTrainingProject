package com.sms.studentmonitoringapp.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sms.studentmonitoringapp.config.CustomProperties;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@Autowired
	private CustomProperties customProperties;
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorObject> handleBadCredentialsException(BadCredentialsException e){
		ErrorObject errorObject = new ErrorObject();
		errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
		errorObject.setMessage(customProperties.getErrormsg());
		return new ResponseEntity<ErrorObject>(errorObject,HttpStatus.OK);
	}

}
