package com.mortgage.mortgage.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mortgage.mortgage.dto.EligibilityCheckRequestDto;
import com.mortgage.mortgage.dto.EligibilityCheckResponseDto;
import com.mortgage.mortgage.entity.LoanCategory;
import com.mortgage.mortgage.exception.AgeInvalidException;
import com.mortgage.mortgage.repository.CustomerRepository;
import com.mortgage.mortgage.repository.LoanCategoryRepository;

@Service
public class EligibilityCheckServiceImpl implements EligibilityCheckService{
	
	private static final Logger lOGGER = LoggerFactory.getLogger(EligibilityCheckServiceImpl.class);

	@Autowired
	LoanCategoryRepository loanCategoryRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Override
	public EligibilityCheckResponseDto checkEligibility(EligibilityCheckRequestDto eligibilityCheckRequestDto) {
		lOGGER.debug("checkEligibility method in service");
		Optional<LoanCategory> loanCategory = loanCategoryRepository.findLoanCategoryByAge(eligibilityCheckRequestDto.getAge());
		if(!loanCategory.isPresent()) {
			throw new AgeInvalidException("Please enter valid age");
		}
		double propertyValue = eligibilityCheckRequestDto.getPropertyValue();
		double grossIncome = eligibilityCheckRequestDto.getGrossIncome();
		double otherEmi = eligibilityCheckRequestDto.getOtherEmi();
		int exposure = loanCategory.get().getExposure();
		double eligbleLoan = (propertyValue*1.5) + ((grossIncome-otherEmi)*exposure);
		EligibilityCheckResponseDto response = new EligibilityCheckResponseDto();
		response.setLoanAmount(eligbleLoan);
		response.setMessage("Eligbility Checked successfully");
		return response;
	}

}
