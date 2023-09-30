CREATE OR REPLACE FUNCTION public.generate_monthly_payslip()
 RETURNS void
 LANGUAGE plpgsql
AS $function$
declare
    emp_id integer;

pay_config_row RECORD;

holidays_count integer;

monthly_pay_record RECORD;

total_days integer;

total_working_days integer;

attendance_bonus float;

monthly_leaves_allowed float;

overtime_rate float;

basic_pay float;

hra float;

da float;

medical_allowance float;

bonus float;

special_allowance float;

conveyance_allowance float;

earnings float;

esi float;

epf float;

ita float;

deductions float;

net_pay float;

holiday_date date;

attendance_closing_date date;

first_day_of_month date;

begin
-- Loop through all employee pay configurations
    FOR pay_config_row IN SELECT a.*,b.* FROM public.t_employee_pay_configuration a,public.t_employee_pay b LOOP
        emp_id := pay_config_row.employee_id;
       
         SELECT  DATE_TRUNC('MONTH',DATE (pay_config_row.attendance_closing_date)) into first_day_of_month;
       	
        -- Get the total number of holidays in the month
       SELECT COUNT(h.holiday_date) into holidays_count
FROM t_holidays h where h.holiday_date between first_day_of_month and pay_config_row.attendance_closing_date ;

-- Calculate total days in the month
total_days := EXTRACT(DAY FROM (DATE_TRUNC('MONTH', CURRENT_DATE) + INTERVAL '1 MONTH - 1 DAY'));

-- Calculate total working days
total_working_days := total_days - holidays_count;

-- Calculate attendance bonus based on attendance percentage
attendance_bonus := pay_config_row.attendance_bonus * total_working_days / total_days;

-- Calculate other pay components (you may customize these calculations based on your requirements)
basic_pay := pay_config_row.basic_pay;

hra := pay_config_row.hra;

da := pay_config_row.da;

medical_allowance := pay_config_row.medical_allowance;

bonus := pay_config_row.bonus;

special_allowance := pay_config_row.special_allowance;

esi := pay_config_row.esi;

epf := pay_config_row.epf;

ita := pay_config_row.ita;

total_days := total_days;

total_working_days := total_working_days;

conveyance_allowance := pay_config_row.conveyance_allowance;

attendance_bonus := pay_config_row.attendance_bonus;

monthly_leaves_allowed := pay_config_row.monthly_leaves_allowed;

overtime_rate := pay_config_row.overtime_rate;

earnings := basic_pay + hra + da + medical_allowance + bonus + special_allowance + conveyance_allowance + attendance_bonus;

deductions := esi + epf + ita;

net_pay := earnings - deductions;
-- Insert the payslip record into the t_employee_monthly_pay table
        insert
	into
	public.t_employee_monthly_pay (
    basic_pay,
	hra,
	da,
	medical_allowance,
	bonus,
	special_allowance,
	conveyance_allowance,
	earnings,
	esi,
	epf,
	ita,
	deductions,
	net_pay,
	attendance_bonus,
	monthly_leaves_allowed,
	overtime_rate,
	total_days, 
	total_working_days ,
	pay_datetime,
	updated_at,
	updated_by,
	employee_id
        )
values (
            basic_pay,
            hra,
            da,
            medical_allowance,
            bonus,
            special_allowance,
conveyance_allowance,
earnings, 
            esi,
            epf,
            ita,
            deductions,
            net_pay,
attendance_bonus,
monthly_leaves_allowed,
overtime_rate,
total_days ,
total_working_days ,
            pay_config_row.attendance_closing_date,
            NOW(),
            'system',
-- Change this to the username or identifier of the process that generates payslips
emp_id
        );
end loop;
end;

$function$
;
