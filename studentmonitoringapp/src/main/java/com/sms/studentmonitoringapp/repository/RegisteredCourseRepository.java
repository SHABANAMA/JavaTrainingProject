package com.sms.studentmonitoringapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sms.studentmonitoringapp.entity.RegisteredCourse;

public interface RegisteredCourseRepository extends JpaRepository<RegisteredCourse, Long>{
	public List<RegisteredCourse> findByCourseId(Long courseId);
	public List<RegisteredCourse> findByStudentId(Long studentId);
	public List<RegisteredCourse> findByBalFeesToPay(Double balFeeToPay);
	public List<RegisteredCourse> findByBalFeesToPayGreaterThan(Double balFeeToPay);
}
