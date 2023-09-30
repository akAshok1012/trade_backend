package com.tm.app.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.tm.app.enums.NotificationStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "t_notification")
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	@Column(name = "message")
	private String message;

	@Column(name = "phone_number")
	private Long phoneNumber;

	@CreationTimestamp
	@Column(name = "send_at")
	private Timestamp sendAt;
	
	@Column(name = "is_send")	
	private Boolean isSend;

	@Enumerated(EnumType.STRING)
	@Column(name = "notification_status")
	private NotificationStatus notificationStatus;

	@Column(name = "is_all_customers")
	private Boolean isAllCustomers;

	@OneToOne
	@JoinColumn(name = "template_id")
	private NotificationTemplate template;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@Column(name = "updated_by")
	private String updatedBy;

}
