package com.otp.OnlineTestPortal.Repositary;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otp.OnlineTestPortal.model.TestInfo;

public interface TestInfoRepo extends JpaRepository<TestInfo, Long> 
{

}
