package com.tm.app.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.StockDto;
import com.tm.app.entity.Stock;
import com.tm.app.repo.StockRepo;
import com.tm.app.service.StockService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class StockServiceImpl implements StockService {

	@Autowired
	private StockRepo stockRepo;

	@Override
	public Stock createStock(StockDto stockDto) {
		Stock stock = new Stock();
		try {
			log.info("[StockServiceImpl] Stock Service Started");
			BeanUtils.copyProperties(stockDto, stock);
			stock = stockRepo.save(stock);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("[StockServiceImpl] Adding Stock Service Failed");
			throw new RuntimeException(e.getMessage());
		}
		return stock;
	}

	@Override
	public List<Stock> getStock() {
		return stockRepo.findAll();
	}

	@Override
	public Stock getStockById(Long id) {
		return stockRepo.findById(id).orElseThrow();
	}

	@Override
	public Stock updateStock(Long id, StockDto stockDto) {
		Stock stock = new Stock();
		try {
			log.info("[StockServiceImpl] Stock Service Started");
			BeanUtils.copyProperties(stockDto, stock);
			stock = stockRepo.save(stock);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("[StockServiceImpl] Updating Stock Service Failed");
			throw new RuntimeException(e.getMessage());
		}
		return stock;
	}

	@Override
	public void deleteStockById(Long id) {
		stockRepo.deleteById(id);
	}

	@Override
	public Page<Stock> getStockList(DataFilter dataFilter) {
		return stockRepo.findByItemName(dataFilter.getSearch(), PageRequest.of(dataFilter.getPage(),
				dataFilter.getSize(), Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}
}
