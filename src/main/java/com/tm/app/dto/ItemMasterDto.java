package com.tm.app.dto;

import java.util.List;

import com.tm.app.entity.Brand;
import com.tm.app.entity.ItemCategory;
import com.tm.app.entity.UnitOfMeasure;

import lombok.Data;

@Data
public class ItemMasterDto {

	private Long id;
	private String itemName;
	private String itemDescription;
	private ItemCategory itemCategory;
	private List<UnitOfMeasure> unitOfMeasures;
	private Float fixedPrice;
	private String itemImage;
	private Brand brand;
	private String updatedBy;

}
