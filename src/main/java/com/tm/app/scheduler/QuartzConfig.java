package com.tm.app.scheduler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Configuration
public class QuartzConfig {

	@Value("${credit.payment.tracker.crom.scheduler}")
	private String cronExpression;

	@Value("${monthly.pay.cron.scheduler}")
	private String monthlyPayExpression;

	@Value("${notification.cron.scheduler}")
	private String notificationExpression;

	/**
	 * Credit Payment Job
	 * 
	 * @return
	 */
	@Bean
	public JobDetailFactoryBean jobCreditPayment() {
		JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
		factoryBean.setJobClass(CreditPaymentJob.class);
		factoryBean.setDurability(true);
		return factoryBean;
	}

	/**
	 * Employee Monthly Pay Job
	 * 
	 * @return
	 */
	@Bean
	public JobDetailFactoryBean jobEmployeeMonthlyPay() {
		JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
		factoryBean.setJobClass(EmployeeMonthlyPayJob.class);
		factoryBean.setDurability(true);
		return factoryBean;
	}

	/**
	 * Notification Job
	 * 
	 * @return
	 */
	@Bean
	public JobDetailFactoryBean jobNotification() {
		JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
		factoryBean.setJobClass(NotificationJob.class);
		factoryBean.setDurability(true);
		return factoryBean;
	}

	/**
	 * Credit Payment Trigger
	 * 
	 * @return
	 */
	@Bean
	public CronTriggerFactoryBean triggerCreditPayment() {
		CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
		factoryBean.setJobDetail(jobCreditPayment().getObject());
		factoryBean.setCronExpression(cronExpression);
		return factoryBean;
	}

	/**
	 * Employee Monthly Pay Trigger
	 * 
	 * @return
	 */
	@Bean
	public CronTriggerFactoryBean triggerEmployeeMonthlyPay() {
		CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
		factoryBean.setJobDetail(jobEmployeeMonthlyPay().getObject());
		factoryBean.setCronExpression(monthlyPayExpression);
		return factoryBean;
	}

	/**
	 * Notification Trigger
	 * 
	 * @return
	 */
	@Bean
	public CronTriggerFactoryBean triggerNotification() {
		CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
		factoryBean.setJobDetail(jobNotification().getObject());
		factoryBean.setCronExpression(notificationExpression);
		return factoryBean;
	}

	/**
	 * \ Contract status
	 * 
	 * @return
	 */
	@Bean
	public JobDetailFactoryBean createContractStatusJobFactoryBean() {
		JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
		jobDetailFactory.setJobClass(ContractStatusJob.class);
		jobDetailFactory.setDurability(true);
		return jobDetailFactory;
	}

	/**
	 * 
	 * @param jobDetail
	 * @return
	 */
	@Bean
	public CronTriggerFactoryBean createContractStatusCronTrigger() {
		CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
		factoryBean.setCronExpression(cronExpression);
		factoryBean.setJobDetail(createContractStatusJobFactoryBean().getObject());
		return factoryBean;
	}
}
