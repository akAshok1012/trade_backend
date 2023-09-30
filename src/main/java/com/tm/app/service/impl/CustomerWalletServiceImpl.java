package com.tm.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tm.app.dto.CustomerWalletDto;
import com.tm.app.dto.DataFilter;
import com.tm.app.entity.CustomerWallet;
import com.tm.app.entity.CustomerWalletHistory;
import com.tm.app.entity.Notification;
import com.tm.app.entity.Payment;
import com.tm.app.entity.PaymentHistory;
import com.tm.app.enums.NotificationStatus;
import com.tm.app.enums.PaymentMode;
import com.tm.app.enums.PaymentStatus;
import com.tm.app.enums.TransactionType;
import com.tm.app.repo.CustomerWalletHistoryRepo;
import com.tm.app.repo.CustomerWalletRepo;
import com.tm.app.repo.NotificationRepo;
import com.tm.app.repo.PaymentHistoryRepository;
import com.tm.app.repo.PaymentRepo;
import com.tm.app.service.CustomerWalletService;

@Service
@Transactional
public class CustomerWalletServiceImpl implements CustomerWalletService {

	@Autowired
	private CustomerWalletRepo customerWalletRepo;

	@Autowired
	private CustomerWalletHistoryRepo customerWalletHistoryRepo;

	@Autowired
	private PaymentRepo paymentRepo;

	@Autowired
	private NotificationRepo notificationRepo;

	@Autowired
	private PaymentHistoryRepository paymentHistoryRepository;

	@Override
	public CustomerWallet saveCustomerWallet(CustomerWalletDto customerWalletDto) {
		CustomerWallet customerWallet = new CustomerWallet();
		CustomerWalletHistory customerWalletHistory = new CustomerWalletHistory();
		BeanUtils.copyProperties(customerWalletDto, customerWallet);
		customerWallet = customerWalletRepo.save(customerWallet);

		// Insert into CustomerWalletHistory table
		customerWalletHistory.setCustomerWallet(customerWallet);
		customerWalletHistory.setAmount(customerWalletDto.getAddAmount());
		customerWalletHistory.setTransactionType(TransactionType.CREDIT);
		customerWalletHistory.setUpdatedBy(customerWalletDto.getUpdatedBy());
		customerWalletHistory = customerWalletHistoryRepo.save(customerWalletHistory);
		return customerWallet;
	}

	@Override
	public List<CustomerWallet> getCustomerWallets() {
		return customerWalletRepo.findAll();
	}

	@Override
	public CustomerWallet getCustomerWalletById(Long id) {
		return customerWalletRepo.findById(id).orElseThrow();
	}

	@Override
	public void deleteCustomerWalletById(Long id) {
		customerWalletRepo.deleteById(id);
	}

	@Override
	@Transactional
	public CustomerWallet updateCustomerWallet(Long id, CustomerWalletDto customerWalletDto,
			TransactionType transactionType) {
		CustomerWallet customerWallet = customerWalletRepo.findById(id).orElseThrow();
		customerWallet = updateWalletAndWalletHistory(customerWalletDto, transactionType, customerWallet);
		List<Payment> pendingPayments = paymentRepo.getPendingPayments(customerWallet.getCustomer());
		if (transactionType.equals(TransactionType.CREDIT)) {
			if (CollectionUtils.isNotEmpty(pendingPayments)) {
				// updating payment,payment history and wallet table
				customerWallet = updatePaymentAndWallet(customerWallet, pendingPayments);
			}
		}

		// insert in Notification table
		updateCustomerWalletNotification(customerWalletDto, customerWallet);
		return customerWallet;
	}

	/**
	 * Insert in Notification Table
	 * 
	 * @param customerWalletDto
	 * @param customerWallet
	 */
	private void updateCustomerWalletNotification(CustomerWalletDto customerWalletDto, CustomerWallet customerWallet) {
		Notification notification = new Notification();
		notification.setPhoneNumber(customerWallet.getCustomer().getPhoneNumber());
		notification.setMessage(
				"Hi " + customerWallet.getCustomer().getName() + ", Amount of Rs." + customerWalletDto.getAddAmount()
						+ " has been Credited to your Customer Wallet Account. Please Check . Thanks, [KPR & Team]");
		notification.setTitle("Customer Wallet Amount Credited");
		notification.setUpdatedBy(customerWallet.getUpdatedBy());
		try {
			notification.setIsSend(true);
			notification.setNotificationStatus(NotificationStatus.PENDING);
		} catch (Exception e) {
			notification.setIsSend(false);
			notification.setNotificationStatus(NotificationStatus.FAILED);
		}
		notification = notificationRepo.save(notification);
	}

