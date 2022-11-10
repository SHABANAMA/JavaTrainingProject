package com.sms.studentmonitoringapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sms.studentmonitoringapp.config.CustomProperties;
import com.sms.studentmonitoringapp.dto.AddCourseRequest;
import com.sms.studentmonitoringapp.dto.AddCourseResponse;
import com.sms.studentmonitoringapp.dto.CourseRegisterRequest;
import com.sms.studentmonitoringapp.dto.StudentDetailsEntryRequest;
import com.sms.studentmonitoringapp.dto.StudentDetailsEntryResponse;
import com.sms.studentmonitoringapp.entity.Course;
import com.sms.studentmonitoringapp.entity.RegisteredCourse;
import com.sms.studentmonitoringapp.entity.StudentAcademic;
import com.sms.studentmonitoringapp.entity.User;
import com.sms.studentmonitoringapp.repository.CourseRepository;
import com.sms.studentmonitoringapp.repository.RegisteredCourseRepository;
import com.sms.studentmonitoringapp.repository.StudentAcademicRepository;
import com.sms.studentmonitoringapp.repository.UserRepository;

@Service
public class StudentMonitoringServiceImpl implements StudentMonitoringService{

	@Autowired
	private CustomProperties customProperties;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private StudentAcademicRepository studentAcademicRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private RegisteredCourseRepository registeredCourseRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(username);
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassWord(), new ArrayList<>());
	}

	@Transactional
	@Override
	public StudentDetailsEntryResponse enterStudentDetails(StudentDetailsEntryRequest studentDetailsEntryRequest) {
		User user = studentDetailsEntryRequest.getUser();
		user = userRepository.save(user);
		
		StudentAcademic studentAcademic = studentDetailsEntryRequest.getStudentAcademic();
		studentAcademic.setStudentId(user.getUserId());
		studentAcademic = studentAcademicRepository.save(studentAcademic);
		
		return new StudentDetailsEntryResponse(customProperties.getEntrysuccess(),user.getFirstName()+user.getLastName());
	}

	@Override
	public User getStudentUserByUserName(String username) {
		Optional<User> opt = Optional.ofNullable(userRepository.findByUserName(username));
		if(opt.isPresent()) {
			return opt.get();
		}
		else
			return null;
	}

	@Override
	public StudentAcademic getStudentAcademicById(Long studentId) {
		Optional<StudentAcademic> opt = studentAcademicRepository.findById(studentId);
		if(opt.isPresent()) {
			return opt.get();
		}
		else
			return null;
	}
	
	@Override
	public User updateStudentUser(String username, User user) {
		Optional<User> opt = Optional.ofNullable(userRepository.findByUserName(username));
		User dbUser=null;
		if(opt.isPresent()) {
			dbUser = opt.get();
			dbUser.setEmail(user.getEmail());
			dbUser.setPhone(user.getPhone());
			userRepository.save(dbUser);
			return dbUser;
		}
		else 
			return null;
	}

	@Override
	public StudentAcademic updateStudentAcademic(Long id, StudentAcademic studentAcademic) {
		Optional<StudentAcademic> opt = studentAcademicRepository.findById(id);
		StudentAcademic dbstudentAcademic=null;
		if(opt.isPresent()) {
			dbstudentAcademic = opt.get();
			dbstudentAcademic.setQualification(studentAcademic.getQualification());
			dbstudentAcademic.setCollege(studentAcademic.getCollege());
			dbstudentAcademic.setUniversity(studentAcademic.getUniversity());
			dbstudentAcademic.setPassoutYear(studentAcademic.getPassoutYear());
			studentAcademicRepository.save(dbstudentAcademic);
			return dbstudentAcademic;
		}
		return null;
	}

	@Override
	public AddCourseResponse addCourse(AddCourseRequest addCourseRequest) {
		Course course = addCourseRequest.getCourse();
		course = courseRepository.save(course);
		return new AddCourseResponse(customProperties.getSavesuccess(), course);
	}

	@Override
	public Course updateCourse(String courseName, Course course) {
		Optional<Course> opt = Optional.ofNullable(courseRepository.findByCourseName(courseName));
		Course dbCourse = null;
		if(opt.isPresent()) {
			dbCourse = opt.get();
			dbCourse.setCourseName(course.getCourseName());
			dbCourse.setDescription(course.getDescription());
			dbCourse.setFees(course.getFees());
			dbCourse = courseRepository.save(dbCourse);
			return dbCourse;
		}
		else
			return null;
	}

	@Override
	public String deleteCourse(String courseName) {
		Optional<Course> opt = Optional.ofNullable(courseRepository.findByCourseName(courseName));
		if(opt.isPresent()) {
			Course course = opt.get();
			courseRepository.deleteById(course.getCourseId());
			return customProperties.getDeletesuccess()+courseName;
		}
		return null;
	}

	@Override
	public List<Course> courseSearchAll() {
		return courseRepository.findAll();
	}

	@Override
	public Course courseSearchByName(String courseName) {
		Optional<Course> opt = Optional.ofNullable(courseRepository.findByCourseName(courseName));
		if(opt.isPresent()) {
			Course course = opt.get();
			return course;
		}
		return null;
	}

	@Override
	public RegisteredCourse registerCourse(String userName,CourseRegisterRequest courseRegisterRequest) {
		RegisteredCourse registeredCourse = new RegisteredCourse();
		double balance;
		Optional<User> opt = Optional.ofNullable(userRepository.findByUserName(userName));
		if (opt.isPresent()) {
			User user = opt.get();
			registeredCourse.setStudentId(user.getUserId());
		}
		
		Optional<Course> opt1 = Optional.ofNullable(courseRepository.findByCourseName(courseRegisterRequest.getCourseName()));
		if (opt1.isPresent()) {
			Course course = opt1.get();
			registeredCourse.setCourseId(course.getCourseId());
			registeredCourse.setBalFeesToPay(course.getFees());
		}
		registeredCourse.setFeesPaid(courseRegisterRequest.getFeesPaid());
		registeredCourse.setFeesPaidDate(courseRegisterRequest.getFeesPaidDate());
		balance = registeredCourse.getBalFeesToPay() - courseRegisterRequest.getFeesPaid();
		registeredCourse.setBalFeesToPay(balance);
		
		registeredCourse = registeredCourseRepository.save(registeredCourse); 
		return registeredCourse;
	}	
		
}
