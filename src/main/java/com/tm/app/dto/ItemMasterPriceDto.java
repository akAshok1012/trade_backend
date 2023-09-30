package com.tm.app.dto;

import com.tm.app.entity.Brand;
import com.tm.app.entity.ItemCategory;

import jakarta.persistence.Transient;
import lombok.Data;

@Data
public class ItemMasterPriceDto {

	private Long id;

	private String itemName;

	private Float fixedPrice;

	private ItemCategory itemCategory;

	private Brand brand;

	private String itemDescription;

	private byte[] itemImage;

	private String[] unitOfMeasure;

	@Transient
	private String imageString;
}
