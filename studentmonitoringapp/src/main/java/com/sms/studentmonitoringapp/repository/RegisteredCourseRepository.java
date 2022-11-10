package com.sms.studentmonitoringapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sms.studentmonitoringapp.entity.RegisteredCourse;

public interface RegisteredCourseRepository extends JpaRepository<RegisteredCourse, Long>{

}
