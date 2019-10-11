package com.mortgage.mortgage.service;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mortgage.mortgage.dto.EligibilityCheckRequestDto;
import com.mortgage.mortgage.dto.LoanResponseDto;
import com.mortgage.mortgage.entity.Customer;
import com.mortgage.mortgage.entity.Loan;
import com.mortgage.mortgage.exception.InvalidAmountException;
import com.mortgage.mortgage.exception.InvalidCustomerException;
import com.mortgage.mortgage.repository.CustomerRepository;
import com.mortgage.mortgage.repository.LoanRepository;

@Service
public abstract class LoanServiceImpl implements LoanService {
	
	@Autowired
	LoanRepository loanRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	private static final Logger lOGGER = LoggerFactory.getLogger(LoanServiceImpl.class);
	
	@Override
	public LoanResponseDto applyLoan(EligibilityCheckRequestDto request) {
		lOGGER.debug("Loan service CLass apply loan method");
		Optional<Customer> customer = customerRepository.findById(request.getCustomerId());
		if(!customer.isPresent()) {
			throw new InvalidCustomerException("Customer not found");
		}
		if(request.getLoanAmount()>request.getEligibleAmount()) {
			throw new InvalidAmountException("you are exceeding eligible loan amount");
		}
		Loan loan = new Loan();
		LoanResponseDto responseDto = new LoanResponseDto();
		loan.setAccountNumber(accountNumber());
		loan.setRateOfInterest(request.getInterestRate());
		loan.setMonthlyEmi(monthlyEmi(request.getLoanAmount(),request.getInterestRate(),request.getTenure()));
		loan.setInterestType("fixed");
		loan.setTenure(request.getTenure());
		loan.setPrincipalAmount(request.getLoanAmount());
		loan.setUserId(request.getCustomerId());;
		loanRepository.save(loan);
		responseDto.setLoanAccountNumber(loan.getAccountNumber());
		responseDto.setMessage("Loan applied successfully");
		return responseDto;
	}
	
	public double monthlyEmi(double principalAmount,double interestRate,double tenure) {
		double emi; 
		interestRate = interestRate / (12 * 100); // one month interest 
		tenure = tenure * 12;  
        emi = (principalAmount * interestRate * (double)Math.pow(1 + interestRate, tenure))  
                / (double)(Math.pow(1 + interestRate, tenure) - 1); 
        return Math.round(emi*100.0)/100.0; 
	}
	
	private long accountNumber() {
		 return ThreadLocalRandom.current().nextLong(100000000000L, 999999999999L);
	}
	

}
