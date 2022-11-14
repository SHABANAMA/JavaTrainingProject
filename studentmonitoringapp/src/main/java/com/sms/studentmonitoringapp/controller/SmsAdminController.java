package com.sms.studentmonitoringapp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sms.studentmonitoringapp.config.CustomProperties;
import com.sms.studentmonitoringapp.dto.AddCourseRequest;
import com.sms.studentmonitoringapp.dto.AddCourseResponse;
import com.sms.studentmonitoringapp.dto.CourseSearchAllResponse;
import com.sms.studentmonitoringapp.dto.RegisteredStudentsAllResponse;
import com.sms.studentmonitoringapp.dto.StudentDetailsEntryRequest;
import com.sms.studentmonitoringapp.dto.StudentDetailsEntryResponse;
import com.sms.studentmonitoringapp.entity.Course;
import com.sms.studentmonitoringapp.service.SmsAdminServiceImpl;

@RestController
public class SmsAdminController {

	@Autowired
	private CustomProperties customProperties;
	
	@Autowired
	private SmsAdminServiceImpl smsAdminServiceImpl;
	
	@PostMapping("/adminEnterDetails")
	public StudentDetailsEntryResponse enterStudentDetails(@RequestBody StudentDetailsEntryRequest studentDetailsEntryRequest) {
		return smsAdminServiceImpl.enterStudentDetails(studentDetailsEntryRequest);
	}
	
	@PostMapping("/addCourseDetails")
	public AddCourseResponse addCourse(@RequestBody  AddCourseRequest addCourseRequest) {
		return smsAdminServiceImpl.addCourse(addCourseRequest);
	}
	
	@PutMapping("/updateCourseDetails/{courseName}")
	public AddCourseResponse updateCourse(@PathVariable("courseName") String courseName,@RequestBody  AddCourseRequest addCourseRequest) {
		Course course = smsAdminServiceImpl.updateCourse(courseName, addCourseRequest.getCourse());
		return new AddCourseResponse(customProperties.getUpdatesuccess(), course);
	}
	
	@DeleteMapping("/deleteCourseDetails/{courseName}")
	public String deleteCourse(@PathVariable("courseName") String courseName) {
		return smsAdminServiceImpl.deleteCourse(courseName);
	}
	
	@GetMapping("/listAllCourses")
	public CourseSearchAllResponse courseSearchAll() {
		return new CourseSearchAllResponse(smsAdminServiceImpl.courseSearchAll());
	}
	
	@GetMapping("/listCourseByName/{courseName}")
	public Course courseSearchByName (@PathVariable("courseName") String courseName) {
		return smsAdminServiceImpl.courseSearchByName(courseName);
	}
	
	@GetMapping("/listAllRegisteredStudents")
	public RegisteredStudentsAllResponse displayAllRegisteredStudents() {
		return new RegisteredStudentsAllResponse("REGISTERED STUDENTS ARE:-",smsAdminServiceImpl.displayAllRegisteredStudents());
	}
	
	@GetMapping("/listStudentsWithNoBalancePay")
	public List<String> displayStudentsWithNoBalance(){
		return smsAdminServiceImpl.displayStudentsWithNoBalance();
	}
	
	@GetMapping("/listStudentsWithBalancePay")
	public Map<String,Double> displayStudentsWithBalance(){
		return smsAdminServiceImpl.displayStudentsWithBalance();
	}
	
}
