package com.otp.OnlineTestPortal.API;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.otp.OnlineTestPortal.Repositary.TestInfoRepo;
import com.otp.OnlineTestPortal.model.TestInfo;

@Component
public class TestScheduler {

@Autowired
private TestInfoRepo testrepo;

@Scheduled(fixedRate = 30000)
public void ManageScheduleTest()
{
	LocalDateTime now=LocalDateTime.now();
	List<TestInfo> tests = testrepo.findAll();
	
	for(TestInfo test:tests)
	{ 
	if(!test.isActive() && test.getStarttime().isBefore(now))
	{
		test.setActive(true);
		testrepo.save(test);
	}
	if(test.isActive() && test.endtime().isBefore(now))
	{
		test.setActive(false);
		testrepo.save(test);
	}
		
	}
}
	
}
