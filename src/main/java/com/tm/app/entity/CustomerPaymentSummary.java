package com.tm.app.entity;

import org.hibernate.annotations.Subselect;
import org.springframework.data.annotation.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
@Immutable
@Subselect(value = "select (c.id) as customer_id , (c.client_name)as client_name, sum(p.payment_amount) as total_payment_amount, SUM(p.paid_amount) as total_paid_amount, SUM(p.balance_amount) as total_balance_amount from t_payment p join t_customer c on (c.id = p.customer_id) group by c.id , c.client_name;")
public class CustomerPaymentSummary {

	@Id

	private Long customerId;

	private String clientName;

	private Float totalPaymentAmount;

	private Float totalPaidAmount;

	private Float totalBalanceAmount;

}
