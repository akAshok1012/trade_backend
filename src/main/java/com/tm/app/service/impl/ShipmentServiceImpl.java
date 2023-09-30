package com.tm.app.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tm.app.dto.CustomerWalletDto;
import com.tm.app.dto.DataFilter;
import com.tm.app.dto.ItemAndQuantityDto;
import com.tm.app.dto.ShipmentCustomerListDto;
import com.tm.app.dto.ShipmentDto;
import com.tm.app.dto.ShipmentItemDto;
import com.tm.app.dto.ShipmentPaymentCreditPaymentWalletDto;
import com.tm.app.dto.UpdateStockDto;
import com.tm.app.entity.CreditPaymentTrack;
import com.tm.app.entity.Customer;
import com.tm.app.entity.CustomerWallet;
import com.tm.app.entity.Notification;
import com.tm.app.entity.Payment;
import com.tm.app.entity.PaymentHistory;
import com.tm.app.entity.Shipment;
import com.tm.app.entity.ShipmentDetails;
import com.tm.app.entity.ShipmentHistory;
import com.tm.app.entity.Stock;
import com.tm.app.entity.User;
import com.tm.app.enums.NotificationStatus;
import com.tm.app.enums.PaymentMode;
import com.tm.app.enums.PaymentStatus;
import com.tm.app.enums.ShipmentStatus;
import com.tm.app.enums.TransactionType;
import com.tm.app.repo.CreditPaymentTrackRepo;
import com.tm.app.repo.CustomerRepo;
import com.tm.app.repo.NotificationRepo;
import com.tm.app.repo.PaymentHistoryRepository;
import com.tm.app.repo.PaymentRepo;
import com.tm.app.repo.ShipmentDetailsRepo;
import com.tm.app.repo.ShipmentHistoryRepo;
import com.tm.app.repo.ShipmentRepo;
import com.tm.app.repo.StockRepo;
import com.tm.app.repo.UserRepository;
import com.tm.app.service.ShipmentService;
import com.tm.app.utils.RandomUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class ShipmentServiceImpl implements ShipmentService {

	@Autowired
	private ShipmentRepo shipmentRepo;

	@Autowired
	private ShipmentDetailsRepo shipmentDetailsRepo;

	@Autowired
	private ShipmentHistoryRepo shipmentHistoryRepo;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private PaymentRepo paymentRepo;

	@Autowired
	private StockRepo stockRepo;

	@Autowired
	private NotificationRepo notificationRepo;

	@Autowired
	private PaymentHistoryRepository paymentHistoryRepository;

	@Autowired
	private CreditPaymentTrackRepo creditPaymentTrackRepo;

	@Autowired
	private CustomerWalletServiceImpl customerWalletService;

	private Float totalShippingAmount;

	private Float currentPaidAmount;

	@Override
	public List<Shipment> getShipments() {
		log.info("[Shipment] Get Shipment");
		return shipmentRepo.findAll();
	}

	@Override
	public Shipment saveShipment(ShipmentDto shipmentDto) {
		log.info("[Shipment] Saved Shipment");
		Shipment shipment = new Shipment();
		BeanUtils.copyProperties(shipmentDto, shipment);
		return shipmentRepo.save(shipment);
	}

	@Override
	public Shipment getShipmentById(Long id) {
		log.info("[Shipment] Get ShipmentById");
		return shipmentRepo.findById(id).orElseThrow();
	}

	@Override
	public void deleteShipmentById(Long id) {
		log.info("[Shipment] Deleted Shipment");
		shipmentRepo.deleteById(id);
	}

	@Override
	@Transactional
	public List<Shipment> updateShipment(ShipmentDto shipmentDto) {
		List<Shipment> shipmentDetails = null;
		try { // get shippingQuatity from shipmentDto
			Map<Long, Integer> shippingQuantity = shipmentDto.getItemAndQuantityDto().stream()
					.collect(Collectors.toMap(r -> r.getOrderItem().getId(), ItemAndQuantityDto::getShippingQuantity));
			// get stockMap
			Map<UpdateStockDto, Integer> stockMap = shipmentDto.getItemAndQuantityDto().stream()
					.collect(
							Collectors.toMap(
									r -> new UpdateStockDto(r.getOrderItem().getItemMaster(),
											r.getOrderItem().getUnitOfMeasure()),
									ItemAndQuantityDto::getShippingQuantity));

			updateStock(stockMap);
			// get orderItemIdList from shippingQuantity
			List<Long> orderItemIdList = shippingQuantity.keySet().stream().collect(Collectors.toList());

			// get shipment details by salesId and orderItemIdList
			shipmentDetails = shipmentRepo.findBySalesIdAndOrderItemIn(shipmentDto.getSalesId(), orderItemIdList);
			ShipmentPaymentCreditPaymentWalletDto paymentWalletDto = paymentRepo
					.getPaymentCreditPaymentWalletDetails(shipmentDto.getSalesId());
			List<ShipmentHistory> shipmentHistories = new ArrayList<>();
			totalShippingAmount = 0F;
			String trackingNumber = generateTrackingNumber();
			shipmentDetails.forEach(shipment -> {
				// updating shipment details and calculating totalShippingAmount
				updateShipmentDetails(shipmentDto, shippingQuantity, shipmentHistories, shipment, trackingNumber);
			});

			shipmentDetails = shipmentRepo.saveAll(shipmentDetails);
			shipmentHistoryRepo.saveAll(shipmentHistories);

			// insert in Notification table
			updateShipmentNotification(shipmentDto, paymentWalletDto, trackingNumber);

			if (!paymentWalletDto.getPayment().getPaymentStatus().equals(PaymentStatus.PAID)) {
				updatePaymentsAndWallet(shipmentDto, paymentWalletDto.getPayment(), totalShippingAmount,
						paymentWalletDto.getCreditPaymentTrack(), paymentWalletDto.getCustomerWallet());
			}
		} catch (Exception e) {

			e.printStackTrace();
			log.error("[SHIPMENT] updating shipment failed", e);
			throw new RuntimeException(e.getMessage());
		}
		return shipmentDetails;
	}

	/**
	 * Insert in Notification Table
	 * 
	 * @param shipmentDto
	 * @param paymentWalletDto
	 * @param trackingNumber
	 */
	private void updateShipmentNotification(ShipmentDto shipmentDto,
			ShipmentPaymentCreditPaymentWalletDto paymentWalletDto, String trackingNumber) {
		Notification notification = new Notification();
		notification.setMessage("Hi " + paymentWalletDto.getCustomerWallet().getCustomer().getName() + ", Your SalesId "
				+ shipmentDto.getSalesId()
				+ " has been shipped and is on its way. You can track your shipment using the tracking number: "
				+ trackingNumber + " . Thanks, [KPR & Team]");
		notification.setTitle("Shipped");
		notification.setUpdatedBy(shipmentDto.getUpdatedBy());
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
	 * 
	 * @param stockMap
	 */
	private void updateStock(Map<UpdateStockDto, Integer> stockMap) {
		List<Stock> stockList = new ArrayList<>();
		stockMap.keySet().stream().forEach(r -> {
			Stock stock = stockRepo.findByItemMasterAndUnitOfMeasure(r.getItemMasterId(), r.getUnitOfMeasureId());
			if (Objects.isNull(stock)) {
				throw new RuntimeException("Stock Unavailable");
			}
			if (stock.getInStock() >= stockMap.get(r)) {
				stock.setInStock(stock.getInStock() - stockMap.get(r));
				stockList.add(stock);
			} else {
				throw new RuntimeException("Out of Stock Item : " + r.getItemMasterId().getItemName() + " Unit : "
						+ r.getUnitOfMeasureId().getUnitName());

			}
		});

		stockRepo.saveAll(stockList);
	}

	/**
	 * check exists trackingNumber
	 * 
	 * @return
	 */
	private String generateTrackingNumber() {
		String trackingNumber = RandomUtils.generateTrackingId();
		if (shipmentHistoryRepo.existsByTrackingNumber(trackingNumber)) {
			return generateTrackingNumber();
		}
		return trackingNumber;
	}

	/**
	 * 
	 * @param shipmentDto
	 * @param shippingQuantity
	 * @param shipmentHistories
	 * @param shipmentHistory
	 * @param shipment
	 * @param trackingNumber
	 */
	private void updateShipmentDetails(ShipmentDto shipmentDto, Map<Long, Integer> shippingQuantity,
			List<ShipmentHistory> shipmentHistories, Shipment shipment, String trackingNumber) {
		ShipmentHistory shipmentHistory = new ShipmentHistory();

		if (shippingQuantity.containsKey(shipment.getOrderItem().getId())) {
			shipment.setShippedQuantity(
					shipment.getShippedQuantity() + shippingQuantity.get(shipment.getOrderItem().getId()));
			shipment.setBalanceQuantity(shipment.getOrderItem().getOrderedQuantity() - shipment.getShippedQuantity());
			totalShippingAmount = totalShippingAmount + ((shipment.getOrderItem().getUnitOfMeasure().getUnitWeight()
					* shippingQuantity.get(shipment.getOrderItem().getId())) * shipment.getOrderItem().getUnitPrice());
		}
		shipment.setShipmentStatus(
				shipment.getBalanceQuantity() != 0 ? ShipmentStatus.PARTIAL : ShipmentStatus.SHIPPED);
		shipment.setUpdatedBy(shipmentDto.getUpdatedBy());
		shipmentHistory.setShipment(shipment);
		shipmentHistory.setShipmentDate(new Date(System.currentTimeMillis()));
		shipmentHistory.setCarrier(shipmentDto.getCarrier());
		shipmentHistory.setTrackingNumber(trackingNumber);
		shipmentHistory.setShippedQuantity(shippingQuantity.get(shipment.getOrderItem().getId()));
		shipmentHistory.setUpdatedBy(shipmentDto.getUpdatedBy());
		shipmentHistory.setRemarks(shipmentDto.getRemark());
		shipmentHistories.add(shipmentHistory);
	}

	/**
	 * updatePaymentsAndWallet
	 * 
	 * @param shipmentDto
	 * @param payment
	 * @param creditPaymentTrack
	 * @param customerWallet
	 * @param currentPaidAmount
	 */
	private void updatePaymentsAndWallet(ShipmentDto shipmentDto, Payment payment, Float totalShippingAmount,
			CreditPaymentTrack creditPaymentTrack, CustomerWallet customerWallet) {
		PaymentHistory paymentHistory = new PaymentHistory();
		paymentHistory.setPaymentMode(PaymentMode.WALLET);
		// update payment table
		updatePayment(shipmentDto, payment, customerWallet, paymentHistory);
		// update credit payment tracker table
		updateCreditPaymentTracker(payment, creditPaymentTrack);
		// insert payment history
		insertPaymentHistory(shipmentDto, payment, currentPaidAmount, paymentHistory);
		// updating Customer wallet and wallet history
		if (customerWallet.getBalance() != 0 && !payment.getPaymentStatus().equals(PaymentStatus.CREDIT)) {
			updateCustomerWallet(payment, customerWallet, currentPaidAmount);
		}
		// insert in Notification table
		updateCustomerWalletNotification(customerWallet, paymentHistory);

	}

	/**
	 * Insert in Notification Table
	 * 
	 * @param customerWallet
	 * @param paymentHistory
	 */
	private void updateCustomerWalletNotification(CustomerWallet customerWallet, PaymentHistory paymentHistory) {
		Notification notification = new Notification();
		notification.setPhoneNumber(customerWallet.getCustomer().getPhoneNumber());
		notification.setMessage(
				"Hi " + customerWallet.getCustomer().getName() + ", Amount of Rs." + paymentHistory.getPaidAmount()
						+ " has been debited from your Customer Wallet Account. Please Check . Thanks, [KPR & Team]");
		notification.setTitle("Customer Wallet Amount Debited");
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
	 * update credit payment tracker table
	 * 
	 * @param payment
	 * @param creditPaymentTrack
	 */
	private void updateCreditPaymentTracker(Payment payment, CreditPaymentTrack creditPaymentTrack) {
		if (Objects.nonNull(creditPaymentTrack)) {
			if (payment.getPaymentStatus().equals(PaymentStatus.PARTIAL)
					|| payment.getPaymentStatus().equals(PaymentStatus.CREDIT)) {
				creditPaymentTrack.setPaidAmount(payment.getTotalPaidAmount());
				creditPaymentTrack.setPendingAmount(payment.getBalanceAmount());
				creditPaymentTrackRepo.save(creditPaymentTrack);
			} else {
				creditPaymentTrackRepo.deleteById(creditPaymentTrack.getId());
			}
		}
	}

	/**
	 * update payment table
	 * 
	 * @param shipmentDto
	 * @param shipment
	 * @param payment
	 * @param shipmentDetails
	 * @param customerWallet
	 * @param currentPaidAmount
	 * @param paymentHistory
	 * @param paymentList
	 */
	private void updatePayment(ShipmentDto shipmentDto, Payment payment, CustomerWallet customerWallet,
			PaymentHistory paymentHistory) {
		currentPaidAmount = 0F;
		if (totalShippingAmount <= customerWallet.getBalance() && customerWallet.getBalance() >= 0) {
			currentPaidAmount = totalShippingAmount;
			payment.setTotalPaidAmount(payment.getTotalPaidAmount() + currentPaidAmount);
			if (payment.getTotalOrderAmount().equals(payment.getTotalPaidAmount())) {
				payment.setPaymentStatus(PaymentStatus.PAID);
			} else {
				payment.setPaymentStatus(PaymentStatus.PARTIAL);
			}
		} else {
			if (customerWallet.getBalance() != 0) {
				currentPaidAmount = customerWallet.getBalance();
				payment.setDeliveryPayableAmount(
						payment.getDeliveryPayableAmount() + (totalShippingAmount - currentPaidAmount));
				payment.setTotalPaidAmount(payment.getTotalPaidAmount() + currentPaidAmount);
				payment.setPaymentStatus(PaymentStatus.PARTIAL);
			} else {
				paymentHistory.setPaymentMode(PaymentMode.CREDIT);
				if (payment.getTotalPaidAmount() > 0) {
					payment.setPaymentStatus(PaymentStatus.PARTIAL);
				} else {
					payment.setPaymentStatus(PaymentStatus.CREDIT);
				}
				payment.setDeliveryPayableAmount(payment.getDeliveryPayableAmount() + totalShippingAmount);
			}
		}
		payment.setBalanceAmount(payment.getTotalOrderAmount() - payment.getTotalPaidAmount());
		payment.setUpdatedBy(shipmentDto.getUpdatedBy());
		payment = paymentRepo.save(payment);
	}

	/**
	 * insertPaymentHistory
	 * 
	 * @param shipmentDto
	 * @param payment
	 * @param shipmentDetails
	 * @param currentPaidAmount
	 * @param paymentHistory
	 */
	private void insertPaymentHistory(ShipmentDto shipmentDto, Payment payment, Float currentPaidAmount,
			PaymentHistory paymentHistory) {
		paymentHistory.setPaidAmount(currentPaidAmount);
		paymentHistory.setPayment(payment);
		paymentHistory.setPaymentStatus(payment.getPaymentStatus());
		paymentHistory.setUpdatedBy(shipmentDto.getUpdatedBy());
		paymentHistoryRepository.save(paymentHistory);
	}

	/**
	 * updateCustomerWallet
	 * 
	 * @param payment
	 * @param customerWallet
	 * @param currentPaidAmount
	 */
	private void updateCustomerWallet(Payment payment, CustomerWallet customerWallet, Float currentPaidAmount) {
		CustomerWalletDto customerWalletDto = new CustomerWalletDto();
		customerWalletDto.setCustomer(customerWallet.getCustomer());
		customerWalletDto.setAddAmount(currentPaidAmount);
		customerWalletDto.setUpdatedBy(payment.getUpdatedBy());
		customerWalletDto.setNote("Debited for Sales Id :" + payment.getSalesId());
		customerWallet = customerWalletService.updateWalletAndWalletHistory(customerWalletDto, TransactionType.DEBIT,
				customerWallet);
	}

	@Override
	public List<ShipmentItemDto> getShipmentBySalesId(Integer salesId) {
		return shipmentRepo.getShipmentBySalesId(salesId);
	}

	@Override
	public List<ShipmentDetails> getCustomerShipmentBySalesId(Integer salesId, Long id) {
		User user = userRepository.findById(id).orElseThrow();
		Customer customer = customerRepo.findById(user.getUserId()).orElseThrow();
		return shipmentDetailsRepo.getCustomerShipmentBySalesId(salesId, customer);
	}

	@Override
	public Page<Shipment> getShipmentListing(DataFilter dataFilter) {
		log.info("[Shipment] Get ShipmentList");
		return shipmentRepo.findAll(PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
				Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}

	@Override
	public Page<ShipmentCustomerListDto> getShipmentsBySalesId(Integer salesId, DataFilter dataFilter) {
		String salesIdString = "%";
		if (Objects.nonNull(salesId)) {
			salesIdString = "%" + salesId.toString() + "%";
			return shipmentRepo.getShipmentsBySalesId(salesIdString, PageRequest.of(dataFilter.getPage(),
					dataFilter.getSize(), Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
		}
		return shipmentRepo.getShipmentsBySalesId(salesIdString, PageRequest.of(dataFilter.getPage(),
				dataFilter.getSize(), Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}

	@Override
	public Page<ShipmentCustomerListDto> getShipmentListingWithCustomer(DataFilter dataFilter) {
		return shipmentRepo.getShipmentListingWithCustomer(PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
				Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())), dataFilter.getSearch());
	}
}
