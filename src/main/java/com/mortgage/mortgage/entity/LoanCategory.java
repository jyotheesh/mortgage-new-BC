package com.mortgage.mortgage.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table
public class LoanCategory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer loan_type_id;
	private String typeOfLoan;
	private Integer minAge;
	private Integer maxAge;
	private Integer exposure;
}
