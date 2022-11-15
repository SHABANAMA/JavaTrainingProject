package com.sms.studentmonitoringapp.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.studentmonitoringapp.config.CustomProperties;
import com.sms.studentmonitoringapp.dto.AddCourseRequest;
import com.sms.studentmonitoringapp.dto.AddCourseResponse;
import com.sms.studentmonitoringapp.dto.StudentDetailsEntryRequest;
import com.sms.studentmonitoringapp.dto.StudentDetailsEntryResponse;
import com.sms.studentmonitoringapp.dto.StudentForACourseResponse;
import com.sms.studentmonitoringapp.dto.TotalAndBalanceFeeResponse;
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
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(user.getDob());
		user.setPassWord(user.getFirstName() + calendar.get(Calendar.DAY_OF_MONTH) + calendar.get(Calendar.MONTH)
				+ calendar.get(Calendar.YEAR));
		user.setUserName(user.getFirstName() + user.getLastName());
		user = userRepository.save(user);

		StudentAcademic studentAcademic = studentDetailsEntryRequest.getStudentAcademic();
		studentAcademic.setStudentId(user.getUserId());
		studentAcademic = studentAcademicRepository.save(studentAcademic);

		return new StudentDetailsEntryResponse(customProperties.getEntrysuccess(),
				user.getFirstName() + user.getLastName());
	}

	@Override
	@Transactional
	public StudentDetailsEntryResponse updateStudent(String userName,
			StudentDetailsEntryRequest studentDetailsEntryRequest) {
		
		User dbUser = Optional.ofNullable(userRepository.findByUserName(userName)).get();
		Long id = dbUser.getUserId();
		dbUser = studentDetailsEntryRequest.getUser();
		dbUser.setUserId(id);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dbUser.getDob());
		dbUser.setPassWord(dbUser.getFirstName() + calendar.get(Calendar.DAY_OF_MONTH) + calendar.get(Calendar.MONTH)
				+ calendar.get(Calendar.YEAR));
		dbUser.setUserName(dbUser.getFirstName() + dbUser.getLastName());
		dbUser = userRepository.save(dbUser);
		
		StudentAcademic dbStudentAcademic = studentAcademicRepository.findByStudentId(dbUser.getUserId());
		dbStudentAcademic = studentDetailsEntryRequest.getStudentAcademic();
		dbStudentAcademic.setStudentId(dbUser.getUserId());
		dbStudentAcademic = studentAcademicRepository.save(dbStudentAcademic);
		return new StudentDetailsEntryResponse(customProperties.getUpdatesuccess(),
				dbUser.getFirstName() + dbUser.getLastName());
	}
	
	@Override
	@Transactional
	public String deleteStudent(String userName) {
		User user = userRepository.findByUserName(userName);
		String s = " OF : "+user.getFirstName()+" "+user.getLastName();
		userRepository.deleteByUserName(userName);
		studentAcademicRepository.deleteById(user.getUserId());
		registeredCourseRepository.deleteByStudentId(user.getUserId());
		return customProperties.getDeletesuccess()+s;
	}

	@Override
	public List<StudentForACourseResponse> listAllStudents() {
		List<StudentForACourseResponse> studentDetails= new ArrayList<StudentForACourseResponse>();
		List<User> students = userRepository.findAll();
		for (User u : students) {
			StudentForACourseResponse studentForACourseResponse = new StudentForACourseResponse(
					u,studentAcademicRepository.findByStudentId(u.getUserId()));
			studentDetails.add(studentForACourseResponse);
		}
		return studentDetails;
	}
	
	public StudentForACourseResponse listStudentByUserName(String userName) {
		User user = userRepository.findByUserName(userName);
		StudentAcademic studentAcademic = studentAcademicRepository.findByStudentId(user.getUserId());
		return new StudentForACourseResponse(user,studentAcademic);
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
	@Transactional
	public String deleteCourse(String courseName) {
		Optional<Course> opt = Optional.ofNullable(courseRepository.findByCourseName(courseName));
		if (opt.isPresent()) {
			Course course = opt.get();
			courseRepository.deleteById(course.getCourseId());
			registeredCourseRepository.deleteAllByCourseId(course.getCourseId());
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
	public List<String> displayAllRegisteredStudents() {
		List<RegisteredCourse> al = registeredCourseRepository.findAll();
		Collections.sort(al, (a, b) -> a.getFeesPaidDate().compareTo(b.getFeesPaidDate()));
		List<String> list = new ArrayList<String>();
		for (RegisteredCourse rc : al) {
			User user = (userRepository.findById(rc.getStudentId())).get();
			Course course = (courseRepository.findById(rc.getCourseId())).get();
			list.add(user.getFirstName() + " " + user.getLastName() + " : " + course.getCourseName() + " : "
					+ rc.getFeesPaidDate());
		}
		return list;
	}

	@Override
	public List<String> displayStudentsWithNoBalance() {
		List<RegisteredCourse> al = registeredCourseRepository.findByBalFeesToPay(0.0);
		List<String> result = new ArrayList<String>();
		for (RegisteredCourse rc : al) {
			User user = (userRepository.findById(rc.getStudentId())).get();
			Course course = (courseRepository.findById(rc.getCourseId())).get();
			result.add(user.getFirstName() + " " + user.getLastName() + " : " + course.getCourseName());
		}
		return result;
	}

	@Override
	public List<String> displayStudentsWithBalance() {
		List<RegisteredCourse> al = registeredCourseRepository.findByBalFeesToPayGreaterThan(0.0);
		// List<RegisteredCourse> al =
		// registeredCourseRepository.findStudentsWithBalance();
		List<String> list = new ArrayList<String>();
		for (RegisteredCourse rc : al) {
			User user = (userRepository.findById(rc.getStudentId())).get();
			Course course = (courseRepository.findById(rc.getCourseId())).get();
			list.add(user.getFirstName() + " " + user.getLastName() + " : " + course.getCourseName() + " : "
					+ rc.getBalFeesToPay());
		}
		return list;
	}

	@Override
	public List<StudentForACourseResponse> displayStudentsForACourse(String courseName) {
		List<StudentForACourseResponse> dsdr = new ArrayList<StudentForACourseResponse>();
		Course course = courseRepository.findByCourseName(courseName);
		List<RegisteredCourse> al = registeredCourseRepository.findByCourseId(course.getCourseId());
		for (RegisteredCourse rc : al) {
			User user = (userRepository.findById(rc.getStudentId())).get();
			StudentAcademic studentAcademic = studentAcademicRepository.findByStudentId(rc.getStudentId());
			StudentForACourseResponse studentForACourseResponse = new StudentForACourseResponse(user, studentAcademic);
			dsdr.add(studentForACourseResponse);
		}
		return dsdr;
	}

	@Override
	public List<Course> displayCoursesForAStudent(String userName) {
		List<Course> courseList = new ArrayList<Course>();
		User user = userRepository.findByUserName(userName);
		List<RegisteredCourse> al = registeredCourseRepository.findByStudentId(user.getUserId());
		for (RegisteredCourse rc : al) {
			Course course = (courseRepository.findById(rc.getCourseId())).get();
			courseList.add(course);
		}
		return courseList;
	}

	@Override
	public TotalAndBalanceFeeResponse totalAndBalanceFee() {
		List<RegisteredCourse> allRegistration = registeredCourseRepository.findAll();
		double sum = 0, rem = 0;
		for (RegisteredCourse rc : allRegistration) {
			sum = sum + rc.getFeesPaid();
			rem = rem + rc.getBalFeesToPay();
		}
		return new TotalAndBalanceFeeResponse(sum, rem);
	}

}
