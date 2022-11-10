package com.sms.studentmonitoringapp.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="registered_course_details")
public class RegisteredCourse {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long registerId;
	private Long studentId;
	private Long courseId;
	private double feesPaid;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date feesPaidDate;
	private double balFeesToPay;
}
