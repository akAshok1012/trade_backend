package com.tm.app.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.Customer;
import com.tm.app.entity.CustomerWallet;
import com.tm.app.entity.CustomerWalletHistory;

@Repository
public interface CustomerWalletHistoryRepo extends JpaRepository<CustomerWalletHistory, Long> {

	@Query("select cwh FROM CustomerWalletHistory cwh where cwh.customerWallet=:customerWallet AND  cast(cwh.amount as text) like (:search)")
	Page<CustomerWalletHistory> getDataByCustomerWallet(CustomerWallet customerWallet, PageRequest pageRequest,
			String search);

	@Query("select cwh FROM CustomerWalletHistory cwh join CustomerWallet cw on(cw.id=cwh.customerWallet) where cw.customer=:customer AND  cast(cwh.amount as text) like (:search) AND DATE(cwh.updatedAt) BETWEEN TO_DATE(:fromDate,'YYYY-MM-DD')  and TO_DATE(:toDate,'YYYY-MM-DD')")
	Page<CustomerWalletHistory> getCustomerWalletHistoryWithDateFilter(Customer customer, PageRequest of,
			String fromDate, String toDate, String search);

	@Query("select cwh FROM CustomerWalletHistory cwh join CustomerWallet cw on(cw.id=cwh.customerWallet) where cw.customer=:customer AND  cast(cwh.amount as text) like (:search)")
	Page<CustomerWalletHistory> getCustomerWalletHistory(Customer customer, PageRequest of, String search);

	@Query("select cwh FROM CustomerWalletHistory cwh where cwh.customerWallet=:customerWallet AND  cast(cwh.amount as text) like (:search) AND DATE(cwh.updatedAt) BETWEEN TO_DATE(:fromDate,'YYYY-MM-DD')  and TO_DATE(:toDate,'YYYY-MM-DD')")
	Page<CustomerWalletHistory> getCustomerWalletHistoryByDate(CustomerWallet customerWallet, PageRequest of,
			String fromDate, String toDate, String search);

}
