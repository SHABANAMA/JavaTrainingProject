package com.sms.studentmonitoringapp.dto;

import com.sms.studentmonitoringapp.entity.StudentAcademic;
import com.sms.studentmonitoringapp.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetailsEntryRequest {
	private User user;
	private StudentAcademic studentAcademic;
}
