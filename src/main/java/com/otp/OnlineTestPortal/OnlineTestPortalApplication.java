package com.otp.OnlineTestPortal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OnlineTestPortalApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(OnlineTestPortalApplication.class, args);
	}

}

