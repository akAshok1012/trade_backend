package com.tm.app.service;

import java.util.List;

import com.tm.app.dto.NotificationTemplateDto;
import com.tm.app.entity.NotificationTemplate;

public interface NotificationTemplateService {

	NotificationTemplate saveNotificationTemplate(NotificationTemplateDto notificationTemplateDto);

	List<NotificationTemplate> getNotificationTemplate();

}
