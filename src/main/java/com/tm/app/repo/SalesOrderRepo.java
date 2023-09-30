package com.tm.app.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.dto.SalesViewDto;
import com.tm.app.entity.Customer;
import com.tm.app.entity.SalesOrder;
import com.tm.app.enums.SalesStatus;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface SalesOrderRepo extends JpaRepository<SalesOrder, Long> {

	List<SalesOrder> findByCustomerId(Long id);

	List<SalesOrder> findBySalesStatus(SalesStatus salesStatus);

	SalesOrder findAllBySalesId(Integer id);

	SalesOrder findBySalesId(Integer salesId);

	@Query(value = "select count(o.*) as orderCount from t_order o where Date(o.updated_at) = current_date", nativeQuery = true)
	Integer getOrderDailyCount();

	@Query(value = "select count(o.*) as orderCount from t_order o where Date(o.updated_at)  >= current_date - interval '30 days'", nativeQuery = true)
	Integer getOrderMonthlyCount();

	@Query(value = "select count(o.*) as orderCount from t_order o where Date(o.updated_at)  >= current_date - interval '7 days'", nativeQuery = true)
	Integer getOrderWeeklyCount();

	@Query(value = "select count(tso.*) as salesCount from t_sales_order tso where  Date(tso.updated_at) = current_date and tso.sales_status ='PROCESSING'", nativeQuery = true)
	Integer getSalesDailyCount();

	@Query(value = "select count(tso.*) as salesCount from t_sales_order tso where  Date(tso.updated_at) >= current_date - interval '30 days' and tso.sales_status ='PROCESSING'", nativeQuery = true)
	Integer getSalesMonthlyCount();

	@Query(value = "select count(tso.*) as salesCount from t_sales_order tso where  Date(tso.updated_at) >= current_date - interval '7 days' and tso.sales_status ='PROCESSING'", nativeQuery = true)
	Integer getSalesWeeklyCount();

	@Query("SELECT new com.tm.app.dto.SalesViewDto(s.salesId as salesId,c.name as customerName,p.paymentStatus as paymentStatus) from SalesOrder s join Customer c on(c.id=s.customer.id) join Payment p on(p.salesId=s.salesId) where s.customer=:customer and Lower(c.name) like LOWER(:search)")
	Page<SalesViewDto> getCustomerSalesPaymentStatusByCustomer(Customer customer, String search, PageRequest of);

	@Query("SELECT new com.tm.app.dto.SalesViewDto(s.salesId as salesId,c.name as customerName,p.paymentStatus as paymentStatus) from SalesOrder s join Customer c on(c.id=s.customer.id) join Payment p on(p.salesId=s.salesId) where Lower(c.name) like LOWER(:search)")
	Page<SalesViewDto> getCustomerSalesPaymentStatus(String search, PageRequest of);

	@Query("SELECT new com.tm.app.dto.SalesViewDto(s.salesId as salesId,c.name as customerName,p.paymentStatus as paymentStatus) from SalesOrder s join Customer c on(c.id=s.customer.id) join Payment p on(p.salesId=s.salesId) where cast(s.salesId as text) like (:salesIdString)")
	Page<SalesViewDto> getSalesBySalesId(String salesIdString, PageRequest of);

	@Query("SELECT new com.tm.app.dto.SalesViewDto(s.salesId as salesId,c.name as customerName,p.paymentStatus as paymentStatus) from SalesOrder s join Customer c on(c.id=s.customer.id) join Payment p on(p.salesId=s.salesId) where s.customer=:customer and cast(s.salesId as text) like (:salesIdString) and Lower(c.name) like LOWER(:search)")
	Page<SalesViewDto> getCustomerSalesPaymentStatusByCustomerAndSalesId(Customer customer, String salesIdString,
			String search, PageRequest of);

	boolean existsBySalesId(int salesId);

}
