package com.sms.studentmonitoringapp.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.sms.studentmonitoringapp.dto.AddCourseRequest;
import com.sms.studentmonitoringapp.dto.AddCourseResponse;
import com.sms.studentmonitoringapp.dto.CourseRegisterRequest;
import com.sms.studentmonitoringapp.dto.StudentDetailsEntryRequest;
import com.sms.studentmonitoringapp.dto.StudentDetailsEntryResponse;
import com.sms.studentmonitoringapp.entity.Course;
import com.sms.studentmonitoringapp.entity.RegisteredCourse;
import com.sms.studentmonitoringapp.entity.StudentAcademic;
import com.sms.studentmonitoringapp.entity.User;

public interface StudentMonitoringService extends UserDetailsService{
	public StudentDetailsEntryResponse enterStudentDetails(StudentDetailsEntryRequest studentDetailsEntryRequest);
	public User getStudentUserByUserName(String username);
	public StudentAcademic getStudentAcademicById(Long studentId);
	public User updateStudentUser(String username, User user);
	public StudentAcademic updateStudentAcademic(Long id, StudentAcademic studentAcademic);
	public AddCourseResponse addCourse(AddCourseRequest addCourseRequest);
	public Course updateCourse(String courseName,Course course);
	public String deleteCourse(String courseName);
	public List<Course> courseSearchAll();
	public Course courseSearchByName(String courseName);
	public RegisteredCourse registerCourse(String userName,CourseRegisterRequest courseRegisterRequest);
}
