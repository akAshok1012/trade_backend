package com.tm.app.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

import org.hibernate.annotations.UpdateTimestamp;

import io.micrometer.common.util.StringUtils;
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
@Table(name = "t_employee_weekly_wages")
public class EmployeeWeeklyWages {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;

	@Column(name = "work_start_date")
	private Date workStartDate;

	@Column(name = "work_end_date")
	private Date workEndDate;

	@Column(name = "weekly_worked_hours")
	private Integer weeklyWorkedHours;

	@Column(name = "hourly_pay")
	private Integer hourlyPay;

	@Column(name = "weekly_total_pay")
	private Integer weeklyTotalPay;

	@Column(name = "updated_by")
	private String updatedBy;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@Transient
	private String errorDescription;

	public EmployeeWeeklyWages(Date workStartDate, Date workEndDate, String weeklyWorkedHours, String hourlyPay,
			String weeklyTotalPay, String updatedBy, Employee employee) {

		if (Objects.isNull(workStartDate)) {
			throw new IllegalArgumentException("WorkStartDate should not be empty");
		}
		
		if (Objects.isNull(workEndDate)) {
			throw new IllegalArgumentException("WorkEndDate should not be empty");
		}

		if (StringUtils.isEmpty(weeklyWorkedHours)) {
			throw new IllegalArgumentException("Weekly Worked Hours should not be empty");
		}

		if (StringUtils.isEmpty(hourlyPay)) {
			throw new IllegalArgumentException("Hourly Pay should not be empty");
		}
		
		if (StringUtils.isEmpty(weeklyTotalPay)) {
			throw new IllegalArgumentException("Weekly Total Pay should not be empty");
		}

		if (StringUtils.isEmpty(updatedBy)) {
			throw new IllegalArgumentException("UpdatedBy should not be empty");
		}

		this.employee = employee;
		this.workStartDate = workStartDate;
		this.workEndDate = workEndDate;
		this.weeklyWorkedHours = StringUtils.isNotEmpty(weeklyWorkedHours) ? Integer.parseInt(weeklyWorkedHours) : null;
		this.hourlyPay = StringUtils.isNotEmpty(hourlyPay) ? Integer.parseInt(hourlyPay) : null;
		this.weeklyTotalPay = StringUtils.isNotEmpty(weeklyTotalPay) ? Integer.parseInt(weeklyTotalPay) : null;
		this.updatedBy = updatedBy;
	}

	public EmployeeWeeklyWages() {
	}
}
