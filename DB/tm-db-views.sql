create or replace  view v_customer_payment_daily_summary as
select
	customer_id,
	date(payment_date) as date,
	paid_amount,
	balance_amount
from
	t_payment
where
	payment_date >= CURRENT_DATE - interval '30 days';


 CREATE OR replace  VIEW v_customer_payment_monthly_summary AS
 SELECT customer_id,
  SUM(paid_amount) AS total_paid_amount,
  SUM(balance_amount) AS total_balance_amount
 FROM t_payment 
 WHERE date(payment_date) >= CURRENT_DATE - INTERVAL '30 days'
 GROUP BY customer_id; 


 create or replace view v_customers_payment_summary as
 select
	(user_id) as id ,
	(c.client_name)as client_name,
	sum(p.payment_amount) as total_payment_amount,
	SUM(p.paid_amount) as total_paid_amount,
	SUM(p.balance_amount) as total_balance_amount
from
	t_payment p
join t_customer c on
	(c.id = p.customer_id)
group by
	user_id,
	c.client_name	

	
create or replace view v_customer_sales_orders_payment_summary as
select
	tc.id,
	tc.client_name,
	count(distinct to2.order_id) as orders,
	count(distinct tso.sales_id) as sales,
	count(distinct to2.reject_reason_id) as rejected,
	sum(tp.payment_amount) as total_payment_amount,
	sum(tp.paid_amount) as total_paid_amount,
	sum(tp.balance_amount) as total_balance_amount
from
	t_order to2,
	t_sales_order tso,
	t_customer tc,
	t_payment tp
where
	to2.customer_id = tc.id
	and tso.customer_id = tc.id
	and tc.id = tp.customer_id
	and tp.order_id = to2.order_id
	and tp.sale_id = tso.sales_id
group by
	tc.id,
	tc.client_name;


create or replace view v_customer_daily_sales_orders AS (
  SELECT DATE_TRUNC('day', updated_at) AS date,
  COUNT(DISTINCT sales_id) AS salesCount,
  0 AS orderCount
  FROM t_sales_order 
  WHERE updated_at >= CURRENT_DATE - INTERVAL '30 days'
  GROUP BY DATE_TRUNC('day', updated_at)

  UNION ALL

  SELECT DATE_TRUNC('day', updated_at) AS date,
  0 AS salesCount,
  COUNT(DISTINCT order_id) AS orderCount
  FROM t_order 
  WHERE updated_at >= CURRENT_DATE - INTERVAL '30 days'
  GROUP BY DATE_TRUNC('day', updated_at)
 )
 
 CREATE OR REPLACE VIEW v_total_payment_summary AS 
select 
 sum(p.payment_amount) as total_payment_amount,
 SUM(p.paid_amount) as total_paid_amount,
 SUM(p.balance_amount) as total_balance_amount
from
t_payment p
	
