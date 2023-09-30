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
@Table(name = "t_employee_contractor")
public class EmployeeContractor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "hire_date")
	private Date hireDate;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "daily_rate")
	private Float dailyRate;

	@Column(name = "title")
	private String title;

	@Column(name = "department")
	private String department;

	@Column(name = "updatedBy")
	private String updatedBy;

	@UpdateTimestamp
	@Column(name = "update_at")
	private Timestamp updatedAt;

}
