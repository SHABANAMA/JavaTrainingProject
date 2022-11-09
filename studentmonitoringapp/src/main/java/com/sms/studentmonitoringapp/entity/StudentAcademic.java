package com.sms.studentmonitoringapp.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="student_academic_details")
public class StudentAcademic {
	@Id
	private Long studentId;
	private String qualification;
	private String college;
	private String university;
	private int passoutYear;
}
