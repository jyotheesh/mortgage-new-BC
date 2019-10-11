package com.mortgage.mortgage.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table(name = "loan")
public class Loan {

    @Id
    @GeneratedValue()
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
