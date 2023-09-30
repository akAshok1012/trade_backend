package com.tm.app.dto;

import lombok.Data;

@Data
public class BrandDto {

	private Long id;
	private String name;
	private String description;
	private String logoString;
	private String updatedBy;

}
