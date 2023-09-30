package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.LeadNotificationDto;
import com.tm.app.dto.NotificationDto;
import com.tm.app.dto.OrderApprovedNotificationDto;
import com.tm.app.dto.OrderPlacedNotificationDto;
import com.tm.app.dto.PaymentNotificationDto;
import com.tm.app.dto.SparesNotificationDto;
import com.tm.app.entity.Notification;

public interface NotificationService {

	public Notification saveSMSLog(NotificationDto smsLogDto);

	public List<Notification> getSMSLogs();

	public Notification getSMSLogById(Long id);

	public Notification updateSMSLog(Long id, NotificationDto smsLogDto);

	public void deleteSMSLogById(Long id);

	public List<SparesNotificationDto> pendingSparesNotification();

	public List<PaymentNotificationDto> pendingPaymentNotification();

	public List<LeadNotificationDto> leadFollowUpNotification();

	public List<OrderPlacedNotificationDto> OrderNotification();

	public List<OrderApprovedNotificationDto> OrderApprovedNotification();

	public void markNotificationsAsCompleted(List<Long> id);

	public Page<Notification> getFailedNotificationList(DataFilter dataFilter);

	public List<Notification> getFailedNotification();
}
