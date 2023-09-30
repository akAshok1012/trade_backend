package com.tm.app.dto;

import lombok.Data;

@Data
public class ItemMasterListDto {
	private Long id;
	private String itemName;
	private String itemDescription;
	private String itemCategory;
	private String brand;
	private Float fixedPrice;
	private String[] unitOfMeasure;
}
