package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.CustomerWalletDto;
import com.tm.app.dto.DataFilter;
import com.tm.app.entity.CustomerWallet;
import com.tm.app.enums.TransactionType;

public interface CustomerWalletService {

	CustomerWallet saveCustomerWallet(CustomerWalletDto customerWalletDto);

	List<CustomerWallet> getCustomerWallets();

	CustomerWallet getCustomerWalletById(Long id);

	void deleteCustomerWalletById(Long id);

	CustomerWallet updateCustomerWallet(Long id, CustomerWalletDto customerWalletDto,TransactionType transactionType);

	Page<CustomerWallet> getCustomerWalletList(DataFilter dataFilter);

}
