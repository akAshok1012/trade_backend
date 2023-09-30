package com.tm.app.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.dto.CustomerOutstandingDto;
import com.tm.app.dto.OutstandingPaymentHistoryOrderDto;
import com.tm.app.dto.PaymentCustomerDto;
import com.tm.app.dto.PaymentSearchByCustomerDto;
import com.tm.app.dto.PaymentStatusDto;
import com.tm.app.dto.PaymentStatusSalesIdDto;
import com.tm.app.dto.ShipmentPaymentCreditPaymentWalletDto;
import com.tm.app.entity.Customer;
import com.tm.app.entity.Payment;
import com.tm.app.entity.PaymentHistory;
import com.tm.app.enums.PaymentStatus;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface PaymentRepo extends JpaRepository<Payment, Long> {

//	@Query("SELECT new com.tm.app.dto.CustomersPaymentStatusOrderStatusDto(p.paymentStatus,c.name,s.salesStatus) FROM Payment p join Customer c on (c.id = p.customerId.id) join SalesOrder s on (s.salesId = p.salesId)")
//	List<CustomersPaymentStatusOrderStatusDto> findAllCustomersPaymentStatusOrderStatus();

	Payment getPaymentById(Long id);

	@Query("SELECT new com.tm.app.dto.PaymentStatusDto(s.salesId as salesId,c.name as customerName,p.paymentStatus as paymentStatus,p.totalPaidAmount as paidAmount,p.totalOrderAmount as paymentAmount,p.paymentDate as paymentDate) from Payment p Join SalesOrder s on (s.salesId = p.salesId) Join Customer c on(c.id=p.customer) where p.paymentStatus =:paymentStatus And LOWER(c.name) like LOWER(:search)")
	Page<PaymentStatusDto> getPaymentStatus(PaymentStatus paymentStatus, PageRequest pageRequest, String search);

	@Query("SELECT t From Payment t where t.paymentStatus='UNPAID'")
	List<Payment> findByUnpaidPaymentStatus();

//	@Query("SELECT new com.tm.app.dto.PaymentOrderIdAndCountDto(p.order as orderId,count(*) as count) from Payment p where paymentStatus='UNPAID' group by p.order")
//	List<PaymentOrderIdAndCountDto> getPaymentCountAndOrderId();

//	@Query("SELECT new com.tm.app.dto.PaymentPendingDetailsDto(p.order as orderId,p.totalOrderAmount as totalAmount,p.totalPaidAmount as paidAmount,p.balanceAmount as balanceAmount,p.customerId as customer,p.paymentStatus as paymentStatus) FROM Payment p  join Order od on(p.order=od.orderId) where paymentStatus in('UNPAID','PARTIAL')")
//	List<PaymentPendingDetailsDto> getPaymentPendingDetails();

	Payment findBySalesId(Integer salesId);

//	List<Payment> findAllByOrder(Integer orderId);

	@Query("SELECT p FROM Payment p WHERE paymentStatus NOT IN ('PAID') and customer = :customer")
	Page<Payment> findPaymentByCustomer(Customer customer, PageRequest pageRequest);

	@Query("SELECT p FROM Payment p WHERE paymentStatus NOT IN ('PAID') and salesId = :salesId")
	List<Payment> findPaymentBySalesId(Integer salesId);

	@Query("SELECT new com.tm.app.dto.PaymentSearchByCustomerDto(pm.id as id,c.name as name,pm.salesId as salesId,pm.paymentStatus as paymentStatus,pm.totalOrderAmount as paymentAmount,pm.balanceAmount as balanceAmount,pm.totalPaidAmount as paidAmount) FROM Payment pm join Customer c on(c.id=pm.customer.id) WHERE Lower(c.name) like LOWER(:search)")
	List<PaymentSearchByCustomerDto> getCustomerPaymentBySearch(String search);

	@Query("SELECT t FROM Payment t join Customer c on(c.id=t.customer) WHERE t.paymentStatus NOT IN ('PAID') and LOWER(c.name) like LOWER(:name)")
	Page<Payment> findPaymentByCustomerName(String name, PageRequest of);

	@Query("SELECT pm FROM Payment pm join Customer c on(c.id=pm.customer.id) WHERE Lower(c.name) like LOWER(:search) and paymentStatus NOT IN ('PAID') ")
	Page<Payment> getPayments(PageRequest of, String search);

	@Query("SELECT new com.tm.app.dto.PaymentCustomerDto (p.salesId as salesId,p.paymentStatus as paymentStatus,c.name as name,u.id as user,p.totalPaidAmount as paidAmount,p.totalOrderAmount as paymentAmount,p.paymentDate as paymentDate) from Payment p JOIN Customer c ON(p.customer = c.id) JOIN User u on(c.id=u.userId) where u.id= :id and p.paymentStatus=:paymentStatus")
	Page<PaymentCustomerDto> getPaymentUserId(Long id, PaymentStatus paymentStatus, PageRequest pageRequest);

	@Query(value = "select sum(tp.delivery_payable_amount) from t_payment tp where tp.customer_id =:id and tp.delivery_payable_amount>0", nativeQuery = true)
	Float getCustomerOutstanding(Long id);

	@Query("select new com.tm.app.dto.CustomerOutstandingDto(sum(p.balanceAmount) as balanceAmount, cw.balance as walletAmount) from Payment p join CustomerWallet cw on(cw.customer.id=p.customer) join Customer c on (c.id=cw.customer.id) where c.id =:id group by cw.balance")
	CustomerOutstandingDto getCustomerOutstandingByUserId(Long id);

	@Query("select tp.salesId, tc.name, tp.salesOrderDate,cp from Payment tp left join CreditPaymentTrack cp on(cp.paymentId=tp.id) join Customer tc on (tp.customer = tc.id) where tp.salesOrderDate <= current_date - tc.followUpDays and tp.paymentStatus in ('UNPAID')")
	List<Object[]> getPaymentNotification();

	@Query("Select ph from PaymentHistory ph JOIN Payment p on(ph.payment=p.id) JOIN SalesOrder so ON(p.salesId=so.salesId) where p.salesId = :salesId")
	List<PaymentHistory> getViewPayment(Integer salesId);

	@Query("Select new com.tm.app.dto.PaymentStatusSalesIdDto(so.salesId as salesId,c.name as customerName,p.paymentStatus as paymentStatus,p.totalPaidAmount as paidAmount,p.totalOrderAmount as paymentAmount,p.paymentDate as paymentDate) from Payment p join SalesOrder so ON(p.salesId=so.salesId) join Customer c ON(p.customer.id=c.id)  where p.paymentStatus = :paymentStatus and p.customer =:customer and cast(p.salesId as text) like (:salesIdString)")
	Page<PaymentStatusSalesIdDto> getPaymentStatusSalesIdAndCustomer(PaymentStatus paymentStatus, Customer customer,
			String salesIdString, PageRequest of);

	@Query("select tp from Payment tp where paymentStatus IN ('UNPAID','PARTIAL') and cast(tp.salesId as text) like (:salesIdString)")
	Page<Payment> getPaymentsBySalesId(String salesIdString, PageRequest of);

	@Query("Select new com.tm.app.dto.PaymentStatusSalesIdDto(so.salesId as salesId,c.name as customerName,p.paymentStatus as paymentStatus,p.totalPaidAmount as paidAmount,p.totalOrderAmount as paymentAmount,p.paymentDate as paymentDate) from Payment p join SalesOrder so ON(p.salesId=so.salesId) join Customer c ON(p.customer=c.id) where p.paymentStatus = :paymentStatus and cast(p.salesId as text) like (:salesIdString)")
	Page<PaymentStatusSalesIdDto> getPaymentStatusSalesId(PaymentStatus paymentStatus, String salesIdString,
			PageRequest of);

	@Query("Select new com.tm.app.dto.OutstandingPaymentHistoryOrderDto(o.orderId as orderId,p.totalOrderAmount as paymentAmount,p.totalPaidAmount as paidAmount,p.balanceAmount as balanceAmount,c.name as name,o.updatedAt as dateTime) from Payment p join SalesOrder so ON(p.salesId=so.salesId) Join Order o On(o.orderId = so.order) Join Customer c ON (o.customer=c.id) where c.id = :id and p.paymentStatus NOT IN('PAID')")
	List<OutstandingPaymentHistoryOrderDto> getOutstandingOrderPayment(Long id);

	@Query("SELECT t From Payment t where t.paymentStatus NOT IN('PAID') and t.deliveryPayableAmount >0 and t.customer=:customerId order by salesOrderDate")
	List<Payment> getPendingPayments(Customer customerId);

	@Query("SELECT new com.tm.app.dto.ShipmentPaymentCreditPaymentWalletDto(p as payment, cpt as creditPaymentTrack, cw as customerWallet) from Payment p full outer join CreditPaymentTrack cpt on(p.id=cpt.paymentId) join CustomerWallet cw on(p.customer=cw.customer) where p.salesId=:salesId")
	ShipmentPaymentCreditPaymentWalletDto getPaymentCreditPaymentWalletDetails(Integer salesId);

}
