package com.tm.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tm.app.entity.NotificationTemplate;

public interface NotificationTemplateRepo extends JpaRepository<NotificationTemplate, Long>{

}
