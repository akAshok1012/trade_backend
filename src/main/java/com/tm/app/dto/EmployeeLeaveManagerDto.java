package com.tm.app.dto;

import java.sql.Date;

import com.tm.app.entity.Employee;
import com.tm.app.enums.EmployeeLeaveStatus;
import com.tm.app.enums.LeaveType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeLeaveManagerDto {
	private Employee employee;
	private Date startDate;
	private Date endDate;
	private String reason;
	private String updatedBy;
	private Integer duration;
	private LeaveType leaveType;
	private EmployeeLeaveStatus employeeLeaveStatus;
	private Boolean isCompensation;
	private Date compensationDate;
}
