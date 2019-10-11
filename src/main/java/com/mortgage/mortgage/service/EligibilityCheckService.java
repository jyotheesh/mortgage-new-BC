/**
 * @author User1
 * @name EligibilityCheckService.java
 * @date Oct 10, 2019
 */
package com.mortgage.mortgage.service;

import org.springframework.stereotype.Service;

import com.mortgage.mortgage.dto.EligibilityCheckRequestDto;
import com.mortgage.mortgage.dto.EligibilityCheckResponseDto;

/**
 * @author User1
 *
 */
@Service
public interface EligibilityCheckService {
	
	public EligibilityCheckResponseDto checkEligibility(EligibilityCheckRequestDto eligibilityCheckRequestDto);
}
