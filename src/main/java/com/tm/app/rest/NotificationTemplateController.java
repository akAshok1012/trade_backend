package com.tm.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tm.app.dto.NotificationTemplateDto;
import com.tm.app.entity.NotificationTemplate;
import com.tm.app.service.NotificationTemplateService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.Response;

@RestController
@CrossOrigin
public class NotificationTemplateController {

	@Autowired
	private NotificationTemplateService notificationTemplateService;
	
	@PostMapping("/notification-template")
	public APIResponse<?> saveNotificationTemplate(@RequestBody NotificationTemplateDto notificationTemplateDto) {
		try {
			NotificationTemplate notificationTemplate = notificationTemplateService.saveNotificationTemplate(notificationTemplateDto);
			return Response.getSuccessResponse(notificationTemplate, "Payment Saved Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/notification-template")
	public APIResponse<?> getNotificationTemplate() {
		try {
			List<NotificationTemplate> notificationTemplate = notificationTemplateService.getNotificationTemplate();
			return Response.getSuccessResponse(notificationTemplate, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
