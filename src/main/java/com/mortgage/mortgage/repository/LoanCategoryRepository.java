package com.mortgage.mortgage.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mortgage.mortgage.entity.LoanCategory;

@Repository
public interface LoanCategoryRepository extends JpaRepository<LoanCategory, Integer> {
	
	@Query("select l from LoanCategory l where :age BETWEEN l.minAge AND l.maxAge")
	Optional<LoanCategory> findLoanCategoryByAge(@Param("age") Integer age);

}
