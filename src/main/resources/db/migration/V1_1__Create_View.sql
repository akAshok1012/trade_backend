create or replace view v_daily_lead_generation_count as
select  DATE_TRUNC('day', updated_at) AS date,
 count(id)as Leads
 from
        t_lead_generation  
where
        updated_at >= CURRENT_DATE - interval '30 days'
        GROUP BY DATE_TRUNC('day', updated_at)
        ORDER BY DATE_TRUNC('day', updated_at) ASC;






create or replace view v_weekly_lead_generation_count as
select  DATE_TRUNC('week', updated_at) AS date,
 count(id)as Leads
 from
        t_lead_generation  
where
        updated_at >= CURRENT_DATE - interval '7 days'
        GROUP BY DATE_TRUNC('week', updated_at)
        ORDER BY DATE_TRUNC('week', updated_at) ASC;





create or replace view v_yearly_lead_generation_count as
select
        DATE_TRUNC('year', updated_at) as date,
        count(id)as Leads
from
        t_lead_generation
where
        updated_at >= CURRENT_DATE - interval '365 days'
group by
        DATE_TRUNC('year', updated_at);
	
	
	
		
	    create or replace view v_lead_generation_createdBy_count as 
	select 
	count(id) as leads_count,
	updated_by
	from 
	t_lead_generation
	group by updated_by;
	
	
	
	create or replace view v_lead_generation_status_count as
select 
	count(id) as leads_count,
	status
	from 
	t_lead_generation
	group by status;


	CREATE OR REPLACE VIEW v_total_payment_summary AS 
	select 
	sum(p.total_order_amount) as total_payment_amount,
	SUM(p.total_paid_amount) as total_paid_amount,
	SUM(p.balance_amount) as total_balance_amount
from
	t_payment p;
	
	
	 create or replace view v_customers_payment_summary as
 select
    (c.id) as customer_id ,
	(c.client_name)as client_name,
	sum(p.total_order_amount) as total_payment_amount,
	SUM(p.total_paid_amount) as total_paid_amount,
	SUM(p.balance_amount) as total_balance_amount
from
	t_payment p
join t_customer c on
	(c.id = p.customer_id)
group by
    c.id ,
	c.client_name;
	
	create or replace  view v_daily_orders_sales_payment_count as
with all_dates as (
select
	distinct
        (CURRENT_DATE - n) as date
from
	generate_series(0,
	29) as n
)
select
	ad.date,
	coalesce(SUM(cd.order_counts),
	0) as total_order_counts,
	coalesce(SUM(cd.sales_counts),
	0) as total_sales_counts,
	coalesce(SUM(cd.paid_amount),
	0) as total_paid_amount
from
	all_dates ad
left join (
	select
		DATE_TRUNC('day',
		updated_at) as date,
		COUNT(order_id) as order_counts,
		null::real as sales_counts,
		null::real as paid_amount
	from
		t_order
	where
		updated_at >= CURRENT_DATE - interval '30 days'
	group by
		DATE_TRUNC('day',
		updated_at)
union all
	select
		DATE_TRUNC('day',
		updated_at) as date,
		null::real as order_counts,
		COUNT(sales_id) as sales_counts,
		null::real as paid_amount
	from
		t_sales_order
	where
		updated_at >= CURRENT_DATE - interval '30 days'
	group by
		DATE_TRUNC('day',
		updated_at)
union all
	select
		DATE_TRUNC('day',
		updated_at) as date,
		null::real as order_counts,
		null::real as sales_counts,
		SUM(total_paid_amount) as paid_amount
	from
		t_payment
	where
		updated_at >= CURRENT_DATE - interval '30 days'
	group by
		DATE_TRUNC('day',
		updated_at)
) as cd on
	ad.date = cd.date
group by
	ad.date
order by
	ad.date asc;




create or replace  view v_weekly_orders_sales_payment_count as
with all_weeks as (
select
	distinct
        (DATE_TRUNC('week',
	CURRENT_DATE) - n * interval '7 days') as date
from
	generate_series(0,
	3) as n
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
		DATE_TRUNC('week',
		updated_at) as date,
		COUNT(order_id) as order_counts,
		null::real as sales_counts,
		null::real as paid_amount
	from
		t_order
	where
		updated_at >= CURRENT_DATE - interval '7 days'
	group by
		DATE_TRUNC('week',
		updated_at)
union all
	select
		DATE_TRUNC('week',
		updated_at) as date,
		null::real as order_counts,
		COUNT(sales_id) as sales_counts,
		null::real as paid_amount
	from
		t_sales_order
	where
		updated_at >= CURRENT_DATE - interval '7 days'
	group by
		DATE_TRUNC('week',
		updated_at)
union all
	select
		DATE_TRUNC('week',
		updated_at) as date,
		null::real as order_counts,
		null::real as sales_counts,
		SUM(total_paid_amount) as paid_amount
	from
		t_payment
	where
		updated_at >= CURRENT_DATE - interval '7 days'
	group by
		DATE_TRUNC('week',
		updated_at)
) as cd on
	aw.date = cd.date
