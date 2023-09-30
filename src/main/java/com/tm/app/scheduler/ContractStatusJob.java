package com.tm.app.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.tm.app.service.ContractDetailsService;

public class ContractStatusJob implements Job {
	
	@Autowired
	private ContractDetailsService contractDetailsService;

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		contractDetailsService.updateContractStatusJob();
	}

}
