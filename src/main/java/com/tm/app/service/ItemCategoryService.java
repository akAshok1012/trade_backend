package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.ItemCategoryDto;
import com.tm.app.entity.ItemCategory;

public interface ItemCategoryService {

	public List<ItemCategory> getItemCategories();

	public ItemCategory saveItemCategory(ItemCategoryDto itemCategoryDto);

	public ItemCategory getItemCategoryById(Long id);

	public void deleteItemCategoryById(Long id);

	public ItemCategory updateItemCategory(Long id, ItemCategoryDto itemCategoryDto);

	public Page<ItemCategory> getItemCategoryList(DataFilter dataFilterO);

}
