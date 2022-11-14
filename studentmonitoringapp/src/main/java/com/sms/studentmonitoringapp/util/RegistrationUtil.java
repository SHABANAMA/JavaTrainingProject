package com.sms.studentmonitoringapp.util;

import java.util.Date;

import com.sms.studentmonitoringapp.exception.InapropriateAgeException;

public class RegistrationUtil {
	public static boolean validateAge(Date dob) {
		Date date = new Date(System.currentTimeMillis());
		if ((date.getTime() - dob.getTime()) / (1000l * 60 * 60 * 24 * 365) < 18) {
			throw new InapropriateAgeException(
					"The student must have a minimum of 18 years of age to register for a course");
		} else
			return true;
	}
}