	/**
	 * updatePaymentAndPaymentHistoryAndWalletHistory
	 * 
	 * @param customerWallet
	 * @param pendingPayments
	 * @return
	 */
	private CustomerWallet updatePaymentAndWallet(CustomerWallet customerWallet, List<Payment> pendingPayments) {
		List<PaymentHistory> paymentHistories = new ArrayList<PaymentHistory>();
		List<Payment> payments = new ArrayList<Payment>();
		for (Payment payment : pendingPayments) {
			Float currentPaidAmount = 0F;
			if (Objects.nonNull(customerWallet.getBalance()) && customerWallet.getBalance() > 0) {
				if (payment.getDeliveryPayableAmount() <= customerWallet.getBalance()) {
					currentPaidAmount = payment.getDeliveryPayableAmount();
					payment.setTotalPaidAmount(payment.getTotalPaidAmount() + currentPaidAmount);
					payment.setDeliveryPayableAmount(payment.getDeliveryPayableAmount() - currentPaidAmount);
					payment.setBalanceAmount(payment.getTotalOrderAmount() - payment.getTotalPaidAmount());
					if (payment.getBalanceAmount() != 0) {
						payment.setPaymentStatus(PaymentStatus.PARTIAL);
					} else {
						payment.setPaymentStatus(PaymentStatus.PAID);
					}

				} else {
					if (customerWallet.getBalance() != 0) {
						currentPaidAmount = customerWallet.getBalance();
						payment.setTotalPaidAmount(payment.getTotalPaidAmount() + currentPaidAmount);
						payment.setDeliveryPayableAmount(payment.getDeliveryPayableAmount() - currentPaidAmount);
						payment.setPaymentStatus(PaymentStatus.PARTIAL);
					} else {
						payment.setPaymentStatus(PaymentStatus.CREDIT);
					}
					payment.setBalanceAmount(payment.getTotalOrderAmount() - payment.getTotalPaidAmount());
				}
				payment.setUpdatedBy(customerWallet.getUpdatedBy());
				payments.add(payment);

				// add PaymentHistory table
				paymentHistories.add(insertPaymentHistory(payment, currentPaidAmount));
				CustomerWalletDto customerWalletDto = new CustomerWalletDto();
				customerWalletDto.setCustomer(customerWallet.getCustomer());
				customerWalletDto.setAddAmount(currentPaidAmount);
				customerWalletDto.setNote("Debited for Sales Id :" + payment.getSalesId());

				// updating Wallet and wallet history
				customerWallet = updateWalletAndWalletHistory(customerWalletDto, TransactionType.DEBIT, customerWallet);
			} else {
				break;
			}
		}
		if (!payments.isEmpty()) {
			paymentRepo.saveAll(payments);
		}
		if (!paymentHistories.isEmpty()) {
			paymentHistoryRepository.saveAll(paymentHistories);
		}

		return customerWallet;
	}

	/**
	 * insert into payment history
	 * 
	 * @param payment
	 * @param currentPaidAmount
	 */
	private PaymentHistory insertPaymentHistory(Payment payment, Float currentPaidAmount) {
		PaymentHistory paymentHistory = new PaymentHistory();
		paymentHistory.setPayment(payment);
		paymentHistory.setPaidAmount(payment.getTotalPaidAmount());
		paymentHistory.setPaidAmount(currentPaidAmount);
		paymentHistory.setPaymentStatus(payment.getPaymentStatus());
		paymentHistory.setPaymentMode(PaymentMode.WALLET);
		paymentHistory.setUpdatedBy(payment.getUpdatedBy());
		return paymentHistory;
	}

	/**
	 * updating wallet and wallet history
	 * 
	 * @param customerWalletDto
	 * @param transactionType
	 * @param customerWallet
	 * @return
	 */
	public CustomerWallet updateWalletAndWalletHistory(CustomerWalletDto customerWalletDto,
			TransactionType transactionType, CustomerWallet customerWallet) {
		CustomerWalletHistory customerWalletHistory = new CustomerWalletHistory();
		customerWallet.setCustomer(customerWalletDto.getCustomer());
		if (transactionType.equals(TransactionType.CREDIT)) {
			customerWallet.setBalance(customerWallet.getBalance() + customerWalletDto.getAddAmount());
		} else {
			customerWallet.setBalance(customerWallet.getBalance() - customerWalletDto.getAddAmount());
		}
		customerWallet.setUpdatedBy(customerWalletDto.getUpdatedBy());
		customerWallet = customerWalletRepo.save(customerWallet);
		// Insert into CustomerWalletHistory table
		insertCustomerWalletHistory(customerWalletDto, transactionType, customerWallet, customerWalletHistory);
		return customerWallet;
	}

	/**
	 * insert into wallet history table
	 * 
	 * @param customerWalletDto
	 * @param transactionType
	 * @param customerWallet
	 * @param customerWalletHistory
	 */
	private void insertCustomerWalletHistory(CustomerWalletDto customerWalletDto, TransactionType transactionType,
			CustomerWallet customerWallet, CustomerWalletHistory customerWalletHistory) {
		customerWalletHistory.setCustomerWallet(customerWallet);
		customerWalletHistory.setAmount(customerWalletDto.getAddAmount());
		customerWalletHistory.setTransactionType(transactionType);
		customerWalletHistory.setUpdatedBy(customerWalletDto.getUpdatedBy());
		customerWalletHistory.setNotes(customerWalletDto.getNote());
		customerWalletHistory = customerWalletHistoryRepo.save(customerWalletHistory);
	}

	@Override
	public Page<CustomerWallet> getCustomerWalletList(DataFilter dataFilter) {
		return customerWalletRepo.findByCustomer(dataFilter.getSearch(), PageRequest.of(dataFilter.getPage(),
				dataFilter.getSize(), Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}
}
