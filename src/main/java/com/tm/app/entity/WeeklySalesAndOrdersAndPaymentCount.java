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
@Subselect(value = "with all_weeks as ( select distinct date_trunc('week', generate_series) as date from generate_series( date_trunc('week', CURRENT_DATE::timestamp with time zone) - interval '4 weeks', date_trunc('week', CURRENT_DATE::timestamp with time zone), interval '1 week' ) ) select aw.date, coalesce(SUM(cd.order_counts), 0) as total_order_counts, coalesce(SUM(cd.sales_counts), 0) as total_sales_counts, coalesce(SUM(cd.paid_amount), 0) as total_paid_amount from all_weeks aw left join ( select date_trunc('week', t_order.updated_at) as week_start, COUNT(t_order.order_id) as order_counts, null::real as sales_counts, null::real as paid_amount from t_order where date_trunc('week', t_order.updated_at) >= date_trunc('week', CURRENT_DATE::timestamp with time zone) - interval '4 weeks' group by date_trunc('week', t_order.updated_at) union all select date_trunc('week', t_sales_order.updated_at) as week_start, null::real as order_counts, COUNT(t_sales_order.sales_id) as sales_counts, null::real as paid_amount from t_sales_order where date_trunc('week', t_sales_order.updated_at) >= date_trunc('week', CURRENT_DATE::timestamp with time zone) - interval '4 weeks' group by date_trunc('week', t_sales_order.updated_at) union all select date_trunc('week', t_payment.updated_at) as week_start, null::real as order_counts, null::real as sales_counts, SUM(t_payment.total_paid_amount) as paid_amount from t_payment where date_trunc('week', t_payment.updated_at) >= date_trunc('week', CURRENT_DATE::timestamp with time zone) - interval '4 weeks' group by date_trunc('week', t_payment.updated_at) ) cd on aw.date = cd.week_start group by aw.date order by aw.date;")
public class WeeklySalesAndOrdersAndPaymentCount {

	@Id
	private Date date;
	private Integer totalOrderCounts;
	private Integer totalSalesCounts;
	private Float totalPaidAmount;

}
