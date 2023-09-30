package com.tm.app.entity;

import java.sql.Date;
import java.sql.Timestamp;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "t_credit_payment_track")
public class CreditPaymentTrack {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "payment_id")
	private Long paymentId;

	@OneToOne
	@JoinColumn(name = "customer_id")
	private Customer customerId;

	@Column(name = "sale_id")
	private Integer salesId;

	@Column(name = "pending_amount")
	private Float pendingAmount;

	@Column(name = "paid_amount")
	private Float paidAmount;

	@Column(name = "total_order_amount")
	private Float totalOrderAmount;

	@Column(name = "order_date_time")
	private Timestamp orderDateTime;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@Column(name = "notification_send_at")
	private Timestamp notificationSendAt;

	@Column(name = "description")
	private String description;
	
	@Column(name ="dueDate" )
	private Date dueDate;
	
	@Column(name ="updated_by" )
	private String updatedBy;

}
