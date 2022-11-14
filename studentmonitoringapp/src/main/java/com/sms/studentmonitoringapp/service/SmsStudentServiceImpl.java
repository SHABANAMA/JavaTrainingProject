package com.sms.studentmonitoringapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.studentmonitoringapp.dto.CourseRegisterRequest;
import com.sms.studentmonitoringapp.entity.Course;
import com.sms.studentmonitoringapp.entity.RegisteredCourse;
import com.sms.studentmonitoringapp.entity.StudentAcademic;
import com.sms.studentmonitoringapp.entity.User;
import com.sms.studentmonitoringapp.repository.CourseRepository;
import com.sms.studentmonitoringapp.repository.RegisteredCourseRepository;
import com.sms.studentmonitoringapp.repository.StudentAcademicRepository;
import com.sms.studentmonitoringapp.repository.UserRepository;
import com.sms.studentmonitoringapp.util.RegistrationUtil;

@Service
public class SmsStudentServiceImpl implements SmsStudentService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StudentAcademicRepository studentAcademicRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private RegisteredCourseRepository registeredCourseRepository;

	@Override
	public User getStudentUserByUserName(String username) {
		Optional<User> opt = Optional.ofNullable(userRepository.findByUserName(username));
		if (opt.isPresent()) {
			return opt.get();
		} else
			return null;
	}

	@Override
	public StudentAcademic getStudentAcademicById(Long studentId) {
		Optional<StudentAcademic> opt = studentAcademicRepository.findById(studentId);
		if (opt.isPresent()) {
			return opt.get();
		} else
			return null;
	}

	@Override
	public User updateStudentUser(String username, User user) {
		Optional<User> opt = Optional.ofNullable(userRepository.findByUserName(username));
		User dbUser = null;
		if (opt.isPresent()) {
			dbUser = opt.get();
			dbUser.setEmail(user.getEmail());
			dbUser.setPhone(user.getPhone());
			userRepository.save(dbUser);
			return dbUser;
		} else
			return null;
	}

	@Override
	public StudentAcademic updateStudentAcademic(Long id, StudentAcademic studentAcademic) {
		Optional<StudentAcademic> opt = studentAcademicRepository.findById(id);
		StudentAcademic dbstudentAcademic = null;
		if (opt.isPresent()) {
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
	public RegisteredCourse registerCourse(String userName, CourseRegisterRequest courseRegisterRequest) {
		
		RegisteredCourse registeredCourse = new RegisteredCourse();
		Optional<User> opt = Optional.ofNullable(userRepository.findByUserName(userName));
		User user = null;
		if (opt.isPresent()) {
			user = opt.get();
			RegistrationUtil.validateAge(user.getDob());
			registeredCourse.setStudentId(user.getUserId());
		}
	
		Optional<Course> opt1 = Optional
				.ofNullable(courseRepository.findByCourseName(courseRegisterRequest.getCourseName()));
		if (opt1.isPresent()) {
			Course course = opt1.get();
			registeredCourse.setCourseId(course.getCourseId());
		}
		
		registeredCourse.setFeesPaid(courseRegisterRequest.getFeesPaid());
		registeredCourse.setFeesPaidDate(courseRegisterRequest.getFeesPaidDate());
		
		registeredCourse = registeredCourseRepository.save(registeredCourse);
		return registeredCourse;
	}
	

}
