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
@Table(name = "t_employee_monthly_pay")
public class EmployeeMonthlyPay {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@OneToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;

	@Column(name = "basic_pay")
	private Float basicPay;

	@Column(name = "hra")
	private Float hra; // House Rent Allowance

	@Column(name = "da")
	private Float da; // Dearness Allowance

	@Column(name = "conveyance_allowance")
	private Float conveyanceAllowance;

	@Column(name = "medical_allowance")
	private Float medicalAllowance;

	@Column(name = "ita")
	private Float ita; // Interactive Tax Assistant

	@Column(name = "special_allowance")
	private Float specialAllowance;

	@Column(name = "bonus")
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

	@Column(name = "pay_datetime")
	private Date payDateTime;

	@Column(name = "monthly_leaves_allowed")
	private Integer monthlyLeavesAllowed;

	@Column(name = "overtime_rate")
	private Float overtimeRate;

	@Column(name = "attendance_bonus")
	private Float attendanceBonus;
	
	@Column(name = "total_days")
	private Integer totalDays;
	
	@Column(name = "total_working_days")
	private Integer totalWorkingDays;

	@Column(name = "updated_by")
	private String updatedBy;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;
}
