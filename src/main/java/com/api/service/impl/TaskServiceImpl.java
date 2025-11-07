package com.api.service.impl;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Profile(value =  "prod" )
public class TaskServiceImpl {
//	@Scheduled(fixedRate = 5000)
	public void cleanTempFile() {
		System.out.println("Clean Temp File: "+ LocalDateTime.now().toString());
	}
	
				// seconds Minute Hours DayOfMonth Month DayOfWeek
//	@Scheduled(cron = "0 0 0 ? * SUN")
//	public void cleanlog() {
//		
//	}
//	
}
