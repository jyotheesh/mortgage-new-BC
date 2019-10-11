package com.mortgage.mortgage.impl;

import com.mortgage.mortgage.controller.UserController;
import com.mortgage.mortgage.dto.EligibilityCheckRequestDto;
import com.mortgage.mortgage.dto.LoanResponseDto;
import com.mortgage.mortgage.entity.Customer;
import com.mortgage.mortgage.entity.Loan;
import com.mortgage.mortgage.entity.User;
import com.mortgage.mortgage.exception.InvalidAmountException;
import com.mortgage.mortgage.exception.InvalidCustomerException;
import com.mortgage.mortgage.repository.CustomerRepository;
import com.mortgage.mortgage.repository.LoanRepository;
import com.mortgage.mortgage.repository.UserRepository;
import com.mortgage.mortgage.service.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class LoanServiceImpl implements LoanService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoanServiceImpl.class);
    private LoanRepository loanRepository;
    private Double principleAmount = null;


    private Integer userId = null;
    private Loan loan = null;

    private UserRepository userRepository;
    
	@Autowired
	CustomerRepository customerRepository;


    public LoanServiceImpl(LoanRepository loanRepository, UserRepository userRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Map<String, List> loanInfo(Integer userId) {
        this.userId = userId;

        Map<String, List> hm = new HashMap<>();
        List<Double> list = new ArrayList<>();

        loan = loanRepository.findAllByUserId(userId);
        principleAmount = loan.getPrincipalAmount();

        Double tenure = loan.getTenure();
        Double rateOfInterest = loan.getRateOfInterest();
        rateOfInterest = rateOfInterest / (12 * 100);
        tenure = tenure * 12;
        double emi = (principleAmount * rateOfInterest * Math.pow(1 + rateOfInterest, tenure)) / (Math.pow(1 + rateOfInterest, tenure) - 1);

        //  Double emi = loan.getMonthlyEmi();


        //   Double loanAmount = loan.getLoanAmount();
        list.add(emi);
        list.add(principleAmount);
        list.add(tenure);
        Double months = 12d;
        list.add(months);

        list.add(rateOfInterest);
        // list.add(loanAmount);

        hm.put("LoanDetails", list);
        List<Double> row1 = new ArrayList<>();
        Double interest = (principleAmount * rateOfInterest) / months;
        principleAmount = emi - interest;
        row1.add(interest);
        row1.add(principleAmount);

        hm.put("Schedule Details Row 1", row1);

        return hm;

    }

    @Scheduled(fixedRate = 20000)
    public void debit() {

        User user = userRepository.findAllByUserId(userId);
        Optional<User> user1 = Optional.ofNullable(user);
        if (user1.isPresent()) {
            user.setAmount(user.getAmount() - principleAmount);
            userRepository.saveAndFlush(user);
            LOGGER.info("scheduler executed " + principleAmount + " has been deducted from savings account ");
            LOGGER.info("Remaining balance is  " + user.getAmount());
        }
    }
    
    @Override
	public LoanResponseDto applyLoan(EligibilityCheckRequestDto request) {
		LOGGER.debug("Loan service CLass apply loan method");
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
