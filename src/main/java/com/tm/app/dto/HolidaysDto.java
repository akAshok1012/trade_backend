package com.tm.app.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class HolidaysDto {

	private String holidayName;
	private Date holidayDate;
	private String updatedBy;

}
