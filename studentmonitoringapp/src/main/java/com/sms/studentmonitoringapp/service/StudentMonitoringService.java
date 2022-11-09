package com.sms.studentmonitoringapp.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.sms.studentmonitoringapp.dto.StudentDetailsEntryRequest;
import com.sms.studentmonitoringapp.dto.StudentDetailsEntryResponse;
import com.sms.studentmonitoringapp.entity.StudentAcademic;
import com.sms.studentmonitoringapp.entity.User;

public interface StudentMonitoringService extends UserDetailsService{
	public StudentDetailsEntryResponse enterStudentDetails(StudentDetailsEntryRequest studentDetailsEntryRequest);
	public User getStudentUserByUserName(String username);
	public StudentAcademic getStudentAcademicById(Long studentId);
	public User updateStudentUser(String username, User user);
	public StudentAcademic updateStudentAcademic(Long id, StudentAcademic studentAcademic);
}
