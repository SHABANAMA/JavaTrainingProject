package com.sms.studentmonitoringapp.dto;

import java.util.Date;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredStudentsAllResponse {
	private String status;
	private Map<String,Date> ListOfRegisteredStudents;
}
