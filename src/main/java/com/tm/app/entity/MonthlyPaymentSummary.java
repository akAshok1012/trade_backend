package com.tm.app.entity;

import org.hibernate.annotations.Subselect;
import org.springframework.data.annotation.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
@Immutable
@Subselect(value = "SELECT customer_id, SUM(paid_amount) AS total_paid_amount, SUM(balance_amount) AS total_balance_amount FROM t_payment WHERE date(payment_date) >= CURRENT_DATE - INTERVAL '30 days' GROUP BY customer_id;")
public class MonthlyPaymentSummary {

	@Id

	private Long customerId;

	private Float totalPaidAmount;

	private Float totalBalanceAmount;
}
