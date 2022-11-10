package com.sms.studentmonitoringapp.dto;

import com.sms.studentmonitoringapp.entity.RegisteredCourse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseRegisterResponse {
	private String status;
	private RegisteredCourse registeredCourse;
}
