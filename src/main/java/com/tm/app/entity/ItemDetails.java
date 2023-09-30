package com.tm.app.entity;

import lombok.Data;
@Data
public class ItemDetails {
	private ItemMaster itemMaster;
	
	private UnitOfMeasure unitOfMeasure;
	
	private Integer orderedQuantity;
}
