package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.CustomerWalletHistoryDto;
import com.tm.app.dto.DataFilter;
import com.tm.app.entity.CustomerWalletHistory;

public interface CustomerWalletHistoryService {

	CustomerWalletHistory saveCustomerWalletHistory(CustomerWalletHistoryDto customerWalletHistoryDto);

	List<CustomerWalletHistory> getCustomerWalletHistorys();

	CustomerWalletHistory getCustomerWalletHistoryById(Long id);

	void deleteCustomerWalletHistoryById(Long id);

	CustomerWalletHistory updateCustomerWalletHistory(Long id, CustomerWalletHistoryDto customerWalletHistoryDto);

	Page<CustomerWalletHistory> getCustomerWalletHistoryByWalletId(Long walletId, DataFilter dataFilter, String fromDate, String toDate);

	Page<CustomerWalletHistory> getCustomerWalletHistory(DataFilter dataFilter, Long id, String fromDate,
			String toDate);

}
