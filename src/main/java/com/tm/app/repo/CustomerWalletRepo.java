package com.tm.app.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.Customer;
import com.tm.app.entity.CustomerWallet;

@Repository
public interface CustomerWalletRepo extends JpaRepository<CustomerWallet, Long> {

	CustomerWallet findByCustomer(Customer customerId);

	@Query("SELECT cw FROM CustomerWallet cw join Customer c on(cw.customer=c.id) where LOWER(c.name) like LOWER(:search)")
	Page<CustomerWallet> findByCustomer(String search, PageRequest of);

	boolean existsByCustomer(Customer customer);

}
