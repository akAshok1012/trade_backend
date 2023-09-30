package com.tm.app.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import org.hibernate.annotations.UpdateTimestamp;

import com.tm.app.enums.DailyStatus;

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
@Table(name = "t_employee_daily_status")
public class EmployeeDailyStatus implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;

	@Enumerated(EnumType.STRING)
	private DailyStatus dailyStatus;
	
	private Date date;
	
	@Column(name = "notes")
	private String notes;

	@Column(name = "updated_by")
	private String updatedBy;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;

}
