package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

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

public interface AnalyticsService {

	public List<DailyPaymentSummary> getDailyPaymentSummary();

	public List<MonthlyPaymentSummary> getMonthlyPaymentSummary();

	public List<CustomerPaymentSummary> getCustomerPaymentSummary(Long id);

	public Page<CustomerSalesAndOrderSummary> getCustomerSalesAndOdersCount(DataFilter dataFilter);

	public List<SalesAndOrderCount> getSalesAndOrdersCount();

	public List<TotalPaymentSummary> getTotalPaymentSummary();

	public List<SalesPerCustomer> getSalesPerCustomer(Integer year);

	public List<OrdersPerCustomer> getOrdersPerCustomer(Integer year);

	public List<DailyLeadGeneration> getDailyLeadGeneration();

	public List<WeeklyLeadGeneration> getWeeklyLeadGeneration();

	public List<YearlyLeadGeneration> getYearlyLeadGeneration();

	public List<LeadCountWithStatus> getLeadGenerationStatusCount();

	public List<LeadCountAndCreatedBy> getLeadGenerationAndCreatedBy();

	public List<MonthlySalesAndOrdersAndPaymentCount> getMonthlySalesAndOrdersAndPaymentCount();

	public List<CustomerSalesAndOrderAndPaymentSummary> getCustomerSalesAndOrderAndPayment(Long id);

	List<CustomerPaymentSummary> getCustomerPaymentSummary();

	public List<DailySalesAndOrdersAndPaymentCount> getDailySalesAndOrdersCount();

	public List<WeeklySalesAndOrdersAndPaymentCount> getWeeklySalesAndOrdersAndPaymentCount();

	public CustomerSalesAndOrderAndPaymentSummary getCustomerSalesAndOrderAndPayments(Long id);

}
