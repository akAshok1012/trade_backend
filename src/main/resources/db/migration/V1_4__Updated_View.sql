
CREATE OR REPLACE VIEW v_customer_sales_orders_payment_summary
AS SELECT tc.id,
    tc.client_name,
    orders.order_count,
    sales.sales_count,
    count(DISTINCT to2.reject_reason_id) AS rejected,
    tcw.balance AS wallet_amount,
    sales.total_payment_amount,
    sales.total_paid_amount,
    sales.total_balance_amount
   FROM t_customer tc
     LEFT JOIN ( SELECT t_order.customer_id,
            count(DISTINCT t_order.order_id) AS order_count
           FROM t_order
          GROUP BY t_order.customer_id) orders ON tc.id = orders.customer_id
     LEFT JOIN ( SELECT tso_1.customer_id,
            count(DISTINCT tso_1.sales_id) AS sales_count,
            sum(tp.total_order_amount) AS total_payment_amount,
            sum(tp.total_paid_amount) AS total_paid_amount,
            sum(tp.balance_amount) AS total_balance_amount
           FROM t_sales_order tso_1
             LEFT JOIN t_payment tp ON tp.sale_id = tso_1.sales_id
          GROUP BY tso_1.customer_id) sales ON tc.id = sales.customer_id
     LEFT JOIN t_order to2 ON to2.customer_id = tc.id
     LEFT JOIN t_sales_order tso ON tso.customer_id = tc.id
     LEFT JOIN t_customer_wallet tcw ON tc.id = tcw.customer
  GROUP BY tc.id, tc.client_name, orders.order_count, sales.sales_count, tcw.balance, sales.total_payment_amount, sales.total_paid_amount, sales.total_balance_amount
HAVING orders.order_count > 0 AND sales.sales_count > 0;



create or replace
view v_weekly_orders_sales_payment_count as
with all_weeks as (
select
	distinct
    date_trunc('week',
	generate_series) as date
from
	generate_series(
    date_trunc('week',
	CURRENT_DATE::timestamp with time zone) - interval '4 weeks',
	date_trunc('week',
	CURRENT_DATE::timestamp with time zone),
	interval '1 week'
  ) 
)
select
	aw.date,
	coalesce(SUM(cd.order_counts),
	0) as total_order_counts,
	coalesce(SUM(cd.sales_counts),
	0) as total_sales_counts,
	coalesce(SUM(cd.paid_amount),
	0) as total_paid_amount
from
	all_weeks aw
left join (
	select
		date_trunc('week',
		t_order.updated_at) as week_start,
		COUNT(t_order.order_id) as order_counts,
		null::real as sales_counts,
		null::real as paid_amount
	from
		t_order
	where
		date_trunc('week',
		t_order.updated_at) >= date_trunc('week',
		CURRENT_DATE::timestamp with time zone) - interval '4 weeks'
	group by
		date_trunc('week',
		t_order.updated_at)
union all
	select
		date_trunc('week',
		t_sales_order.updated_at) as week_start,
		null::real as order_counts,
		COUNT(t_sales_order.sales_id) as sales_counts,
		null::real as paid_amount
	from
		t_sales_order
	where
		date_trunc('week',
		t_sales_order.updated_at) >= date_trunc('week',
		CURRENT_DATE::timestamp with time zone) - interval '4 weeks'
	group by
		date_trunc('week',
		t_sales_order.updated_at)
union all
	select
		date_trunc('week',
		t_payment.updated_at) as week_start,
		null::real as order_counts,
		null::real as sales_counts,
		SUM(t_payment.total_paid_amount) as paid_amount
	from
		t_payment
	where
		date_trunc('week',
		t_payment.updated_at) >= date_trunc('week',
		CURRENT_DATE::timestamp with time zone) - interval '4 weeks'
	group by
		date_trunc('week',
		t_payment.updated_at)
) cd on
	aw.date = cd.week_start
group by
	aw.date
order by
	aw.date;









