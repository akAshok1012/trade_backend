package com.tm.app;

import org.quartz.SchedulerException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class XyloTradeManagerApplication {

	public static void main(String[] args) throws SchedulerException {
		SpringApplication.run(XyloTradeManagerApplication.class, args);
	}
}
