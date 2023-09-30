package com.tm.app.entity;

import org.hibernate.annotations.Subselect;
import org.springframework.data.annotation.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
@Immutable
@Subselect(value = "select tc.id,tc.client_name,orders.order_count,sales.sales_count,count(distinct to2.reject_reason_id) as rejected,tcw.balance as wallet_amount,sum(tp.payment_amount) as total_payment_amount,sum(tp.paid_amount) as total_paid_amount,sum(tp.balance_amount) as total_balance_amount from t_customer tc left join select customer_id,count(distinct order_id) as order_count from t_order group by customer_id) orders on tc.id = orders.customer_id left join select customer_id,count(distinct sales_id) as sales_count from t_sales_order group by customer_id) sales o tc.id = sales.customer_id left join t_order to2 on to2.customer_id = tc.id left join t_sales_order tso on tso.customer_id = tc.id left join t_customer_wallet tcw on tc.id = tcw.customer left join t_payment tp on tp.customer_id = tc.id and tp.order_id = to2.order_id and tp.sale_id = tso.sales_id group by tc.id, tc.client_name,orders.order_count,sales.sales_count,tcw.balance;")
public class CustomerSalesAndOrderSummary {

	@Id
	
	private Long id;
	
	private String clientName;

	private Integer orderCount;

	private Integer salesCount;

	private Integer rejected;

	private Float walletAmount;

	private Float totalPaymentAmount;

	private Float totalPaidAmount;

	private Float totalBalanceAmount;

}
