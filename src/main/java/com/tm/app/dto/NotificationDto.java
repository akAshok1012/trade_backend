package com.tm.app.dto;

import java.sql.Timestamp;

import com.tm.app.entity.NotificationTemplate;
import com.tm.app.enums.NotificationStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class NotificationDto {
	
	private Long id;
	private String title;
	private String message;
	private Timestamp sendAt;
	@Enumerated(EnumType.STRING)
	private NotificationStatus notificationStatus;
	private Boolean isAllCustomers;
	private Boolean isSend;
	private Long phoneNumber;
	private NotificationTemplate template;
	private Timestamp updatedAt;
	private String updatedBy;
}
