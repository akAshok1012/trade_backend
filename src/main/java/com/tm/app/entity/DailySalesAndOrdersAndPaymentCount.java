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
@Subselect(value = "with all_dates as (select distinct(CURRENT_DATE - n) as date from generate_series(0,29) as)select ad.date,coalesce(SUM(cd.order_counts),0) as total_order_counts,coalesce(SUM(cd.sales_counts),0) as total_sales_counts,coalesce(SUM(cd.paid_amount),0) as total_paid_amount from all_dates ad left join (select DATE_TRUNC('day',updated_at) as date,COUNT(order_id) as order_counts,null::real as sales_counts,null::real as paid_amount from t_order where updated_at >= CURRENT_DATE - interval '30 days'group by DATE_TRUNC('day',updated_at) union all select DATE_TRUNC('day',updated_at) as date,null::real as order_counts,COUNT(sales_id) as sales_counts,null::real as paid_amount from t_sales_order where updated_at >= CURRENT_DATE - interval '30 days' group by DATE_TRUNC('day',updated_at) union all select DATE_TRUNC('day',updated_at) as date,null::real as order_counts,null::real as sales_counts,SUM(paid_amount) as paid_amount from t_payment where updated_at >= CURRENT_DATE - interval '30 days'group by DATE_TRUNC('day',updated_at) ) as cd on ad.date = cd.date group by ad.date order by ad.date asc;")
public class DailySalesAndOrdersAndPaymentCount {

	@Id
	private Date date;
	private Integer totalOrderCounts;
	private Integer totalSalesCounts;
	private Float totalPaidAmount;

}
