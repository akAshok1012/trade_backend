package com.tm.app.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.ItemMaster;
import com.tm.app.entity.Stock;
import com.tm.app.entity.UnitOfMeasure;

import jakarta.transaction.Transactional;

@Repository
public interface StockRepo extends JpaRepository<Stock, Long> {

	@Query("SELECT s FROM Stock s join ItemMaster im on(s.itemMaster=im.id) where LOWER(im.itemName) like LOWER(:search)")
	Page<Stock> findByItemName(String search, PageRequest of);

	Stock findByItemMasterAndUnitOfMeasure(ItemMaster itemMaster, UnitOfMeasure uom);

	@Modifying
	@Query(value = "DELETE FROM t_stock WHERE item_master = ?1", nativeQuery = true)
	@Transactional
	void deleteStockByItemMaster(Long id);

}
