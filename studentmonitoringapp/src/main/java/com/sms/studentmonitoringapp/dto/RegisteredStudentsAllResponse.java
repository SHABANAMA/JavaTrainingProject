package com.sms.studentmonitoringapp.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredStudentsAllResponse {
	private String status;
	private List<String> ListOfRegisteredStudents;
}
