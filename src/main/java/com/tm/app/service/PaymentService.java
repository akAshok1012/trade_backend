package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.CustomerOutstandingDto;
import com.tm.app.dto.CustomersPaymentStatusOrderStatusDto;
import com.tm.app.dto.DataFilter;
import com.tm.app.dto.OutstandingPaymentHistoryOrderDto;
import com.tm.app.dto.PaymentCustomerDto;
import com.tm.app.dto.PaymentDto;
import com.tm.app.dto.PaymentOrderIdAndCountDto;
import com.tm.app.dto.PaymentPendingDetailsDto;
import com.tm.app.dto.PaymentSearchByCustomerDto;
import com.tm.app.dto.PaymentStatusDto;
import com.tm.app.dto.PaymentStatusSalesIdDto;
import com.tm.app.entity.Payment;
import com.tm.app.entity.PaymentHistory;
import com.tm.app.enums.PaymentStatus;

public interface PaymentService {

	public Payment savePayment(PaymentDto paymentDto);

	public Page<Payment> getPayments(DataFilter dataFilter);

	public Payment getPaymentById(Long id);

	public Payment updatePayment(PaymentDto paymentDto);

	public void deletePaymentById(Long id);

	public List<CustomersPaymentStatusOrderStatusDto> getCustomersStatus();

	public Page<PaymentStatusDto> getPaymentStatus(PaymentStatus paymentStatus, DataFilter dataFilter);

	public List<Payment> getUnpaidPaymentStatus();

	public List<PaymentOrderIdAndCountDto> getPaymentCountAndOrderId();

	public List<PaymentPendingDetailsDto> getPaymentPendingDetails();

	public List<Payment> getPaymentOrderId(Integer order);

	public List<Payment> getPaymentBySalesId(Integer salesId);

	public Page<Payment> getPaymentByCustomer(String name, DataFilter dataFilter);

	public Page<Payment> getPaymentByCustomerId(Long customerId, DataFilter dataFilter);

	public List<PaymentSearchByCustomerDto> getCustomerPaymentBySearch(String search);

	public Page<PaymentCustomerDto> getPaymentUserId(Long id, PaymentStatus paymentStatus, DataFilter dataFilter);

	public Float getCustomerOutstanding(Long id);

	public CustomerOutstandingDto getCustomerOutstandingByUserId(Long id);

	public List<PaymentHistory> getViewPayment(Integer salesId);

	public Page<PaymentStatusSalesIdDto> getPaymentStatusSalesId(PaymentStatus paymentStatus, Long id, Integer salesId,
			DataFilter dataFilter, String searchName);

	public Page<Payment> getPaymentsBySalesId(Integer salesId, DataFilter dataFilter);

	public List<OutstandingPaymentHistoryOrderDto> getOutstandingOrderPayment(Long id);

}