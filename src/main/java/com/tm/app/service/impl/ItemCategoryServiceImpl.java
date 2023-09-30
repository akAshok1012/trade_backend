package com.tm.app.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.ItemCategoryDto;
import com.tm.app.entity.ItemCategory;
import com.tm.app.repo.ItemCategoryRepo;
import com.tm.app.service.ItemCategoryService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ItemCategoryServiceImpl implements ItemCategoryService {

	@Autowired
	private ItemCategoryRepo itemCategoryRepo;

	@Override
	@Transactional
	@CacheEvict(value="itemCategory",allEntries=true)
	public ItemCategory saveItemCategory(ItemCategoryDto itemCategoryDto) {
		log.info("[ItemCategory] Created itemCategory");
		ItemCategory itemCategory = new ItemCategory();
		try {
			BeanUtils.copyProperties(itemCategoryDto, itemCategory);
			itemCategory = itemCategoryRepo.save(itemCategory);
		} catch (Exception e) {
			log.error("[ITEM] adding itemCategory failed", e);
			throw new RuntimeException("Adding itemCategory failed");
		}
		return itemCategory;
	}

	@Override
	@Cacheable(value ="itemCategory")
	public List<ItemCategory> getItemCategories() {
		log.info("[ItemCategory] Get itemCategory");
		return itemCategoryRepo.findAll();
	}

	@Override
	@Cacheable(value = "itemCategory" , key = "#id")
	public ItemCategory getItemCategoryById(Long id) {
		log.info("[ItemCategory] Get itemCategoryById");
		return itemCategoryRepo.findById(id).orElseThrow();
	}

	@Override
	@Transactional
	@CacheEvict(cacheNames = "itemCategory", key = "#id",allEntries=true)
	public void deleteItemCategoryById(Long id) {
		log.info("[ItemCategory] Deleted itemCategory");
		try {
			itemCategoryRepo.deleteById(id);
		} catch (Exception e) {
			log.error("[ITEM] deleting itemCategory failed", e);
			throw new RuntimeException("Deleting itemCategory failed");
		}

	}

	@Override
	@Transactional
	@CacheEvict(value="itemCategory",allEntries=true)
	public ItemCategory updateItemCategory(Long id, ItemCategoryDto itemCategoryDto) {
		log.info("[ItemCategory] Updated itemCategory");
		ItemCategory itemCategory = itemCategoryRepo.findById(id).orElseThrow();
		try {
			itemCategory.setCategoryName(itemCategoryDto.getCategoryName());
			itemCategory.setCategoryDescription(itemCategoryDto.getCategoryDescription());
			itemCategory.setUpdatedBy(itemCategoryDto.getUpdatedBy());
			itemCategory = itemCategoryRepo.save(itemCategory);
		} catch (Exception e) {
			log.error("[ITEM] updating itemCategory failed", e);
			throw new RuntimeException("Updating itemCategory failed");
		}
		return itemCategory;
	}

	@Override
	@Cacheable(value ="itemCategory")
	public Page<ItemCategory> getItemCategoryList(DataFilter dataFilter) {
		log.info("[ItemCategory] Get itemCategoryList");
		return itemCategoryRepo.findByCategoryNameLikeIgnoreCase(dataFilter.getSearch(),
				PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
						Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}
}