package com.sms.studentmonitoringapp.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sms.studentmonitoringapp.config.CustomProperties;
import com.sms.studentmonitoringapp.dto.CourseRegisterRequest;
import com.sms.studentmonitoringapp.dto.CourseRegisterResponse;
import com.sms.studentmonitoringapp.dto.DisplayStudentDetailsResponse;
import com.sms.studentmonitoringapp.dto.StudentDetailsEntryRequest;
import com.sms.studentmonitoringapp.dto.StudentDetailsEntryResponse;
import com.sms.studentmonitoringapp.entity.StudentAcademic;
import com.sms.studentmonitoringapp.entity.User;
import com.sms.studentmonitoringapp.service.SmsStudentServiceImpl;

@RestController
public class SmsStudentController {

	@Autowired
	private CustomProperties customProperties;

	@Autowired
	private SmsStudentServiceImpl smsStudentServiceImpl;

	@GetMapping("/DisplayStudentDetails/{username}")
	public DisplayStudentDetailsResponse getStudentDetails(@PathVariable("username") String username) {
		User user = smsStudentServiceImpl.getStudentUserByUserName(username);

		StudentAcademic studentAcademic = smsStudentServiceImpl.getStudentAcademicById(user.getUserId());

		return new DisplayStudentDetailsResponse(user.getFirstName(), user.getLastName(), user.getEmail(),
				user.getPhone(), user.getDob(), studentAcademic);
	}

	@Transactional
	@PutMapping("/updateStudentDetailsByStudent/{username}")
	public StudentDetailsEntryResponse updateStudentDetails(@PathVariable("username") String username,
			@RequestBody StudentDetailsEntryRequest studentDetailsEntryRequest) {
		User user = smsStudentServiceImpl.updateStudentUser(username, studentDetailsEntryRequest.getUser());
		smsStudentServiceImpl.updateStudentAcademic(user.getUserId(),
				studentDetailsEntryRequest.getStudentAcademic());
		return new StudentDetailsEntryResponse(customProperties.getUpdatesuccess(),
				user.getFirstName() + user.getLastName());
	}

	@PostMapping("/registerForACourse/{userName}")
	public CourseRegisterResponse registerCourse(@PathVariable("userName") String userName,
			@RequestBody CourseRegisterRequest courseRegisterRequest) {
		return new CourseRegisterResponse(customProperties.getRegistersuccess(),
				smsStudentServiceImpl.registerCourse(userName, courseRegisterRequest));
	}
}
