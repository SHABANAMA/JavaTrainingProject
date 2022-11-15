package com.sms.studentmonitoringapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sms.studentmonitoringapp.entity.RegisteredCourse;

public interface RegisteredCourseRepository extends JpaRepository<RegisteredCourse, Long>{
	public List<RegisteredCourse> findByCourseId(Long courseId);
	public List<RegisteredCourse> findByStudentId(Long studentId);
	public List<RegisteredCourse> findByBalFeesToPay(Double balFeeToPay);
	public List<RegisteredCourse> findByBalFeesToPayGreaterThan(Double balFeeToPay);
	@Query("select  registerId,studentId, courseId, feesPaid, feesPaidDate, sum(balFeesToPay) as balFeesToPay FROM RegisteredCourse where balFeesToPay>0.0 group by studentId")
	public List<RegisteredCourse> findStudentsWithBalance();
	public void deleteAllByCourseId(Long courseId);
	public void deleteByStudentId(Long studentId);
}
