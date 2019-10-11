package com.mortgage.mortgage.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EligibilityCheckRequestDto {
	
	private String customerName;
	private String propertyDetails;
	private double propertyValue;
	private int age;
	private double grossIncome;
	private double eligibleAmount;
	private double otherEmi;
	private double loanAmount;
	private int month;
	private double interestRate;
	private double tenure;
	private int customerId;
}
