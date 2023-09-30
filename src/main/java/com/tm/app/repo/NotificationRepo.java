package com.tm.app.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.Notification;

import jakarta.transaction.Transactional;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, Long> {

	Notification getSMSLogById(Long id);

	@Query(value = "Select * from t_notification tn where tn.notification_status = 'FAILED'", nativeQuery = true)
	List<Notification> getUnSendNotification(String NotificationStatus);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM t_notification WHERE notification_status <> 'FAILED'", nativeQuery = true)
	void deleteSendNotification();

	@Query("SELECT t FROM Notification t WHERE t.notificationStatus='FAILED' and t.id in :id")
	List<Notification> findAllByIdAndNotificationStatus(List<Long> id);

	@Query("Select tn from Notification tn where tn.notificationStatus = 'FAILED'")
	Page<Notification> getFailedNotificationList(PageRequest of);

	@Query("Select tn from Notification tn where tn.notificationStatus = 'FAILED'")
	List<Notification> getFailedNotification();

}
