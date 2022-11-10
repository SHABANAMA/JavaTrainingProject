package com.sms.studentmonitoringapp.dto;

import com.sms.studentmonitoringapp.entity.Course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddCourseRequest {
	private Course course;
}
