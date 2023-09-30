package com.tm.app.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.tm.app.repo.NotificationRepo;

public class NotificationJob implements Job{

	@Autowired
	private NotificationRepo notificationRepo;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		notificationRepo.getUnSendNotification(null);
		notificationRepo.deleteSendNotification();
	}
}