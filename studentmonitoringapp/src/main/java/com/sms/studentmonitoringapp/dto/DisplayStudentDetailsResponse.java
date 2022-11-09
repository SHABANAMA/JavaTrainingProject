package com.sms.studentmonitoringapp.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sms.studentmonitoringapp.entity.StudentAcademic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisplayStudentDetailsResponse {
	private String firstName;
	private String lastName;
	private String email;
	private Long phone;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date dob;
	private StudentAcademic studentAcademic;
}
