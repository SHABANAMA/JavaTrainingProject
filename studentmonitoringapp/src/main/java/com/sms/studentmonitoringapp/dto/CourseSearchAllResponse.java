package com.sms.studentmonitoringapp.dto;

import java.util.List;

import com.sms.studentmonitoringapp.entity.Course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseSearchAllResponse {
	private List<Course> courseList;
}
