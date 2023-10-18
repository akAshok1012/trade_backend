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
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(name = "t_employee_pay_hours",uniqueConstraints={
	    @UniqueConstraint(columnNames = {"work_date", "employee_id"})
	}) 
public class EmployeePayHours {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;

	@Column(name = "work_date")
	private Date workDate;

	@Column(name = "hours_worked")
	private Integer hoursWorked;

	@Column(name = "hourly_pay")
	private Integer hourlyPay;

	@Column(name = "updated_by")
	private String updatedBy;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@Transient
	private String errorDescription;

	public EmployeePayHours(String hoursWorked, String hourlyPay, Date workDate, String updatedBy, Employee employee) {

		if (Objects.isNull(workDate)) {
			throw new IllegalArgumentException("WorkDate should not be empty");
		}

		if (StringUtils.isEmpty(hoursWorked)) {
			throw new IllegalArgumentException("Hours Worked should not be empty");
		}

		if (StringUtils.isEmpty(hourlyPay)) {
			throw new IllegalArgumentException("Hourly Pay should not be empty");
		}

		if (StringUtils.isEmpty(updatedBy)) {
			throw new IllegalArgumentException("UpdatedBy should not be empty");
		}

		this.employee = employee;
		this.workDate = workDate;
		this.hoursWorked = StringUtils.isNotEmpty(hoursWorked) ? Integer.parseInt(hoursWorked) : null;
		this.hourlyPay = StringUtils.isNotEmpty(hourlyPay) ? Integer.parseInt(hourlyPay) : null;
		this.updatedBy = updatedBy;
	}

	public EmployeePayHours() {
	}

}
