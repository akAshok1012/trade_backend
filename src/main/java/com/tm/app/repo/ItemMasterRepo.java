package com.tm.app.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.dto.ItemMasterIdAndPriceDto;
import com.tm.app.dto.ItemMasterListDto;
import com.tm.app.dto.ItemMasterPriceDto;
import com.tm.app.entity.Brand;
import com.tm.app.entity.ItemCategory;
import com.tm.app.entity.ItemMaster;

@Repository
public interface ItemMasterRepo extends JpaRepository<ItemMaster, Long> {

	@Query("SELECT new com.tm.app.dto.ItemMasterPriceDto(im.id as id, im.itemName as itemName, im.fixedPrice as fixedPrice, im.itemCategory as itemCategory, im.brand as brand, im.itemDescription as itemDescription, im.itemImage as itemImage) FROM ItemMaster im join ItemCategory ic on (ic.id = im.itemCategory.id) join Brand b on (b.id = im.brand.id)")
	List<ItemMasterPriceDto> getItemMasterPriceData();

	@Query("SELECT new com.tm.app.dto.ItemMasterIdAndPriceDto(im.id as id,im.fixedPrice as fixedPrice) FROM ItemMaster im")
	List<ItemMasterIdAndPriceDto> getItemMasterIdAndPrice();

	List<ItemMaster> findByBrand(Brand brand);

	@Query("SELECT new com.tm.app.dto.ItemMasterListDto(im.id as id,im.itemName as itemName,im.itemDescription as itemDescription,ic.categoryName as categoryName,b.name as brandName,im.fixedPrice as fixedPrice) FROM ItemMaster im join ItemCategory ic on (ic.id = im.itemCategory.id) join Brand b on (b.id = im.brand.id)")
	List<ItemMasterListDto> getAllItemDetails();

	@Query("SELECT new com.tm.app.dto.ItemMasterPriceDto(im.id as id, im.itemName as itemName, im.fixedPrice as fixedPrice, im.itemCategory as itemCategory, im.brand as brand, im.itemDescription as itemDescription, im.itemImage as itemImage, im.unitOfMeasures as unitOfMeasures) FROM ItemMaster im join ItemCategory ic on (ic.id = im.itemCategory.id) join Brand b on (b.id = im.brand.id) where LOWER(im.itemName) like LOWER(:search) OR LOWER(im.itemDescription) like LOWER(:search) OR LOWER(b.name) like LOWER(:search)")
	Page<ItemMasterPriceDto> getItemDetailsBySearch(PageRequest pageRequest, String search);

	@Query("SELECT im FROM ItemMaster im join ItemCategory ic on (ic.id = im.itemCategory) join Brand b on (b.id = im.brand) where LOWER(im.itemName) like LOWER(:search) OR LOWER(b.name) like LOWER(:search)")
	Page<ItemMaster> getItemMasterListDto(String search, PageRequest of);

	boolean existsByItemNameIgnoreCaseAndItemCategoryAndBrand(String itemName, ItemCategory itemCategory, Brand brand);

}
