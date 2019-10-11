package com.mortgage.mortgage.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString

public class LoanDto {


	private Integer loanId;
    private Long accountNumber;
    private Double loanAmount;
    private Integer userId;
    private Double principalAmount;
    private Double rateOfInterest;
    private Double monthlyEmi;
    private Double tenure;
    private String interestType;
}
