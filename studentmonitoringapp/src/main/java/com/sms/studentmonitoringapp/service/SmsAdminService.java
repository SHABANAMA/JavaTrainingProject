package com.sms.studentmonitoringapp.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sms.studentmonitoringapp.dto.AddCourseRequest;
import com.sms.studentmonitoringapp.dto.AddCourseResponse;
import com.sms.studentmonitoringapp.dto.StudentDetailsEntryRequest;
import com.sms.studentmonitoringapp.dto.StudentDetailsEntryResponse;
import com.sms.studentmonitoringapp.dto.StudentForACourseResponse;
import com.sms.studentmonitoringapp.dto.TotalAndBalanceFeeResponse;
import com.sms.studentmonitoringapp.entity.Course;

public interface SmsAdminService {
	public StudentDetailsEntryResponse enterStudentDetails(StudentDetailsEntryRequest studentDetailsEntryRequest);
	public AddCourseResponse addCourse(AddCourseRequest addCourseRequest);
	public Course updateCourse(String courseName,Course course);
	public String deleteCourse(String courseName);
	public List<Course> courseSearchAll();
	public Course courseSearchByName(String courseName);
	public Map<String,Date> displayAllRegisteredStudents();
	public List<String> displayStudentsWithNoBalance();
	public Map<String,Double> displayStudentsWithBalance();
	public List<StudentForACourseResponse> displayStudentsForACourse(String courseName);
	public List<Course> displayCoursesForAStudent(String userName); 
	public TotalAndBalanceFeeResponse totalAndBalanceFee();
}
