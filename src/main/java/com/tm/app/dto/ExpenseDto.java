package com.tm.app.dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.tm.app.entity.Employee;
import com.tm.app.entity.ExpenseDetails;

import lombok.Data;

@Data
public class ExpenseDto {

	private List<ExpenseDetails> expenseDetails;
	private Date date;
	private String notes;
	private String status;
	private Employee employee;
	private Timestamp updatedAt;
	private String updatedBy;

}
