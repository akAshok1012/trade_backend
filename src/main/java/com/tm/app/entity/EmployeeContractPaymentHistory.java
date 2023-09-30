package com.tm.app.entity;

import java.sql.Date;
import java.sql.Timestamp;

import org.hibernate.annotations.UpdateTimestamp;

import com.tm.app.enums.PaymentBasis;

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
@Table(name = "t_emp_contract_payment_history")
public class EmployeeContractPaymentHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "employee_contract_payment_id")
	private EmployeeContractPayment employeeContractPayment;

	@Column(name = "amount_paid")
	private Float amountPaid;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "payment_mode")
	private PaymentBasis paymentBasis;
	
	@Column(name = "payment_date")
	private Date paymentDate;

	@Column(name = "notes")
	private String notes;

	@UpdateTimestamp
	@Column(name = "update_at")
	private Timestamp updatedAt;

	@Column(name = "updated_by")
	private String updatedBy;
	
}
