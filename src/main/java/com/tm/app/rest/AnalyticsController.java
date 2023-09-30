package com.tm.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tm.app.dto.DataFilter;
import com.tm.app.entity.CustomerPaymentSummary;
import com.tm.app.entity.CustomerSalesAndOrderAndPaymentSummary;
import com.tm.app.entity.CustomerSalesAndOrderSummary;
import com.tm.app.entity.DailyLeadGeneration;
import com.tm.app.entity.DailyPaymentSummary;
import com.tm.app.entity.DailySalesAndOrdersAndPaymentCount;
import com.tm.app.entity.LeadCountAndCreatedBy;
import com.tm.app.entity.LeadCountWithStatus;
import com.tm.app.entity.MonthlyPaymentSummary;
import com.tm.app.entity.MonthlySalesAndOrdersAndPaymentCount;
import com.tm.app.entity.OrdersPerCustomer;
import com.tm.app.entity.SalesAndOrderCount;
import com.tm.app.entity.SalesPerCustomer;
import com.tm.app.entity.TotalPaymentSummary;
import com.tm.app.entity.WeeklyLeadGeneration;
import com.tm.app.entity.WeeklySalesAndOrdersAndPaymentCount;
import com.tm.app.entity.YearlyLeadGeneration;
import com.tm.app.security.annotations.IsSuperAdmin;
import com.tm.app.security.annotations.IsSuperAdminOrAdminOrCustomer;
import com.tm.app.service.AnalyticsService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.APIResponseConstants;
import com.tm.app.utils.Response;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AnalyticsController {

	@Autowired
	private AnalyticsService dashboardService;

	@GetMapping("/customer-daily-payment-summary")
	@IsSuperAdmin
	public APIResponse<?> getCustomerDailyPaymentSummary() {
		try {
			List<DailyPaymentSummary> paymentsummary = dashboardService.getDailyPaymentSummary();
			return Response.getSuccessResponse(paymentsummary, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/customer-monthly-payment-summary")
	@IsSuperAdmin
	public APIResponse<?> getCustomerMonthlyPaymentSummary() {
		try {
			List<MonthlyPaymentSummary> monthlypayment = dashboardService.getMonthlyPaymentSummary();
			return Response.getSuccessResponse(monthlypayment, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/customer-payment-summary")
	@IsSuperAdmin
	public APIResponse<?> getCustomerPaymentSummary(@RequestParam(value = "id", required = false) Long id) {
		try {
			List<CustomerPaymentSummary> paymentSummary = dashboardService.getCustomerPaymentSummary(id);
			return Response.getSuccessResponse(paymentSummary, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/customer-daily-sales-orders-count")
	@IsSuperAdmin
	public APIResponse<?> getCustomerDailySalesAndOrdersCount() {
		try {
			List<SalesAndOrderCount> salesOrdersCount = dashboardService.getSalesAndOrdersCount();
			return Response.getSuccessResponse(salesOrdersCount, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/customer-sales-orders-count")
	@IsSuperAdmin
	public APIResponse<?> getCustomerSalesAndOdersCount(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<CustomerSalesAndOrderSummary> salesOrderSummary = dashboardService
					.getCustomerSalesAndOdersCount(dataFilter);
			return Response.getSuccessResponse(salesOrderSummary, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/total-payment-summary")
	@IsSuperAdmin
	public APIResponse<?> getTotalPaymentSummary() {
		try {
			List<TotalPaymentSummary> totalPaymentSummaries = dashboardService.getTotalPaymentSummary();
			return Response.getSuccessResponse(totalPaymentSummaries, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/sales-per-customer")
	@IsSuperAdmin
	public APIResponse<?> getSalesPerCustomer(@RequestParam("year") Integer year) {
		try {
			List<SalesPerCustomer> salesPerCustomers = dashboardService.getSalesPerCustomer(year);
			return Response.getSuccessResponse(salesPerCustomers, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/orders-per-customer")
	@IsSuperAdmin
	public APIResponse<?> getOrdersPerCustomer(@RequestParam("year") Integer year) {
		try {
			List<OrdersPerCustomer> ordersPerCustomers = dashboardService.getOrdersPerCustomer(year);
			return Response.getSuccessResponse(ordersPerCustomers, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/lead-daily-generation-count")
	public APIResponse<?> getDailyLeadGeneration() {
		try {
			List<DailyLeadGeneration> dailyLeadGenerations = dashboardService.getDailyLeadGeneration();
			return Response.getSuccessResponse(dailyLeadGenerations, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/lead-weekly-generation-count")
	@IsSuperAdmin
	public APIResponse<?> getWeeklyLeadGeneration() {
		try {
			List<WeeklyLeadGeneration> weeklyLeadGenerations = dashboardService.getWeeklyLeadGeneration();
			return Response.getSuccessResponse(weeklyLeadGenerations, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/lead-yearly-generation-count")
	@IsSuperAdmin
	public APIResponse<?> getYearlyLeadGeneration() {
		try {
			List<YearlyLeadGeneration> yearlyLeadGenerations = dashboardService.getYearlyLeadGeneration();
			return Response.getSuccessResponse(yearlyLeadGenerations, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/lead-generation-count-by-status")
	@IsSuperAdmin
	public APIResponse<?> getLeadGenerationStatusCount() {
		try {
			List<LeadCountWithStatus> leadCountWithStatus = dashboardService.getLeadGenerationStatusCount();
			return Response.getSuccessResponse(leadCountWithStatus, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/lead-generation-count-created-by")
	@IsSuperAdmin
	public APIResponse<?> getLeadGenerationAndCreatedBy() {
		try {
			List<LeadCountAndCreatedBy> leadCountAndCreatedBy = dashboardService.getLeadGenerationAndCreatedBy();
			return Response.getSuccessResponse(leadCountAndCreatedBy, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/weekly-sales-orders-payment-count")
	@IsSuperAdmin
	public APIResponse<?> getWeeklySalesAndOrdersAndPaymentCount() {
		try {
			List<WeeklySalesAndOrdersAndPaymentCount> weeklyOrdersCounts = dashboardService
					.getWeeklySalesAndOrdersAndPaymentCount();
			return Response.getSuccessResponse(weeklyOrdersCounts, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/monthly-sales-orders-payment-count")
	@IsSuperAdmin
	public APIResponse<?> getMonthlySalesAndOrdersAndPaymentCount() {
		try {
			List<MonthlySalesAndOrdersAndPaymentCount> monthlyOrdersCounts = dashboardService
					.getMonthlySalesAndOrdersAndPaymentCount();
			return Response.getSuccessResponse(monthlyOrdersCounts, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/customer-sales-orders-payment-summary")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> getCustomerSalesAndOrderAndPayment(@RequestParam(value = "id", required = false) Long id) {
		try {
			List<CustomerSalesAndOrderAndPaymentSummary> customerSalesAndOrderAndPaymentSummaries = dashboardService
					.getCustomerSalesAndOrderAndPayment(id);
			return Response.getSuccessResponse(customerSalesAndOrderAndPaymentSummaries, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/daily-sales-orders-payment-count")
	@IsSuperAdmin
	public APIResponse<?> getDailySalesAndOrdersCount() {
		try {
			List<DailySalesAndOrdersAndPaymentCount> dailySalesAndOrdersCounts = dashboardService
					.getDailySalesAndOrdersCount();
			return Response.getSuccessResponse(dailySalesAndOrdersCounts, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/customer-sales-orders-payment-summaries")
	@IsSuperAdminOrAdminOrCustomer
	public APIResponse<?> getCustomerSalesAndOrderAndPayments(@RequestParam(value = "id") Long id) {
		try {
			CustomerSalesAndOrderAndPaymentSummary customerSalesAndOrderAndPaymentSummaries = dashboardService
					.getCustomerSalesAndOrderAndPayments(id);
			return Response.getSuccessResponse(customerSalesAndOrderAndPaymentSummaries, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}