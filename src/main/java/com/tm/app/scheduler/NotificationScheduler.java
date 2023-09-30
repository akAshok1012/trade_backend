package com.tm.app.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tm.app.service.impl.NotificationSchedulerServiceImpl;

@Component
public class NotificationScheduler {

	 @Autowired
	    private NotificationSchedulerServiceImpl notificationSchedulerServiceImpl;

	 @Scheduled(cron= "0/10 * *  * * ? ") 
	 public void processNotification() {
		 notificationSchedulerServiceImpl.processNotification();
	    }
	 
}