group by
	aw.date
order by
	aw.date asc;
	
	
	
	create or replace  view v_monthly_orders_sales_payment_count as
with all_months as (
select
	distinct
        DATE_TRUNC('month',
	CURRENT_DATE - (n || ' months')::interval) as date
from
	generate_series(0,
	11) as n
)
select
	am.date,
	coalesce(SUM(cd.order_counts),
	0) as total_order_counts,
	coalesce(SUM(cd.sales_counts),
	0) as total_sales_counts,
	coalesce(SUM(cd.paid_amount),
	0) as total_paid_amount
from
	all_months am
left join (
	select
		DATE_TRUNC('month',
		updated_at) as date,
		COUNT(order_id) as order_counts,
		null::real as sales_counts,
		null::real as paid_amount
	from
		t_order
	where
		updated_at >= CURRENT_DATE - interval '30 days'
	group by
		DATE_TRUNC('month',
		updated_at)
union all
	select
		DATE_TRUNC('month',
		updated_at) as date,
		null::real as order_counts,
		COUNT(sales_id) as sales_counts,
		null::real as paid_amount
	from
		t_sales_order
	where
		updated_at >= CURRENT_DATE - interval '30 days'
	group by
		DATE_TRUNC('month',
		updated_at)
union all
	select
		DATE_TRUNC('month',
		updated_at) as date,
		null::real as order_counts,
		null::real as sales_counts,
		SUM(total_paid_amount) as paid_amount
	from
		t_payment
	where
		updated_at >= CURRENT_DATE - interval '30 days'
	group by
		DATE_TRUNC('month',
		updated_at)
) as cd on
	am.date = cd.date
group by
	am.date
order by
	am.date asc;




create or replace  view v_customer_sales_orders_summary as
select
	tc.id,
	tc.client_name,
	orders.order_count,
	sales.sales_count,
	COUNT(distinct to2.reject_reason_id) as rejected,
	tcw.balance as wallet_amount,
	sales.total_payment_amount,
	sales.total_paid_amount,
	sales.total_balance_amount
from
	t_customer tc
left join (
	select
		customer_id,
		COUNT(distinct order_id) as order_count
	from
		t_order
	group by
		customer_id
) orders on
	tc.id = orders.customer_id
	
left join (
	select
		tso.customer_id,
		COUNT(distinct sales_id) as sales_count,
		SUM(tp.total_order_amount) as total_payment_amount,
		SUM(tp.total_paid_amount) as total_paid_amount,
		SUM(tp.balance_amount) as total_balance_amount
	from
		t_sales_order tso
	left join t_payment tp on
		(tp.sale_id = tso.sales_id)
	group by
		tso.customer_id

) sales on
	tc.id = sales.customer_id
left join t_order to2 on
	to2.customer_id = tc.id
left join t_sales_order tso on
	tso.customer_id = tc.id
left join t_customer_wallet tcw on
	tc.id = tcw.customer
group by
	tc.id,
	tc.client_name,
	orders.order_count,
	sales.sales_count,
	tcw.balance,
	sales.total_payment_amount,
	sales.total_paid_amount,
	sales.total_balance_amount;
	
	
	create or replace view v_customer_sales_orders_payment_summary as
select
	tc.id,
	tc.client_name,
	orders.order_count,
	sales.sales_count,
	COUNT(distinct to2.reject_reason_id) as rejected,
	tcw.balance as wallet_amount,
	sales.total_payment_amount,
	sales.total_paid_amount,
	sales.total_balance_amount
from
	t_customer tc
left join (
	select
		customer_id,
		COUNT(distinct order_id) as order_count
	from
		t_order
	group by
		customer_id
) orders on
	tc.id = orders.customer_id
	
left join (
	select
		tso.customer_id,
		COUNT(distinct sales_id) as sales_count,
		SUM(tp.total_order_amount) as total_payment_amount,
		SUM(tp.total_paid_amount) as total_paid_amount,
		SUM(tp.balance_amount) as total_balance_amount
	from
		t_sales_order tso
	left join t_payment tp on
		(tp.sale_id = tso.sales_id)
	group by
		tso.customer_id

) sales on
	tc.id = sales.customer_id
left join t_order to2 on
	to2.customer_id = tc.id
left join t_sales_order tso on
	tso.customer_id = tc.id
left join t_customer_wallet tcw on
	tc.id = tcw.customer
group by
	tc.id,
	tc.client_name,
	orders.order_count,
	sales.sales_count,
	tcw.balance,
	sales.total_payment_amount,
	sales.total_paid_amount,
	sales.total_balance_amount;


