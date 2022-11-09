package com.sms.studentmonitoringapp.service;

import java.util.ArrayList;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sms.studentmonitoringapp.dto.StudentDetailsEntryRequest;
import com.sms.studentmonitoringapp.dto.StudentDetailsEntryResponse;
import com.sms.studentmonitoringapp.entity.StudentAcademic;
import com.sms.studentmonitoringapp.entity.User;
import com.sms.studentmonitoringapp.repository.StudentAcademicRepository;
import com.sms.studentmonitoringapp.repository.UserRepository;

@Service
public class StudentMonitoringServiceImpl implements StudentMonitoringService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private StudentAcademicRepository studentAcademicRepository;
	
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
		
		return new StudentDetailsEntryResponse("SUCCESFULLY ENTERED DETAILS OF",user.getFirstName()+user.getLastName());
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
}
