package com.otp.OnlineTestPortal.Repositary;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otp.OnlineTestPortal.model.TestResult;

public interface TestResultRepo extends JpaRepository<TestResult, Long>{

	TestResult findResultByEmail(String email);

}
