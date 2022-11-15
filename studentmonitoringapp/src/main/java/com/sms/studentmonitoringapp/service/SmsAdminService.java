package com.sms.studentmonitoringapp.service;

import java.util.List;

import com.sms.studentmonitoringapp.dto.AddCourseRequest;
import com.sms.studentmonitoringapp.dto.AddCourseResponse;
import com.sms.studentmonitoringapp.dto.StudentDetailsEntryRequest;
import com.sms.studentmonitoringapp.dto.StudentDetailsEntryResponse;
import com.sms.studentmonitoringapp.dto.StudentForACourseResponse;
import com.sms.studentmonitoringapp.dto.TotalAndBalanceFeeResponse;
import com.sms.studentmonitoringapp.entity.Course;

public interface SmsAdminService {
	public StudentDetailsEntryResponse enterStudentDetails(StudentDetailsEntryRequest studentDetailsEntryRequest);
	public StudentDetailsEntryResponse updateStudent(String studentName, StudentDetailsEntryRequest studentDetailsEntryRequest);
	public String deleteStudent(String userName);
	public List<StudentForACourseResponse> listAllStudents();
	
	public AddCourseResponse addCourse(AddCourseRequest addCourseRequest);
	public Course updateCourse(String courseName,Course course);
	public String deleteCourse(String courseName);
	public List<Course> courseSearchAll();
	public Course courseSearchByName(String courseName);
	public List<String> displayAllRegisteredStudents();
	public List<String> displayStudentsWithNoBalance();
	public List<String> displayStudentsWithBalance();
	public List<StudentForACourseResponse> displayStudentsForACourse(String courseName);
	public List<Course> displayCoursesForAStudent(String userName); 
	public TotalAndBalanceFeeResponse totalAndBalanceFee();
}
