package com.mortgage.mortgage.controller;

import com.mortgage.mortgage.entity.Loan;
import com.mortgage.mortgage.entity.User;
import com.mortgage.mortgage.service.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class LoanController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoanController.class);
    private LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/loan")
    public ResponseEntity<?> loan(@RequestHeader String token, @RequestParam Integer userId) {
        return new ResponseEntity<>(loanService.loanInfo(userId), HttpStatus.CREATED);

    }

}
