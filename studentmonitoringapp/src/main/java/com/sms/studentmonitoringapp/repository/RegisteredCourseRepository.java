package com.sms.studentmonitoringapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sms.studentmonitoringapp.entity.RegisteredCourse;

public interface RegisteredCourseRepository extends JpaRepository<RegisteredCourse, Long>{
	public List<RegisteredCourse> findByCourseId(Long courseId);
	public List<RegisteredCourse> findByStudentId(Long studentId);

}
