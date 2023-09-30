package com.tm.app.dto;

import lombok.Data;

@Data
public class LeadNotificationDto {

	private Long id;
	private Integer followUps;
	private String name;
	private String days;

}
