package com.tm.app.entity;

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
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name = "t_employee_pay")
public class EmployeePay {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;

	@Column(name = "basic_pay")
	private Float basicPay;
	
	@Column(name = "hra")
	private Float hra; // House Rent Allowance
	
	@Column(name="da")
	private Float da; // Dearness Allowance
	
	@Column(name="conveyance_allowance")
	private Float conveyanceAllowance;
	
	@Column(name="medical_allowance")
	private Float medicalAllowance;
	
	@Column(name="ita")
	private Float ita; // Interactive Tax Assistant
	
	@Column(name="special_allowance")
	private Float specialAllowance;
	
	@Column(name="bonus")
	private Float bonus;
	
	@Column(name = "epf")
	private Float epf;
	
	@Column(name = "esi")
	private Float esi;
	
	@Column(name = "deductions")
	private Float deductions;
	
	@Column(name = "earnings")
	private Float earnings;

	@Column(name = "net_pay")
	private Float netPay;

	@Column(name = "updated_by")
	private String updatedBy;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;
	
	@Transient
	private String departmentName;

}
