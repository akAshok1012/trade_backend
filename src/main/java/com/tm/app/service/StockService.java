package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.StockDto;
import com.tm.app.entity.Stock;

public interface StockService {

	public Stock createStock(StockDto stockDto);

	public List<Stock> getStock();

	public Stock getStockById(Long id);

	public Stock updateStock(Long id, StockDto stockDto);

	public void deleteStockById(Long id);

	public Page<Stock> getStockList(DataFilter dataFilter);

}
