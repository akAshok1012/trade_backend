create or replace
view v_production_count as
select
	tb.id as brand,
	tp.item_id as itemid,
	tim.item_name as item,
	SUM(tpd.quantity) as quantity
from
	t_production tp
join
    t_production_details tpd on
	(tp.id = tpd.production_id)
join
    t_item_master tim on
	(tim.id = tp.item_id)
join
    t_brand tb on
	(tim.brand_id = tb.id)
join
 t_unit_of_measure tuom on
	(tuom.id = tb.id)
group by	
	tb.id,
	tp.item_id,
	tim.item_name;
	
	
create or replace
view v_monthly_production_count as
select
	DATE_TRUNC('month',
	update_at) as date,
	SUM(tpd.quantity) as quantity
from
	t_production tp
join
    t_production_details tpd on
	(tp.id = tpd.production_id)
join
    t_item_master tim on
	(tim.id = tp.item_id)
where
	update_at >= CURRENT_DATE - interval '30 days'
group by
	DATE_TRUNC('month',
	update_at)
order by
	DATE_TRUNC('month',
	update_at) asc;
	

create or replace
view v_weekly_production_count as
select
	DATE_TRUNC('week',
	update_at) as date,
	SUM(tpd.quantity) as quantity
from
	t_production tp
join
    t_production_details tpd on
	(tp.id = tpd.production_id)
join
    t_item_master tim on
	(tim.id = tp.item_id)
where
	update_at >= CURRENT_DATE - interval '7 days'
group by
	DATE_TRUNC('week',
	update_at)
order by
	DATE_TRUNC('week',
	update_at) asc;


create or replace
view v_production_count_by_brand as
select
	tb.id as brand,
	tb."name" as brandName,
	SUM(tpd.quantity) as quantity
from
	t_production tp
join
    t_production_details tpd on
	(tp.id = tpd.production_id)
join
    t_item_master tim on
	(tim.id = tp.item_id)
join
    t_brand tb on
	(tim.brand_id = tb.id)
group by
	tb.id,
	tb."name";
	

create or replace
view v_daily_production_count as
select
	DATE_TRUNC('day',
	update_at) as date,
	SUM(tpd.quantity) as quantity
from
	t_production tp
join
    t_production_details tpd on
	(tp.id = tpd.production_id)
join
    t_item_master tim on
	(tim.id = tp.item_id)
where
	update_at >= CURRENT_DATE
group by
	DATE_TRUNC('day',
	update_at);
	

create or replace view v_production_count_by_item as          
select
	tp.item_id as itemid,
	tuom.unit_name as uom,
	tim.item_name as item,
	SUM(tpd.quantity) as quantity
from
	t_production tp
join
    t_production_details tpd on
	(tp.id = tpd.production_id)
join
    t_item_master tim on
	(tim.id = tp.item_id)
join
 t_unit_of_measure tuom on
	(tuom.id = tp.item_id)

group by
	tuom.unit_name,
	tp.item_id,
	tim.item_name;
	

create or replace
view v_production_brand as
select
	 tb.id as brand,
	 tuom.unit_name as uom,
	SUM(tpd.quantity) as quantity
from
	t_production tp
join
    t_production_details tpd on
	(tp.id = tpd.production_id)
join
    t_item_master tim on
	(tim.id = tp.item_id)
join
    t_brand tb on
	(tim.brand_id = tb.id)
join
 t_unit_of_measure tuom on
	(tuom.id = tb.id)
group by	
	tb.id,
	tuom.unit_name;
	

