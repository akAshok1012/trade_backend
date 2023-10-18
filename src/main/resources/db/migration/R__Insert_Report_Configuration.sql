insert into t_report_configuration
(
   id,
   header_fields,
   mandatory_fields,
   table_name,
   template_name,
   unique_fields
)
values
(
   1,
   '{ADDRESS,CUSTOMER_TYPE(TEMPORARY/PERMANENT),E-MAIL,FOLLOW-UP-DAYS(Number max:25),GST-NO(e.g. 33AABCU9603R1ZU),CLIENT-NAME,ORGANIZATION,PAN-NO(e.g. ABCDE1234F),PHONE-NUMBER (10 digit number starts with 6-9),UPDATED-BY}',
   '{ADDRESS,CUSTOMER_TYPE(TEMPORARY/PERMANENT),E-MAIL,FOLLOW-UP-DAYS(Number max:25),CLIENT-NAME,ORGANIZATION,PHONE-NUMBER (10 digit number starts with 6-9),UPDATED-BY}',
   't_customer',
   'CUSTOMER_IMPORT',
   '{E-MAIL,GST-NO(e.g. 33AABCU9603R1ZU),PAN-NO(e.g. ABCDE1234G),PHONE-NUMBER (10 digit number starts with 6-9)}'
)
on conflict (id) do update set
(
   header_fields,
   mandatory_fields,
   table_name,
   template_name,
   unique_fields
)
=
(
   excluded.header_fields,
   excluded.mandatory_fields,
   excluded.table_name,
   excluded.template_name,
   excluded.unique_fields
);
insert into t_report_configuration
(
   id,
   header_fields,
   mandatory_fields,
   table_name,
   template_name,
   unique_fields
)
values
(
   2,
   '{AADHAAR_NO(12 digit number),ADDRESS,DATE-OF-BIRTH(MM/DD/YYYY),DATE-OF-JOINING(MM/DD/YYYY),DESIGNATION,EMAIL,ESI-NO(17 digit number),NAME,PAN-NO(e.g. ABCDE1234F),PF-NO(e.g. TN MAS 1207199 123 1234567),PHONE-NUMBER(10 digit number starts with 6-9),UAN-NO(12 digit number),UPDATED-BY,DEPARTMENT-ID(e.g 1 or 2)}',
   '{AADHAAR_NO(12 digit number),ADDRESS,DATE-OF-BIRTH(MM/DD/YYYY),DATE-OF-JOINING(MM/DD/YYYY),DESIGNATION,NAME,PHONE-NUMBER(10 digit number starts with 6-9),UPDATED-BY,DEPARTMENT-ID(e.g 1 or 2)}',
   't_employee',
   'EMPLOYEE_IMPORT',
   '{AADHAAR_NO(12 digit number),EMAIL,ESI-NO(17 digit number),PAN-NO(e.g. ABCDE1234F),PF-NO(e.g. TN MAS 1207199 123 1234567),PHONE-NUMBER(10 digit number starts with 6-9),UAN-NO(12 digit number)}'
)
on conflict (id) do update set
(
   header_fields,
   mandatory_fields,
   table_name,
   template_name,
   unique_fields
)
=
(
   excluded.header_fields,
   excluded.mandatory_fields,
   excluded.table_name,
   excluded.template_name,
   excluded.unique_fields
);
insert into t_report_configuration
(
   id,
   header_fields,
   mandatory_fields,
   table_name,
   template_name,
   unique_fields
)
values
(
   3,
   '{AADHAAR_NO(12 digit number),ADDRESS,NAME,NOTES,PHONE-NUMBER(10 digit number starts with 6-9),UPDATED-BY,CONTRACTOR-ID(e.g 1 or 2)}',
   '{AADHAAR_NO(12 digit number),ADDRESS,NAME,PHONE-NUMBER(10 digit number starts with 6-9),UPDATED-BY,CONTRACTOR-ID(e.g 1 or 2)}',
   't_contract_employees',
   'CONTRACT_EMPLOYEES_IMPORT',
   '{AADHAAR_NO(12 digit number),PHONE-NUMBER(10 digit number starts with 6-9)}'
)
on conflict (id) do update set
(
   header_fields,
   mandatory_fields,
   table_name,
   template_name,
   unique_fields
)
=
(
   excluded.header_fields,
   excluded.mandatory_fields,
   excluded.table_name,
   excluded.template_name,
   excluded.unique_fields
);
insert into t_report_configuration
(
   id,
   header_fields,
   mandatory_fields,
   table_name,
   template_name,
   unique_fields
)
values
(
   4,
   '{HOURS-WORKED,HOURLY-PAY,WORK-DATE(YYYY/MM/DD),UPDATED-BY,EMPLOYEE-ID(e.g 1 or 2)}',
   '{HOURS-WORKED,HOURLY-PAY,WORK-DATE(YYYY/MM/DD),UPDATED-BY,EMPLOYEE-ID(e.g 1 or 2)}',
   't_employee_pay_hours',
   'EMPLOYEE_PAY_HOURS_IMPORT',
   '{EMPLOYEE-ID}'
)
on conflict (id) do update set
(
   header_fields,
   mandatory_fields,
   table_name,
   template_name,
   unique_fields
)
=
(
   excluded.header_fields,
   excluded.mandatory_fields,
   excluded.table_name,
   excluded.template_name,
   excluded.unique_fields
);
insert into t_report_configuration
(
   id,
   header_fields,
   mandatory_fields,
   table_name,
   template_name,
   unique_fields
)
values
(
   5,
   '{WORK-START-DATE(YYYY/MM/DD),WORK-END-DATE(YYYY/MM/DD),WEEKLY-WORKED-HOURS,HOURLY-PAY,WEEKLY-TOTAL-PAY,UPDATED-BY,EMPLOYEE-ID(e.g 1 or 2)}',
   '{WORK-START-DATE(YYYY/MM/DD),WORK-END-DATE(YYYY/MM/DD),WEEKLY-WORKED-HOURS,HOURLY-PAY,WEEKLY-TOTAL-PAY,UPDATED-BY,EMPLOYEE-ID(e.g 1 or 2)}',
   't_employee_weekly_wages',
   'EMPLOYEE_WEEKLY_WAGES_IMPORT',
   '{EMPLOYEE-ID}'
)
on conflict (id) do update set
(
   header_fields,
   mandatory_fields,
   table_name,
   template_name,
   unique_fields
)
=
(
   excluded.header_fields,
   excluded.mandatory_fields,
   excluded.table_name,
   excluded.template_name,
   excluded.unique_fields
);