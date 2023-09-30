package com.tm.app.entity;

import java.sql.Date;
import java.sql.Timestamp;

import org.hibernate.annotations.UpdateTimestamp;

import com.tm.app.enums.PaymentBasis;
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
@Table(name = "t_emp_contract_payment")
public class EmployeeContractPayment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "contract_details_id")
	private ContractDetails contractDetails;
																							
	@Column(name = "payment_date")
	private Date paymentDate;

	@Column(name = "amount_paid")
	private Float amountPaid;

	@Column(name = "amount_balance")
	private Float amountBalance;
	
	@Column(name = "total_amount")
	private Float totalAmount;
	
	@Column(name = "notes")
	private String notes;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_mode")
	private PaymentBasis paymentBasis;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "payment_status")
	private PaymentStatus paymentStatus;

	@UpdateTimestamp
	@Column(name = "update_at")
	private Timestamp updatedAt;

	@Column(name = "updated_by")
	private String updatedBy;
}
