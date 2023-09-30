package com.tm.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.LeadNotificationDto;
import com.tm.app.dto.NotificationDto;
import com.tm.app.dto.OrderApprovedNotificationDto;
import com.tm.app.dto.OrderPlacedNotificationDto;
import com.tm.app.dto.PaymentNotificationDto;
import com.tm.app.dto.SparesNotificationDto;
import com.tm.app.entity.Notification;
import com.tm.app.security.annotations.IsSuperAdminOrAdminOrCustomer;
import com.tm.app.security.annotations.IsSuperAdminOrAdminOrEmployee;
import com.tm.app.service.NotificationService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.APIResponseConstants;
import com.tm.app.utils.Response;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NotificationController {

	@Autowired
	private NotificationService notificationService;

	@PostMapping("/sms-log")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> saveSMSLog(@RequestBody NotificationDto smsLogDto) {
		try {
			Notification smsLog = notificationService.saveSMSLog(smsLogDto);
			return Response.getSuccessResponse(smsLog, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/sms-logs")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> getSMSLogs() {
		try {
			List<Notification> smsLogs = notificationService.getSMSLogs();
			return Response.getSuccessResponse(smsLogs, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/sms-log/{id}")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> getSMSLogById(@PathVariable("id") Long id) {
		try {
			Notification smsLog = notificationService.getSMSLogById(id);
			return Response.getSuccessResponse(smsLog, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/sms-log/{id}")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> updateSMSLog(@PathVariable Long id, @RequestBody NotificationDto smsLogDto) {
		try {
			Notification smsLog = notificationService.updateSMSLog(id, smsLogDto);
			return Response.getSuccessResponse(smsLog, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/sms-log/{id}")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> deleteSMSLogById(@PathVariable("id") Long id) {
		try {
			notificationService.deleteSMSLogById(id);
			return Response.getSuccessResponse(null, "Success", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/pending-spares-notification")
	public APIResponse<?> pendingSparesNotification() {
		try {
			List<SparesNotificationDto> notificationTemplate = notificationService.pendingSparesNotification();
			return Response.getSuccessResponse(notificationTemplate, "Success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/pending-payment-notification")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> pendingPaymentNotification() {
		try {
			List<PaymentNotificationDto> notificationTemplate = notificationService.pendingPaymentNotification();
			return Response.getSuccessResponse(notificationTemplate, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/lead-notification")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> leadFollowUpNotification() {
		try {
			List<LeadNotificationDto> notificationTemplate = notificationService.leadFollowUpNotification();
			return Response.getSuccessResponse(notificationTemplate, "Success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/order-placed-notification")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> OrderNotification() {
		try {
			List<OrderPlacedNotificationDto> orderTemplate = notificationService.OrderNotification();
			return Response.getSuccessResponse(orderTemplate, "Success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/order-approved-notification")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> OrderApprovedNotification() {
		try {
			List<OrderApprovedNotificationDto> orderApprovedTemplate = notificationService.OrderApprovedNotification();
			return Response.getSuccessResponse(orderApprovedTemplate, "Success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/sms-api")
	public APIResponse<?> markNotificationsAsCompleted(@RequestBody List<Long> id) {
		try {
			notificationService.markNotificationsAsCompleted(id);
			return Response.getSuccessResponse(null, "Triggered Successfully", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/notification-list")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getFailedNotificationList(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<Notification> notification = notificationService.getFailedNotificationList(dataFilter);
			return Response.getSuccessResponse(notification, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/failed-notification")
	@IsSuperAdminOrAdminOrEmployee
	public APIResponse<?> getFailedNotification() {
		try {
			List<Notification> notification = notificationService.getFailedNotification();
			return Response.getSuccessResponse(notification, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}