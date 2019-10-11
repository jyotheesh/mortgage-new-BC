package com.mortgage.mortgage.repository;

import com.mortgage.mortgage.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan,Integer> {
    Loan findAllByUserId(Integer userId);

}
