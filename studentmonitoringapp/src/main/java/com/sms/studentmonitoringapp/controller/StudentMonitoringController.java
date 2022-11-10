package com.sms.studentmonitoringapp.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
import com.sms.studentmonitoringapp.dto.CourseRegisterRequest;
import com.sms.studentmonitoringapp.dto.CourseRegisterResponse;
import com.sms.studentmonitoringapp.dto.CourseSearchAllResponse;
import com.sms.studentmonitoringapp.dto.DisplayStudentDetailsResponse;
import com.sms.studentmonitoringapp.dto.StudentDetailsEntryRequest;
import com.sms.studentmonitoringapp.dto.StudentDetailsEntryResponse;
import com.sms.studentmonitoringapp.dto.UserAuthenticationRequest;
import com.sms.studentmonitoringapp.dto.UserAuthenticationResponse;
import com.sms.studentmonitoringapp.entity.Course;
import com.sms.studentmonitoringapp.entity.StudentAcademic;
import com.sms.studentmonitoringapp.entity.User;
import com.sms.studentmonitoringapp.service.StudentMonitoringServiceImpl;
import com.sms.studentmonitoringapp.util.JwtUtil;

@RestController
public class StudentMonitoringController {
	
	@Autowired
	private CustomProperties customProperties;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private StudentMonitoringServiceImpl studentMonitoringServiceImpl;
	
	@PostMapping("/authenticate")
	public ResponseEntity<UserAuthenticationResponse> generateToken(@RequestBody UserAuthenticationRequest userAuthenticationRequest){
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
				(userAuthenticationRequest.getUserName(), userAuthenticationRequest.getPassWord()));
		String jwt = jwtUtil.generateToken(userAuthenticationRequest.getUserName());
		return ResponseEntity.ok(new UserAuthenticationResponse(jwt));
	}
	
	@PostMapping("/adminEnterDetails")
	public StudentDetailsEntryResponse enterStudentDetails(@RequestBody StudentDetailsEntryRequest studentDetailsEntryRequest) {
		return studentMonitoringServiceImpl.enterStudentDetails(studentDetailsEntryRequest);
	}
	
	@GetMapping("/DisplayStudentDetails/{username}")
	public DisplayStudentDetailsResponse getStudentDetails(@PathVariable("username") String username) {
		User user = studentMonitoringServiceImpl.getStudentUserByUserName(username);
		
		StudentAcademic studentAcademic	= studentMonitoringServiceImpl.getStudentAcademicById(user.getUserId());
		
		return new DisplayStudentDetailsResponse(user.getFirstName(),user.getLastName(),user.getEmail(),
				user.getPhone(),user.getDob(),studentAcademic);		 
	}
	
	@Transactional
	@PutMapping("/updateStudentDetailsByStudent/{username}")
	public StudentDetailsEntryResponse updateStudentDetails (@PathVariable("username") String username, @RequestBody StudentDetailsEntryRequest studentDetailsEntryRequest) {
		User user = studentMonitoringServiceImpl.updateStudentUser(username, studentDetailsEntryRequest.getUser());
		studentMonitoringServiceImpl.updateStudentAcademic(user.getUserId(), studentDetailsEntryRequest.getStudentAcademic());
		return new StudentDetailsEntryResponse(customProperties.getUpdatesuccess(),user.getFirstName()+user.getLastName());
	}
	
	@PostMapping("/addCourseDetails")
	public AddCourseResponse addCourse(@RequestBody  AddCourseRequest addCourseRequest) {
		return studentMonitoringServiceImpl.addCourse(addCourseRequest);
	}
	
	@PutMapping("/updateCourseDetails/{courseName}")
	public AddCourseResponse updateCourse(@PathVariable("courseName") String courseName,@RequestBody  AddCourseRequest addCourseRequest) {
		Course course = studentMonitoringServiceImpl.updateCourse(courseName, addCourseRequest.getCourse());
		return new AddCourseResponse(customProperties.getUpdatesuccess(), course);
	}
	
	@DeleteMapping("/deleteCourseDetails/{courseName}")
	public String deleteCourse(@PathVariable("courseName") String courseName) {
		return studentMonitoringServiceImpl.deleteCourse(courseName);
	}
	
	@GetMapping("/listAllCourses")
	public CourseSearchAllResponse courseSearchAll() {
		return new CourseSearchAllResponse(studentMonitoringServiceImpl.courseSearchAll());
	}
	
	@GetMapping("/listCourseByName/{courseName}")
	public Course courseSearchByName (@PathVariable("courseName") String courseName) {
		return studentMonitoringServiceImpl.courseSearchByName(courseName);
	}
	
	@PostMapping("/registerForACourse/{userName}")
	public CourseRegisterResponse registerCourse (@PathVariable("userName") String userName,@RequestBody CourseRegisterRequest courseRegisterRequest) {
		return new CourseRegisterResponse(customProperties.getRegistersuccess(),studentMonitoringServiceImpl.registerCourse(userName, courseRegisterRequest));
	}
	
}
