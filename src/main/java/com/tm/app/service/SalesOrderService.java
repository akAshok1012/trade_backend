package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.OrderSalesCountMasterDto;
import com.tm.app.dto.SalesOrderDto;
import com.tm.app.dto.SalesViewDto;
import com.tm.app.entity.SalesOrder;
import com.tm.app.enums.SalesStatus;

public interface SalesOrderService {

	public List<SalesOrder> saveSalesOrder(SalesOrderDto salesOrderDto);

	public List<SalesOrder> getSalesOrders();

	public SalesOrder getSalesOrderById(Long id);

	public SalesOrder updateSalesOrder(Long id, SalesOrderDto salesOrderDto);

	public void deleteSalesOrderById(Long id);

	List<SalesOrder> getSalesOrderByCustomerName(String name);

	List<SalesOrder> getOrderStatus(SalesStatus salesStatus);

	public SalesOrder getSalesBySalesId(Integer salesId);

	public OrderSalesCountMasterDto getOrderSalesMasterCount();

	public Page<SalesViewDto> getCustomerSalesPaymentStatus(Long id, DataFilter dataFilter);

	public Page<SalesViewDto> getSalesBySalesId(Long id, Integer salesId, DataFilter dataFilter);

}