package com.tm.app.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class UnitOfMeasureDto {

	private Long id;
	private String unitName;
	private String unitDescription;
	private Integer unitWeight;
	private Timestamp updatedAt;
	private String updatedBy;
	
}
