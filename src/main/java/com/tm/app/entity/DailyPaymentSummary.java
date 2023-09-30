package com.tm.app.entity;

import java.sql.Date;

import org.hibernate.annotations.Subselect;
import org.springframework.data.annotation.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
@Immutable
@Subselect(value = "select customer_id, date(payment_date) as date, paid_amount, balance_amount FROM x t_payment where payment_date >= CURRENT_DATE - interval '30 days';")
public class DailyPaymentSummary {

	@Id

	private Long customerId;

	private Date date;

	private Float paidAmount;

	private Float balanceAmount;

}
