package com.tm.app.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.tm.app.repo.CreditPaymentTrackRepo;

public class CreditPaymentJob implements Job {

	@Autowired
	private CreditPaymentTrackRepo creditTrackRepo;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		creditTrackRepo.truncateTable();
		creditTrackRepo.saveCreditPaymentTrack();

	}

}
