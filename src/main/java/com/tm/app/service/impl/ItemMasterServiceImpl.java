package com.tm.app.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.ItemMasterDto;
import com.tm.app.dto.ItemMasterIdAndPriceDto;
import com.tm.app.dto.ItemMasterPriceDto;
import com.tm.app.entity.Brand;
import com.tm.app.entity.ItemMaster;
import com.tm.app.entity.Notification;
import com.tm.app.entity.UnitOfMeasure;
import com.tm.app.enums.NotificationStatus;
import com.tm.app.repo.BrandRepo;
import com.tm.app.repo.ItemMasterRepo;
import com.tm.app.repo.NotificationRepo;
import com.tm.app.service.ItemMasterService;
import com.tm.app.utils.APIResponseConstants;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class ItemMasterServiceImpl implements ItemMasterService {

	@Autowired
	private ItemMasterRepo itemMasterRepo;

	@Autowired
	private BrandRepo brandRepo;

	@Autowired
	private NotificationRepo notificationRepo;

	@Override
	@Cacheable(value = "itemMaster")
	public List<ItemMaster> getItemMasters() {
		log.info("Get Item Master");
		List<ItemMaster> itemMasters = itemMasterRepo.findAll();
		itemMasters.stream().forEach(r -> {
			if (Objects.nonNull(r.getItemImage())) {
				r.setItemImageString(new String(r.getItemImage()));
			}
		});
		return itemMasters;
	}

	@Override
	@CacheEvict(value = "itemMaster", allEntries = true)
	public ItemMaster saveItemMaster(ItemMasterDto itemMasterDto) {
		ItemMaster itemMaster = new ItemMaster();
		if (itemMasterRepo.existsByItemNameIgnoreCaseAndItemCategoryAndBrand(itemMasterDto.getItemName(),
				itemMasterDto.getItemCategory(), itemMasterDto.getBrand())) {
			throw new RuntimeException(
					String.format(APIResponseConstants.ITEM_DETAILS_ALREADY_EXISTS, itemMasterDto.getItemName(),
							itemMasterDto.getItemCategory().getCategoryName(), itemMasterDto.getBrand().getName()));
		}
		BeanUtils.copyProperties(itemMasterDto, itemMaster);
		if (StringUtils.isNotEmpty(itemMasterDto.getItemImage())) {
			itemMaster.setItemImage(itemMasterDto.getItemImage().getBytes());
		}
		return itemMasterRepo.save(itemMaster);
	}

	@Override
	@Cacheable(value = "itemMaster", key = "#id")
	public ItemMaster getItemMasterById(Long id) {
		log.info("Get Item Master By Id");
		ItemMaster itemMasterData = itemMasterRepo.findById(id).orElseThrow();
		if (Objects.nonNull(itemMasterData.getItemImage())) {
			itemMasterData.setItemImageString(new String(itemMasterData.getItemImage()));
		}
		return itemMasterData;
	}

	@Override
	@Transactional
	@CacheEvict(cacheNames = "itemMaster", key = "#id", allEntries = true)
	public void deleteItemMasterById(Long id) {
		log.info("Deleted Item Master");
		try {
			itemMasterRepo.deleteById(id);
		} catch (Exception e) {
			log.error("[ITEM] deleting itemMaster failed", e);
			throw new RuntimeException("Deleting itemMaster failed");
		}
	}

	@Override
	@Transactional
	@CacheEvict(value = "itemMaster", allEntries = true)
	public ItemMaster updateItemMaster(Long id, ItemMasterDto itemMasterDto) {
		log.info("Update Item Master");
		ItemMaster itemMaster = itemMasterRepo.findById(id).orElseThrow();
		if (itemMasterRepo.existsByItemNameIgnoreCaseAndItemCategoryAndBrand(itemMasterDto.getItemName(),
				itemMasterDto.getItemCategory(), itemMasterDto.getBrand())
				&& !(itemMaster.getItemName().equalsIgnoreCase(itemMasterDto.getItemName())
						&& itemMaster.getItemCategory().equals(itemMasterDto.getItemCategory())
						&& itemMaster.getBrand().equals(itemMasterDto.getBrand()))) {
			throw new RuntimeException(
					String.format(APIResponseConstants.ITEM_DETAILS_ALREADY_EXISTS, itemMasterDto.getItemName(),
							itemMasterDto.getItemCategory().getCategoryName(), itemMasterDto.getBrand().getName()));
		}
		itemMaster.setItemName(itemMasterDto.getItemName());
		itemMaster.setUnitOfMeasures(itemMasterDto.getUnitOfMeasures());
		itemMaster.setFixedPrice(itemMasterDto.getFixedPrice());
		itemMaster.setItemDescription(itemMasterDto.getItemDescription());
		itemMaster.setItemCategory(itemMasterDto.getItemCategory());
		itemMaster.setBrand(itemMasterDto.getBrand());
		if (StringUtils.isNotEmpty(itemMasterDto.getItemImage())) {
			itemMaster.setItemImage(itemMasterDto.getItemImage().getBytes());
		}
		itemMaster = itemMasterRepo.save(itemMaster);

		// insert in Notification table
		updateItemPriceNotification(itemMaster);
		return itemMaster;
	}

	/**
	 * Insert in Notification Table
	 * 
	 * @param itemMaster
	 */
	private void updateItemPriceNotification(ItemMaster itemMaster) {
		Notification notification = new Notification();
		notification.setMessage("Great news! We have updated our Product Price, Effective from "
				+ itemMaster.getUpdatedAt() + "." + itemMaster.getItemName() + ": " + itemMaster.getFixedPrice()
				+ ". Thank you for your continued support. Thanks, [KPR & Team] ");
		notification.setTitle("Item Price Changes");
		notification.setUpdatedBy(itemMaster.getUpdatedBy());
		try {
			notification.setIsAllCustomers(true);
			notification.setNotificationStatus(NotificationStatus.PENDING);
			notification.setIsSend(true);
		} catch (Exception e) {
			notification.setIsAllCustomers(false);
			notification.setNotificationStatus(NotificationStatus.FAILED);
			notification.setIsSend(true);
		}
		notification = notificationRepo.save(notification);
	}

	@Override
	@Cacheable(value = "itemMaster")
	public List<ItemMasterPriceDto> getItemMasterPriceData() {
		log.info("Get Item Master Price Data");
		List<ItemMasterPriceDto> itemMasterPrice = itemMasterRepo.getItemMasterPriceData();

		itemMasterPrice.stream().forEach(itemMasterPriceDto -> {
			if (Objects.nonNull(itemMasterPriceDto.getItemImage())) {
				itemMasterPriceDto.setImageString(new String(itemMasterPriceDto.getItemImage()));
			}
		});

		return itemMasterPrice;
	}

	@Override
	public List<ItemMasterIdAndPriceDto> getItemMasterIdAndPrice() {
		log.info("Get Item Master Price Data");
		return itemMasterRepo.getItemMasterIdAndPrice();
	}

	@Override
	public List<ItemMaster> getItemBrands(Long brandId) {
		log.info("Get Item Brands");
		Brand brand = brandRepo.findById(brandId).orElseThrow();
		List<ItemMaster> itemMasterList = itemMasterRepo.findByBrand(brand);
		for (ItemMaster itemMaster : itemMasterList) {
			if (Objects.nonNull(itemMaster.getItemImage())) {
				itemMaster.setItemImageString(new String(itemMaster.getItemImage()));
			}
		}
		return itemMasterList;
	}

	@Override
	public Page<ItemMasterPriceDto> getItemDetailsBySearch(DataFilter dataFilter) {
		log.info("Get Item Details By Search");
		Page<ItemMasterPriceDto> itemMasterPrice = itemMasterRepo
				.getItemDetailsBySearch(PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
						Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())), dataFilter.getSearch());
		for (ItemMasterPriceDto itemMasterPriceDto : itemMasterPrice) {
			if (Objects.nonNull(itemMasterPriceDto.getItemImage())) {
				itemMasterPriceDto.setImageString(new String(itemMasterPriceDto.getItemImage()));
			}
		}
		return itemMasterPrice;
	}

	@Override
	@CacheEvict(value = "itemMaster", allEntries = true)
	public Page<ItemMaster> getItemMasterList(DataFilter dataFilter) {
		log.info("Get Item Master List");
		Page<ItemMaster> itemMasterListDto = itemMasterRepo.getItemMasterListDto(dataFilter.getSearch(),
				PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
						Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
		itemMasterListDto.getContent().stream().forEach(r -> {
			r.setUnitMeasureString(
					r.getUnitOfMeasures().stream().map(UnitOfMeasure::getUnitName).collect(Collectors.joining(",")));
			if (Objects.nonNull(r.getItemImage())) {
				r.setItemImageString(new String(r.getItemImage()));
			}
		});
		return itemMasterListDto;
	}
}