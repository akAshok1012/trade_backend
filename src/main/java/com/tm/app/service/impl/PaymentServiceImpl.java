package com.tm.app.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
import com.tm.app.entity.CreditPaymentTrack;
import com.tm.app.entity.Customer;
import com.tm.app.entity.Notification;
import com.tm.app.entity.Order;
import com.tm.app.entity.Payment;
import com.tm.app.entity.PaymentHistory;
import com.tm.app.entity.SalesOrder;
import com.tm.app.entity.User;
import com.tm.app.enums.NotificationStatus;
import com.tm.app.enums.PaymentStatus;
import com.tm.app.repo.CreditPaymentTrackRepo;
import com.tm.app.repo.CustomerRepo;
import com.tm.app.repo.NotificationRepo;
import com.tm.app.repo.OrderRepo;
import com.tm.app.repo.PaymentHistoryRepository;
import com.tm.app.repo.PaymentRepo;
import com.tm.app.repo.SalesOrderRepo;
import com.tm.app.repo.UserRepository;
import com.tm.app.service.PaymentService;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentRepo paymentRepo;

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private SalesOrderRepo salesOrderRepo;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private PaymentHistoryRepository paymentHistoryRepository;

	@Autowired
	private CreditPaymentTrackRepo creditPaymentTrackRepo;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private NotificationRepo notificationRepo;

	@Override
	public Payment savePayment(PaymentDto paymentDto) {
		Payment payment = new Payment();
		try {
			BeanUtils.copyProperties(paymentDto, payment);
			payment = paymentRepo.save(payment);
		} catch (Exception e) {
			log.error("[PAYMENT] adding payment failed", e);
			throw new RuntimeException("Adding payment failed");
		}
		return payment;
	}

	@Override
	public Page<Payment> getPayments(DataFilter dataFilter) {
		log.info("[Payment] Get payment");
		return paymentRepo.getPayments(PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
				Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())), dataFilter.getSearch());
	}

	@Override
	public Payment getPaymentById(Long id) {
		log.info("[Payment] Get paymentById");
		return paymentRepo.getPaymentById(id);
	}

	@Override
	public Payment updatePayment(PaymentDto paymentDto) {
		log.info("[Payment] Updated payment");
		Payment payment = paymentRepo.findBySalesId(paymentDto.getSalesId());
		PaymentHistory paymentHistory = new PaymentHistory();
		CreditPaymentTrack creditPaymentTrack = creditPaymentTrackRepo.findByPaymentId(payment.getId());
		try {
			payment.setTotalPaidAmount(payment.getTotalPaidAmount() + paymentDto.getBalanceAmount());
			payment.setBalanceAmount(payment.getTotalOrderAmount() - payment.getTotalPaidAmount());
			if (payment.getBalanceAmount() == 0) {
				payment.setPaymentStatus(PaymentStatus.PAID);
			} else {
				payment.setPaymentStatus(PaymentStatus.PARTIAL);
			}

			payment.setUpdatedBy(paymentDto.getUpdatedBy());
			payment = paymentRepo.save(payment);

			// update CreditPaymentTrack table if payment is present
			if (Objects.nonNull(creditPaymentTrack)) {
				if (payment.getPaymentStatus().equals(PaymentStatus.PARTIAL)
						|| payment.getPaymentStatus().equals(PaymentStatus.CREDIT)) {
					creditPaymentTrack.setPaidAmount(payment.getTotalPaidAmount());
					creditPaymentTrack.setPendingAmount(payment.getBalanceAmount());
					creditPaymentTrackRepo.save(creditPaymentTrack);
				} else {
					creditPaymentTrackRepo.deleteById(creditPaymentTrack.getId());
				}

				// insert in Notification table
				updatePaymentNotification(creditPaymentTrack);

				log.info("[CreditPaymentTrack] update CreditPaymentTrack with  salesId [{}]",
						creditPaymentTrack.getSalesId());
			} else {

			}

			// insert in Payment History Table for every payment made by customer
			paymentHistory.setPaidAmount(paymentDto.getBalanceAmount());
			paymentHistory.setUpdatedBy(payment.getUpdatedBy());
			paymentHistory.setPaymentStatus(payment.getPaymentStatus());
			paymentHistoryRepository.save(paymentHistory);
			log.info("[PAYMENT] updated payment for salesId [{}]", payment.getSalesId());
		} catch (

		Exception e) {
			log.error("[PAYMENT] updating payment failed", e);
			throw new RuntimeException("Updating payment failed");
		}
		return payment;
	}

	/**
	 * Insert in Notification Table
	 * 
	 * @param creditPaymentTrack
	 */
	private void updatePaymentNotification(CreditPaymentTrack creditPaymentTrack) {
		Notification notification = new Notification();
		notification.setPhoneNumber(creditPaymentTrack.getCustomerId().getPhoneNumber());
		notification.setMessage("Hi " + creditPaymentTrack.getCustomerId().getName()
				+ ", This is a gentle reminder that your credit period for the SalesId "
				+ creditPaymentTrack.getSalesId() + " is over. The total outstanding amount is "
				+ creditPaymentTrack.getPendingAmount()
				+ ". Please make the payment at your earliest convenience to avoid any inconvenience. Thanks, [KPR & Team]");
		notification.setTitle("Credit Payment Tracker");
		notification.setUpdatedBy(creditPaymentTrack.getUpdatedBy());
		try {
			notification.setIsSend(true);
			notification.setNotificationStatus(NotificationStatus.PENDING);
		} catch (Exception e) {
			notification.setIsSend(false);
			notification.setNotificationStatus(NotificationStatus.FAILED);
		}
		notification = notificationRepo.save(notification);
	}

	@Override
	public void deletePaymentById(Long id) {
		log.info("[Payment] Deleted paymentById");
		try {
			paymentRepo.deleteById(id);
		} catch (Exception e) {
			log.error("[PAYMENT] deleting  payment failed", e);
			throw new RuntimeException("Deleting payment failed");
		}

	}

	@Override
	public List<CustomersPaymentStatusOrderStatusDto> getCustomersStatus() {
		return null;
	}

	@Override
	public Page<PaymentStatusDto> getPaymentStatus(PaymentStatus paymentStatus, DataFilter dataFilter) {
		return paymentRepo.getPaymentStatus(paymentStatus, PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
				Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())), dataFilter.getSearch());
	}

	@Override
	public List<Payment> getUnpaidPaymentStatus() {
		return paymentRepo.findByUnpaidPaymentStatus();
	}

	@Override
	public List<PaymentOrderIdAndCountDto> getPaymentCountAndOrderId() {
		return null;
	}

	@Override
	public List<PaymentPendingDetailsDto> getPaymentPendingDetails() {
		return null;
	}

	@Override
	public List<Payment> getPaymentOrderId(Integer order) {
		Order orders = orderRepo.findByOrderId(order);
		return null;
	}

	@Override
	public List<Payment> getPaymentBySalesId(Integer salesId) {
		SalesOrder salesOrder = salesOrderRepo.findAllBySalesId(salesId);
		return paymentRepo.findPaymentBySalesId(salesOrder.getSalesId());
	}

	@Override
	public Page<Payment> getPaymentByCustomer(String name, DataFilter dataFilter) {
		if (StringUtils.isEmpty(name)) {
			name = "%";
		} else {
			name = name + "%";
		}
		return paymentRepo.findPaymentByCustomerName(name, PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
				Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}

	@Override
	public Page<Payment> getPaymentByCustomerId(Long customerId, DataFilter dataFilter) {
		Customer customer = customerRepo.findById(customerId).orElseThrow();
		return paymentRepo.findPaymentByCustomer(customer, PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
				Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}

	@Override
	public List<PaymentSearchByCustomerDto> getCustomerPaymentBySearch(String search) {
		if (StringUtils.isEmpty(search)) {
			search = "%";
		} else {
			search = search + "%";
		}
		return paymentRepo.getCustomerPaymentBySearch(search);
	}

	@Override
	public Page<PaymentCustomerDto> getPaymentUserId(Long id, PaymentStatus paymentStatus, DataFilter dataFilter) {
		return paymentRepo.getPaymentUserId(id, paymentStatus, PageRequest.of(dataFilter.getPage(),
				dataFilter.getSize(), Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}

	@Override
	public Float getCustomerOutstanding(Long id) {
		return paymentRepo.getCustomerOutstanding(id);
	}

	@Override
	public CustomerOutstandingDto getCustomerOutstandingByUserId(Long id) {
		return paymentRepo.getCustomerOutstandingByUserId(id);
	}

	@Override
	public List<PaymentHistory> getViewPayment(Integer salesId) {
		return paymentRepo.getViewPayment(salesId);
	}

	@Override
	public Page<PaymentStatusSalesIdDto> getPaymentStatusSalesId(PaymentStatus paymentStatus, Long id, Integer salesId,
			DataFilter dataFilter) {
		String salesIdString = "%";
		if (id != null) {
			User user = userRepository.findById(id).orElseThrow();
			Customer customer = customerRepo.findById(user.getUserId()).orElseThrow();
			if (Objects.nonNull(salesId)) {
				salesIdString = "%" + salesId.toString() + "%";
				return paymentRepo.getPaymentStatusSalesIdAndCustomer(paymentStatus, customer, salesIdString,
						PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
								Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
			} else {
				return paymentRepo.getPaymentStatusSalesIdAndCustomer(paymentStatus, customer, salesIdString,
						PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
								Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
			}
		} else {
			if (Objects.nonNull(salesId)) {
				salesIdString = "%" + salesId.toString() + "%";
				return paymentRepo.getPaymentStatusSalesId(paymentStatus, salesIdString,
						PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
								Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
			} else {
				return paymentRepo.getPaymentStatusSalesId(paymentStatus, salesIdString,
						PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
								Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
			}
		}
	}

	@Override
	public Page<Payment> getPaymentsBySalesId(Integer salesId, DataFilter dataFilter) {
		String salesIdString = "%";
		if (Objects.nonNull(salesId)) {
			salesIdString = "%" + salesId.toString() + "%";
			return paymentRepo.getPaymentsBySalesId(salesIdString, PageRequest.of(dataFilter.getPage(),
					dataFilter.getSize(), Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
		}
		return paymentRepo.getPaymentsBySalesId(salesIdString, PageRequest.of(dataFilter.getPage(),
				dataFilter.getSize(), Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}

	@Override
	public List<OutstandingPaymentHistoryOrderDto> getOutstandingOrderPayment(Long id) {
		return paymentRepo.getOutstandingOrderPayment(id);
	}
}