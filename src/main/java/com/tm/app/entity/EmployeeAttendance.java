package com.tm.app.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
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
@Table(name = "t_employee_attendance")
public class EmployeeAttendance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;

	@CreationTimestamp
	@Column(name = "attendance_datetime")
	private Timestamp attendanceDatetime;

	@Column(name = "total_hours")
	private Float totalHours;

	@Column(name = "overtime_hours")
	private Float overtimeHours;

	@UpdateTimestamp
	@Column(name = "update_at")
	private Timestamp updatedAt;

	@Column(name = "updated_by")
	private String updatedBy;

}
