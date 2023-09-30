package com.tm.app.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import com.tm.app.enums.PaymentMode;
import com.tm.app.enums.PaymentStatus;

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
@Table(name = "t_payment_history")
public class PaymentHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "payment_id")
	private Payment payment;

	@Column(name = "amount")
	private Float paidAmount;

	@CreationTimestamp
	@Column(name = "payment_date_ime")
	private Timestamp paymentDateTime;

	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;

	@Column(name = "last_updated_by")
	private String updatedBy;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_mode")
	private PaymentMode paymentMode;
}
