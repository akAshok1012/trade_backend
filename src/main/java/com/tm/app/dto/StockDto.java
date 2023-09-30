package com.tm.app.dto;

import com.tm.app.entity.ItemMaster;
import com.tm.app.entity.UnitOfMeasure;

import lombok.Data;

@Data
public class StockDto {

	private Long id;
	private ItemMaster itemMaster;
	private UnitOfMeasure uom;
	private Integer inStock;
	private Integer wastageStock;
	private String updatedBy;
}
