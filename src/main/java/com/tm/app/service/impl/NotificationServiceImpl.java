package com.tm.app.service.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.LeadNotificationDto;
import com.tm.app.dto.NotificationDto;
import com.tm.app.dto.OrderApprovedNotificationDto;
import com.tm.app.dto.OrderPlacedNotificationDto;
import com.tm.app.dto.PaymentNotificationDto;
import com.tm.app.dto.SparesNotificationDto;
import com.tm.app.entity.CreditPaymentTrack;
import com.tm.app.entity.Notification;
import com.tm.app.enums.NotificationStatus;
import com.tm.app.repo.LeadGenerationRepo;
import com.tm.app.repo.MachinerySparesRepo;
import com.tm.app.repo.NotificationRepo;
import com.tm.app.repo.OrderRepo;
import com.tm.app.repo.PaymentRepo;
import com.tm.app.service.NotificationService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class NotificationServiceImpl implements NotificationService {

	private static final String DAYS = "%s Days";

	@Autowired
	private NotificationRepo notificationRepo;

	@Autowired
	private MachinerySparesRepo machinerySparesRepo;

	@Autowired
	private PaymentRepo paymentRepo;

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private LeadGenerationRepo leadGenerationRepo;

	@Override
	@Transactional
	public Notification saveSMSLog(NotificationDto notificationDto) {
		Notification smsLog = new Notification();
		try {
			BeanUtils.copyProperties(notificationDto, smsLog);
			smsLog = notificationRepo.save(smsLog);
		} catch (Exception e) {
			log.error("[NOTIFICATION] adding notification failed", e);
			throw new RuntimeException("Adding notification failed");
		}
		return smsLog;
	}

	@Override
	public List<Notification> getSMSLogs() {
		return notificationRepo.findAll();
	}

	@Override
	public Notification getSMSLogById(Long id) {
		return notificationRepo.getSMSLogById(id);
	}

	@Override
	@Transactional
	public Notification updateSMSLog(Long id, NotificationDto notificationDto) {
		Notification notification = notificationRepo.findById(id).orElseThrow();
		try {
			notification.setNotificationStatus(notificationDto.getNotificationStatus());
			notification.setIsAllCustomers(notificationDto.getIsAllCustomers());
			notification.setTitle(notificationDto.getTitle());
			notification.setSendAt(notificationDto.getSendAt());
			notification.setMessage(notificationDto.getMessage());
			notification.setPhoneNumber(notificationDto.getPhoneNumber());
			notification.setTemplate(notificationDto.getTemplate());
			notification = notificationRepo.save(notification);
		} catch (Exception e) {
			log.error("[NOTIFICATION] updating notification failed", e);
			throw new RuntimeException("Updating notification failed");
		}
		return notification;
	}

	@Override
	@Transactional
	public void deleteSMSLogById(Long id) {
		try {
			notificationRepo.deleteById(id);
		} catch (Exception e) {
			log.error("[NOTIFICATION] deleting notification failed", e);
			throw new RuntimeException("Deleting notification failed");
		}
	}

	@Override
	public List<SparesNotificationDto> pendingSparesNotification() {
		List<Object[]> sparesNotification = machinerySparesRepo.getSparesNotification();
		List<SparesNotificationDto> notificationList = new ArrayList<>();
		sparesNotification.stream().forEach(spare -> mapToSparesNotificationDto(notificationList, spare));
		return notificationList;
	}

	/**
	 * 
	 * @param notificationList
	 * @param spare
	 */
	private void mapToSparesNotificationDto(List<SparesNotificationDto> notificationList, Object[] spare) {
		SparesNotificationDto notificationDto = new SparesNotificationDto();
		Date sqlDate = (Date) spare[2];
		notificationDto.setMachinerySpareId((Long) spare[0]);
		notificationDto.setSpareItem((String) spare[1]);
		notificationDto.setDays(String.format(DAYS, Period.between(LocalDate.now(), sqlDate.toLocalDate()).getDays()));
		notificationList.add(notificationDto);
	}

	@Override
	public List<PaymentNotificationDto> pendingPaymentNotification() {
		List<Object[]> paymentNotification = paymentRepo.getPaymentNotification();
		List<PaymentNotificationDto> notificationList = new ArrayList<>();
		paymentNotification.stream().forEach(payment -> mapToPaymentNotificationDto(notificationList, payment));
		return notificationList;
	}

	/**
	 * 
	 * @param notificationList
	 * @param payment
	 */

	private void mapToPaymentNotificationDto(List<PaymentNotificationDto> notificationList, Object[] payment) {
		PaymentNotificationDto paymentNotificationDto = new PaymentNotificationDto();
		Date sqlDate = (Date) payment[2];
		paymentNotificationDto.setSalesId((Integer) payment[0]);
		paymentNotificationDto.setName((String) payment[1]);
		paymentNotificationDto
				.setDays(String.format(DAYS, Period.between(LocalDate.now(), sqlDate.toLocalDate()).getDays()));
		paymentNotificationDto.setCreditPaymentTrack((CreditPaymentTrack) payment[3]);
		notificationList.add(paymentNotificationDto);
	}

	@Override
	public List<LeadNotificationDto> leadFollowUpNotification() {
		List<Object[]> leadNotification = leadGenerationRepo.getLeadNotification();
		List<LeadNotificationDto> notificationList = new ArrayList<>();
		leadNotification.stream().forEach(lead -> mapToLeadNotificationDto(notificationList, lead));
		return notificationList;
	}

	/**
	 * 
	 * @param notificationList
	 * @param lead
	 */
	private void mapToLeadNotificationDto(List<LeadNotificationDto> notificationList, Object[] lead) {
		LeadNotificationDto leadNotificationDto = new LeadNotificationDto();
		Date sqlDate = (Date) lead[3];
		leadNotificationDto.setId((Long) lead[0]);
		leadNotificationDto.setFollowUps((Integer) lead[1]);
		leadNotificationDto.setName((String) lead[2]);
		leadNotificationDto.setDays(String.format(DAYS, Period.between(LocalDate.now(), sqlDate.toLocalDate()).getDays()));
		notificationList.add(leadNotificationDto);
	}

	@Override
	public List<OrderPlacedNotificationDto> OrderNotification() {
		return orderRepo.getOrderNotification();
	}

	@Override
	public List<OrderApprovedNotificationDto> OrderApprovedNotification() {
		return orderRepo.getOrderApprovedNotification();
	}

	@Override
	public void markNotificationsAsCompleted(List<Long> id) {
		List<Notification> failedNotifications = notificationRepo.findAllByIdAndNotificationStatus(id);
		for (Notification notification : failedNotifications) {
			notification.setNotificationStatus(NotificationStatus.COMPLETED);
		}
		notificationRepo.saveAll(failedNotifications);
	}

	@Override
	public Page<Notification> getFailedNotificationList(DataFilter dataFilter) {
		return notificationRepo.getFailedNotificationList(PageRequest.of(dataFilter.getPage(),
				dataFilter.getSize(), Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}

	@Override
	public List<Notification> getFailedNotification() {
		return notificationRepo.getFailedNotification();
	}
}