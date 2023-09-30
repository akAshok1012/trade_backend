package com.tm.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tm.app.entity.Notification;
import com.tm.app.repo.NotificationRepo;

@Component
public class NotificationSchedulerServiceImpl {

	@Autowired
	private NotificationRepo notificationRepo;
	 
	 public void processNotification() {
	        List<Notification> notifications = notificationRepo.findAll();

	        for (Notification notification : notifications) {
	            sendNotification(notification.getPhoneNumber(), notification.getMessage());
	            notificationRepo.delete(notification);
	        }
	    }

	private void sendNotification(Long phoneNumber, String message) {
		 System.out.println("Sending SMS to: " + phoneNumber + " Message: " + message);
	}
}
