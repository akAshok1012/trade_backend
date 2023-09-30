package com.tm.app.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.context.annotation.Lazy;

import com.tm.app.enums.OrderStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "t_order")
public class Order {

	@Id
	@Column(name = "order_id")
	private Integer orderId;

	@Enumerated(EnumType.STRING)
	@Column(name = "order_status")
	private OrderStatus orderStatus;

	@OneToOne
	@Lazy
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@OneToOne
	@Lazy
	@JoinColumn(name = "reject_reason_id")
	private RejectReason rejectReason;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@Column(name = "updated_by")
	private String updatedBy;
	
	@Column(name = "is_notification_enabled")
	private Boolean isNotificationEnabled;

}
