package com.sms.studentmonitoringapp.advice;

import java.util.ArrayList;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sms.studentmonitoringapp.config.CustomProperties;
import com.sms.studentmonitoringapp.exception.InapropriateAgeException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Autowired
	private CustomProperties customProperties;

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorObject> handleBadCredentialsException(BadCredentialsException e) {
		ErrorObject errorObject = new ErrorObject();
		errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
		errorObject.setMessage(customProperties.getErrormsg());
		return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.OK);
	}

	@ExceptionHandler(InapropriateAgeException.class)
	public ResponseEntity<ErrorObject> handleInapropriateAgeException(InapropriateAgeException e) {
		ErrorObject errorObject = new ErrorObject();
		errorObject.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errorObject.setMessage(e.getMessage());
		return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.OK);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorObject> handleBadCredentialsException(ConstraintViolationException e) {
		ErrorObject errorObject = new ErrorObject();
		errorObject.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errorObject.setMessage(((ConstraintViolation<?>)(new ArrayList<Object>(e.getConstraintViolations()).get(0))).getMessage());
		return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.OK);
	}
	
}
