package com.tm.app.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.tm.app.repo.EmployeeMonthlyPayRepo;

public class EmployeeMonthlyPayJob implements Job {

	@Autowired
	private EmployeeMonthlyPayRepo employeeMonthlyPayRepo;
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		employeeMonthlyPayRepo.truncateTable();
		employeeMonthlyPayRepo.getEmployeeMonthlyPay();
	}

}
