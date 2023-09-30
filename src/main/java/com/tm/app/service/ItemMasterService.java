package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.ItemMasterDto;
import com.tm.app.dto.ItemMasterIdAndPriceDto;
import com.tm.app.dto.ItemMasterPriceDto;
import com.tm.app.entity.ItemMaster;

public interface ItemMasterService {

	public List<ItemMaster> getItemMasters();

	public ItemMaster saveItemMaster(ItemMasterDto itemMasterDto);

	public ItemMaster getItemMasterById(Long id);

	public void deleteItemMasterById(Long id);

	public ItemMaster updateItemMaster(Long id, ItemMasterDto itemMasterDto);

	public List<ItemMasterPriceDto> getItemMasterPriceData();

	public List<ItemMasterIdAndPriceDto> getItemMasterIdAndPrice();

	public List<ItemMaster> getItemBrands(Long brandId);

	public Page<ItemMasterPriceDto> getItemDetailsBySearch(DataFilter dataFilter);

	public Page<ItemMaster> getItemMasterList(DataFilter dataFilter);

}
