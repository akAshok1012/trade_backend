package com.tm.app.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ItemCategoryDto {

	private Long id;
	private String categoryName;
	private String categoryDescription;
	private Timestamp updatedAt;
	private String updatedBy;
}
