package com.tm.app.dto;

import com.tm.app.entity.ItemMaster;
import com.tm.app.entity.UnitOfMeasure;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateStockDto {

	private ItemMaster itemMasterId;
	private UnitOfMeasure unitOfMeasureId;
}
