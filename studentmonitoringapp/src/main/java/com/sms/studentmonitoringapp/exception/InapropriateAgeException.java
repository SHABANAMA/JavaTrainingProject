package com.sms.studentmonitoringapp.exception;

public class InapropriateAgeException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public InapropriateAgeException(String message) {
		super(message);
	}
}
