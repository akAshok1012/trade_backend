package com.tm.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.OrderSalesCountMasterDto;
import com.tm.app.dto.SalesOrderDto;
import com.tm.app.dto.SalesViewDto;
import com.tm.app.entity.SalesOrder;
import com.tm.app.enums.SalesStatus;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.security.annotations.IsSuperAdminOrAdminOrCustomer;
import com.tm.app.service.SalesOrderService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.Response;

@RestController
@CrossOrigin
public class SalesOrderController {

	@Autowired
	private SalesOrderService salesOrderService;

	@PostMapping("/sales-order")
	@IsSuperAdminOrAdmin
	public APIResponse<?> saveSalesOrder(@RequestBody SalesOrderDto salesOrderDto) {
		try {
			List<SalesOrder> salesOrders = salesOrderService.saveSalesOrder(salesOrderDto);
			return Response.getSuccessResponse(salesOrders, "SalesOrder Saved Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/sales-orders")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getSalesOrders() {
		try {
			List<SalesOrder> salesOrders = salesOrderService.getSalesOrders();
			return Response.getSuccessResponse(salesOrders, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/salesOrder/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getSalesOrderById(@PathVariable("id") Long id) {
		try {
			SalesOrder salesOrders = salesOrderService.getSalesOrderById(id);
			return Response.getSuccessResponse(salesOrders, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/salesOrder-Bycustomer")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getSalesOrderByCustomerName(@RequestParam("name") String name) {
		try {
			List<SalesOrder> orders = salesOrderService.getSalesOrderByCustomerName(name);
			return Response.getSuccessResponse(orders, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/salesOrder/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> updateSalesOrder(@PathVariable Long id, @RequestBody SalesOrderDto salesOrderDto) {
		try {
			SalesOrder salesOrders = salesOrderService.updateSalesOrder(id, salesOrderDto);
			return Response.getSuccessResponse(salesOrders, "SalesOrder Approved Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/salesOrder/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> deleteSalesOrderById(@PathVariable("id") Long id) {
		try {
			salesOrderService.deleteSalesOrderById(id);
			return Response.getSuccessResponse(null, "SalesOrder Deleted Successfully", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/salesorder-status")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getOrderStatus(@RequestParam("orderStatus") SalesStatus salesStatus) {
		try {
			List<SalesOrder> sales = salesOrderService.getOrderStatus(salesStatus);
			return Response.getSuccessResponse(sales, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/sales/{salesId}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getSalesBySalesId(@PathVariable("salesId") Integer salesId) {
		try {
			SalesOrder salesOrders = salesOrderService.getSalesBySalesId(salesId);
			return Response.getSuccessResponse(salesOrders, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/order-sales-master-count")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getOrderSalesMasterCount() {
		try {
			OrderSalesCountMasterDto salesOrders = salesOrderService.getOrderSalesMasterCount();
			return Response.getSuccessResponse(salesOrders, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/customer-sales-paymentStatus")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> getCustomerSalesPaymentStatus(@RequestParam(value = "id", required = false) Long id,
			@ModelAttribute DataFilter dataFilter) {
		try {
			Page<SalesViewDto> salesOrders = salesOrderService.getCustomerSalesPaymentStatus(id, dataFilter);
			return Response.getSuccessResponse(salesOrders, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/salesOrder-BySalesId")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> getSalesBySalesId(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "salesId", required = false) Integer salesId, @ModelAttribute DataFilter dataFilter) {
		try {
			Page<SalesViewDto> salesAndStatusDto = salesOrderService.getSalesBySalesId(id, salesId, dataFilter);
			return Response.getSuccessResponse(salesAndStatusDto, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}