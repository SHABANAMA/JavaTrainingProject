package com.sms.studentmonitoringapp.service;

import com.sms.studentmonitoringapp.dto.CourseRegisterRequest;
import com.sms.studentmonitoringapp.entity.RegisteredCourse;
import com.sms.studentmonitoringapp.entity.StudentAcademic;
import com.sms.studentmonitoringapp.entity.User;

public interface SmsStudentService {
	public User getStudentUserByUserName(String username);
	public StudentAcademic getStudentAcademicById(Long studentId);
	public User updateStudentUser(String username, User user);
	public StudentAcademic updateStudentAcademic(Long id, StudentAcademic studentAcademic);
	public RegisteredCourse registerCourse(String userName,CourseRegisterRequest courseRegisterRequest);
}
