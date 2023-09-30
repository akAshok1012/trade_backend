package com.tm.app.entity;

import java.sql.Date;
import java.sql.Timestamp;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.context.annotation.Lazy;

import com.tm.app.enums.EmployeeLeaveStatus;
import com.tm.app.enums.LeaveType;

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
@Table(name = "t_employee_leave_manager")
public class EmployeeLeaveManager {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@Lazy
	@JoinColumn(name = "employee_id")
	private Employee employee;
	
	@Column(name = "start_date")
	private Date startDate;
	
	@Column(name = "end_date")
	private Date endDate;
	
	@Column(name = "reason")
	private String reason;
	
	@Enumerated(EnumType.STRING)
	private EmployeeLeaveStatus employeeLeaveStatus;
	
	@Column(name = "updated_by")
	private String updatedBy;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;
	
	@Enumerated(EnumType.STRING)
	private LeaveType leaveType;
	
	@Column(name = "duration")
	private Integer duration;
	
	@Column(name = "is_compensation")
	private Boolean isCompensation;

	@Column(name = "compensation_date")
	private Date compensationDate;
	
	@Column(name = "is_notification_enabled")
	private Boolean isNotificationEnabled;
}
