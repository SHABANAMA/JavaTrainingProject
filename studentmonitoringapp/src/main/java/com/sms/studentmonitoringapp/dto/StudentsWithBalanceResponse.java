package com.sms.studentmonitoringapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentsWithBalanceResponse {
	private Long studentId;
	private Double balFeesToPay;
}
