package com.tm.app.entity;

import org.springframework.context.annotation.Lazy;

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
@Table(name = "t_expense_details")
public class ExpenseDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@Lazy
	@JoinColumn(name = "expense_category_id")
	private ExpenseCategory expenseCategory;
	
	@Column(name = "amount")
	private Float amount;
	
	@Column(name = "receipt_file")
	private String receiptFile;
}
