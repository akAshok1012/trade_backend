package com.tm.app.entity;

import java.sql.Date;
import java.sql.Timestamp;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.context.annotation.Lazy;

import com.tm.app.enums.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "t_payment", indexes = @Index(name = "payment_index", columnList = "sale_id,customer_id"))
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "sale_id")
	private Integer salesId;

	@Column(name = "sales_order_date")
	private Date salesOrderDate;

	@OneToOne
	@Lazy
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@Column(name = "total_order_amount")
	private Float totalOrderAmount;

	@Column(name = "total_paid_amount")
	private Float totalPaidAmount;

	@Column(name = "balance_amount")
	private Float balanceAmount;

	@Column(insertable = false)
	@UpdateTimestamp
	private Timestamp paymentDate;

	@Column(name = "payment_notes")
	private String paymentNotes;

	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "delivery_payable_amount", columnDefinition = "FLOAT default 0.0")
	private Float deliveryPayableAmount;

}