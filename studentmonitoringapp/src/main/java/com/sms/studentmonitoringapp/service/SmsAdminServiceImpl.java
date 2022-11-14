package com.sms.studentmonitoringapp.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.studentmonitoringapp.config.CustomProperties;
import com.sms.studentmonitoringapp.dto.AddCourseRequest;
import com.sms.studentmonitoringapp.dto.AddCourseResponse;
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
public class SmsAdminServiceImpl implements SmsAdminService {

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

	@Transactional
	@Override
	public StudentDetailsEntryResponse enterStudentDetails(StudentDetailsEntryRequest studentDetailsEntryRequest) {
		User user = studentDetailsEntryRequest.getUser();
		user = userRepository.save(user);

		StudentAcademic studentAcademic = studentDetailsEntryRequest.getStudentAcademic();
		studentAcademic.setStudentId(user.getUserId());
		studentAcademic = studentAcademicRepository.save(studentAcademic);

		return new StudentDetailsEntryResponse(customProperties.getEntrysuccess(),
				user.getFirstName() + user.getLastName());
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
		if (opt.isPresent()) {
			dbCourse = opt.get();
			dbCourse.setCourseName(course.getCourseName());
			dbCourse.setDescription(course.getDescription());
			dbCourse.setFees(course.getFees());
			dbCourse = courseRepository.save(dbCourse);
			return dbCourse;
		} else
			return null;
	}

	@Override
	public String deleteCourse(String courseName) {
		Optional<Course> opt = Optional.ofNullable(courseRepository.findByCourseName(courseName));
		if (opt.isPresent()) {
			Course course = opt.get();
			courseRepository.deleteById(course.getCourseId());
			return customProperties.getDeletesuccess() + courseName;
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
		if (opt.isPresent()) {
			Course course = opt.get();
			return course;
		}
		return null;
	}

	@Override
	public Map<String, Date> displayAllRegisteredStudents() {
		List<RegisteredCourse> al = registeredCourseRepository.findAll();
		Collections.sort(al, (a, b) -> a.getFeesPaidDate().compareTo(b.getFeesPaidDate()));
		Map<String, Date> lhm = new LinkedHashMap<String, Date>();
		for (RegisteredCourse rc : al) {
			Optional<User> opt = userRepository.findById(rc.getStudentId());
			if (opt.isPresent()) {
				User user = opt.get();
				lhm.put(user.getFirstName() + " " + user.getLastName(), rc.getFeesPaidDate());
			}
		}
		return lhm;
	}

	@Override
	public List<String> displayStudentsWithNoBalance() {
		List<RegisteredCourse> al = registeredCourseRepository.findAll();
		List<String> result = new ArrayList<String>();
		for (RegisteredCourse rc : al) {
			if (rc.getBalFeesToPay() == 0) {
				Optional<User> opt = userRepository.findById(rc.getStudentId());
				if (opt.isPresent()) {
					User user = opt.get();
					result.add(user.getFirstName() + " " + user.getLastName());
				}
			}
		}
		return result;
	}

	@Override
	public Map<String, Double> displayStudentsWithBalance() {
		List<RegisteredCourse> al = registeredCourseRepository.findAll();
		Map<String, Double> lhm = new LinkedHashMap<String, Double>();
		for (RegisteredCourse rc : al) {
			if (rc.getBalFeesToPay() > 0) {
				Optional<User> opt = userRepository.findById(rc.getStudentId());
				if (opt.isPresent()) {
					User user = opt.get();
					lhm.put(user.getFirstName() + " " + user.getLastName(), rc.getBalFeesToPay());
				}
			}
		}
		return lhm;
	}
}
