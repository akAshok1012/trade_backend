CREATE OR REPLACE FUNCTION public.calculate_employee_pay(employee_id_param bigint, from_date_param date, to_date_param date)
 RETURNS TABLE(totalpay bigint, totalhoursworked bigint)
 LANGUAGE plpgsql
AS $function$
begin
    return QUERY
      select
	SUM(e.hours_worked * e.hourly_pay) as totalPay,
	SUM(e.hours_worked) as totalHoursWorked
from
	t_employee_pay_hours e
where
	e.employee_id = employee_id_param
	and e.work_date between  from_date_param and to_date_param
           ;
end;

$function$
;
