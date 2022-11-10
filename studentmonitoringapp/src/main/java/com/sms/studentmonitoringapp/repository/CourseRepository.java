package com.sms.studentmonitoringapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sms.studentmonitoringapp.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long>{
	public Course findByCourseName(String courseName);
	public void deleteByCourseName(String coursename);
}
