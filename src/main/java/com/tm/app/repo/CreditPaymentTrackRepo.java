package com.tm.app.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tm.app.entity.CreditPaymentTrack;

import jakarta.transaction.Transactional;

public interface CreditPaymentTrackRepo extends JpaRepository<CreditPaymentTrack, Long> {

	@Transactional
	@Modifying
	@Query(value = "TRUNCATE TABLE t_credit_payment_track RESTART IDENTITY", nativeQuery = true)
	void truncateTable();

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "insert into t_credit_payment_track (payment_id,customer_id,sale_id,paid_amount,	pending_amount,total_order_amount,order_date_time ,updated_at) select tp.id  as payment_id,tp.customer_id,tp.sale_id,tp.total_paid_amount as paid_amount,tp.balance_amount as pending_amount,tp.total_order_amount,tp.sales_order_date  as order_date_time,current_timestamp from t_payment tp join t_customer tc on(tp.customer_id=tc.id) where sales_order_date  <= current_date - tc.follow_up_days and payment_status in ('UNPAID')", nativeQuery = true)
	void saveCreditPaymentTrack();

	void deleteByPaymentId(Long id);

	CreditPaymentTrack findByPaymentId(Long id);

	@Query("SELECT tcp FROM CreditPaymentTrack tcp join Customer c on(c.id=tcp.customerId) where LOWER(c.name) like LOWER(:search)")
	Page<CreditPaymentTrack> findByCustomer(String search, PageRequest of);

}
