package com.tm.app.entity;

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
@Table(name = "t_employee_type")
public class EmployeeType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "employee_type")
	private String employeeType;

	@Column(name = "description")
	private String description;

	@Column(name = "updatedBy")
	private String updatedBy;

	@UpdateTimestamp
	@Column(name = "update_at")
	private Timestamp updatedAt;

}
