package com.sms.studentmonitoringapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalAndBalanceFeeResponse {
	private double totalFeeCollectedFromAllStudents;
	private double balFeeToPayByAllStudents;

}
