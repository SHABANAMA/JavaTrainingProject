package com.sms.studentmonitoringapp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="course_details")
public class Course {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long courseId;
	private String courseName;
	private String description;
	private double fees;
}
