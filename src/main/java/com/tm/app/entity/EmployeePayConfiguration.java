package com.tm.app.entity;

import java.sql.Date;
import java.sql.Timestamp;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "t_employee_pay_configuration")
public class EmployeePayConfiguration {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "monthly_leaves_allowed")
	private Integer monthlyLeavesAllowed;

	@Column(name = "overtime_rate")
	private Float overtimeRate;

	@Column(name = "attendance_bonus")
	private Float attendanceBonus;

	@Column(name = "attendance_closing_date")
	private Date attendanceClosingDate;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;
	
	@Column(name = "updated_by")
	private String updatedBy;

}
