package com.tm.app.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tm.app.dto.NotificationTemplateDto;
import com.tm.app.entity.NotificationTemplate;
import com.tm.app.repo.NotificationTemplateRepo;
import com.tm.app.service.NotificationTemplateService;

@Service
public class NotificationTemplateServiceImpl implements NotificationTemplateService {

	@Autowired
	private NotificationTemplateRepo notificationTemplateRepo;
	
	@Override
	public NotificationTemplate saveNotificationTemplate(NotificationTemplateDto notificationTemplateDto) {
		NotificationTemplate notificationTemplate = new NotificationTemplate();
			BeanUtils.copyProperties(notificationTemplateDto, notificationTemplate);
			notificationTemplate= notificationTemplateRepo.save(notificationTemplate);
			return notificationTemplate;
	}

	@Override
	public List<NotificationTemplate> getNotificationTemplate() {
		return notificationTemplateRepo.findAll();
	}


}
