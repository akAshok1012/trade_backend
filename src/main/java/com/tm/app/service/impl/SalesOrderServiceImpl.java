package com.tm.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.OrderSalesCountDto;
import com.tm.app.dto.OrderSalesCountMasterDto;
import com.tm.app.dto.SalesOrderDto;
import com.tm.app.dto.SalesViewDto;
import com.tm.app.entity.Customer;
import com.tm.app.entity.ItemDetails;
import com.tm.app.entity.SalesOrder;
import com.tm.app.entity.User;
import com.tm.app.enums.SalesStatus;
import com.tm.app.repo.CustomerRepo;
import com.tm.app.repo.SalesOrderRepo;
import com.tm.app.repo.UserRepository;
import com.tm.app.service.SalesOrderService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SalesOrderServiceImpl implements SalesOrderService {

	@Autowired
	private SalesOrderRepo salesOrderRepo;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public List<SalesOrder> saveSalesOrder(SalesOrderDto salesOrderDto) {
		List<SalesOrder> salesOrderList = new ArrayList<SalesOrder>();
		SalesOrder salesOrder = new SalesOrder();
		try {
			Integer orderNumber = RandomUtils.nextInt(100000, 999999);
			for (ItemDetails salOrders : salesOrderDto.getItemDetails()) {
				salesOrder.setSalesId(orderNumber);
				salesOrder.setCustomer(salesOrderDto.getCustomer());
				salesOrder.setDeliveryDate(salesOrderDto.getDeliveryDate());
				salesOrder.setUpdatedBy(salesOrderDto.getUpdatedBy());
				salesOrder.setIsReturn(salesOrderDto.getIsReturn());
				salesOrder.setSalesStatus(salesOrderDto.getSalesStatus());
				salesOrderList.add(salesOrder);

			}
			salesOrderList = salesOrderRepo.saveAll(salesOrderList);
		} catch (Exception e) {
			log.error("[SALES] adding salesOrder failed", e);
			throw new RuntimeException("Adding salesOrder failed");
		}
		return salesOrderList;
	}

	@Override
	public List<SalesOrder> getSalesOrders() {
		return salesOrderRepo.findAll();
	}

	@Override
	public SalesOrder getSalesOrderById(Long id) {
		return salesOrderRepo.findById(id).orElseThrow();
	}

	@Override
	@Transactional
	public SalesOrder updateSalesOrder(Long id, SalesOrderDto salesOrderDto) {
		SalesOrder salesOrder = salesOrderRepo.findById(id).orElseThrow();
		try {
			salesOrder.setDeliveryDate(salesOrderDto.getDeliveryDate());
			salesOrder.setUpdatedBy(salesOrderDto.getUpdatedBy());
			salesOrder = salesOrderRepo.save(salesOrder);
		} catch (Exception e) {
			log.error("[SALES] updating sales order failed", e);
			throw new RuntimeException("Updating sales order failed");
		}
		return salesOrder;
	}

	@Override
	@Transactional
	public void deleteSalesOrderById(Long id) {
		try {
			salesOrderRepo.deleteById(id);
		} catch (Exception e) {
			log.error("[SALES] deleting sales order failed", e);
			throw new RuntimeException("Deleting sales order failed");
		}
	}

	@Override
	public List<SalesOrder> getSalesOrderByCustomerName(String name) {
		Customer customer = customerRepo.findByName(name);
		return salesOrderRepo.findByCustomerId(customer.getId());
	}

	@Override
	public List<SalesOrder> getOrderStatus(SalesStatus salesStatus) {
		return salesOrderRepo.findBySalesStatus(salesStatus);
	}

	@Override
	public SalesOrder getSalesBySalesId(Integer salesId) {
		return salesOrderRepo.findBySalesId(salesId);
	}

	@Override
	public OrderSalesCountMasterDto getOrderSalesMasterCount() {
		Integer orderDailyCount = salesOrderRepo.getOrderDailyCount();
		Integer orderMonthlyCount = salesOrderRepo.getOrderMonthlyCount();
		Integer orderWeeklyCount = salesOrderRepo.getOrderWeeklyCount();
		Integer salesDailyCount = salesOrderRepo.getSalesDailyCount();
		Integer salesMonthlyCount = salesOrderRepo.getSalesMonthlyCount();
		Integer salesWeeklyCount = salesOrderRepo.getSalesWeeklyCount();
		OrderSalesCountDto dailyCountDto = new OrderSalesCountDto();
		dailyCountDto.setOrderCount(orderDailyCount);
		dailyCountDto.setSalesCount(salesDailyCount);
		OrderSalesCountDto weeklyCountDto = new OrderSalesCountDto();
		weeklyCountDto.setOrderCount(orderWeeklyCount);
		weeklyCountDto.setSalesCount(salesWeeklyCount);
		OrderSalesCountDto monthlyCountDto = new OrderSalesCountDto();
		monthlyCountDto.setOrderCount(orderMonthlyCount);
		monthlyCountDto.setSalesCount(salesMonthlyCount);
		OrderSalesCountMasterDto masterDto = new OrderSalesCountMasterDto();
		masterDto.setDailyCount(dailyCountDto);
		masterDto.setMonthlyCount(monthlyCountDto);
		masterDto.setWeeklyCount(weeklyCountDto);
		return masterDto;
	}

	@Override
	public Page<SalesViewDto> getCustomerSalesPaymentStatus(Long id, DataFilter dataFilter) {
		if (id != null) {
			User user = userRepository.findById(id).orElseThrow();
			Customer customer = customerRepo.findById(user.getUserId()).orElseThrow();
			return salesOrderRepo.getCustomerSalesPaymentStatusByCustomer(customer, dataFilter.getSearch(),
					PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
							Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
		} else {
			return salesOrderRepo.getCustomerSalesPaymentStatus(dataFilter.getSearch(),
					PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
							Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
		}
	}

	@Override
	public Page<SalesViewDto> getSalesBySalesId(Long id, Integer salesId, DataFilter dataFilter) {
		String salesIdString = "%";
		if (id != null) {
			User user = userRepository.findById(id).orElseThrow();
			Customer customer = customerRepo.findById(user.getUserId()).orElseThrow();
			if (Objects.nonNull(salesId)) {
				salesIdString = "%" + salesId.toString() + "%";
				return salesOrderRepo.getCustomerSalesPaymentStatusByCustomerAndSalesId(customer, salesIdString,
						dataFilter.getSearch(), PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
								Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
			}
			return salesOrderRepo.getCustomerSalesPaymentStatusByCustomerAndSalesId(customer, salesIdString,
					dataFilter.getSearch(), PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
							Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
		} else {
			if (Objects.nonNull(salesId)) {
				salesIdString = "%" + salesId.toString() + "%";
				return salesOrderRepo.getSalesBySalesId(salesIdString, PageRequest.of(dataFilter.getPage(),
						dataFilter.getSize(), Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
			}
			return salesOrderRepo.getSalesBySalesId(salesIdString, PageRequest.of(dataFilter.getPage(),
					dataFilter.getSize(), Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
		}
	}
}