package com.mortgage.mortgage.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mortgage.mortgage.dto.EligibilityCheckRequestDto;
import com.mortgage.mortgage.dto.LoanResponseDto;

@Service
public interface LoanService {
    public Map<String, List> loanInfo(Integer userId);
    
    public LoanResponseDto applyLoan(EligibilityCheckRequestDto request);
}
