package com.tm.app.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.CustomerWalletHistoryDto;
import com.tm.app.dto.DataFilter;
import com.tm.app.entity.Customer;
import com.tm.app.entity.CustomerWallet;
import com.tm.app.entity.CustomerWalletHistory;
import com.tm.app.entity.User;
import com.tm.app.repo.CustomerRepo;
import com.tm.app.repo.CustomerWalletHistoryRepo;
import com.tm.app.repo.CustomerWalletRepo;
import com.tm.app.repo.UserRepository;
import com.tm.app.service.CustomerWalletHistoryService;

import io.micrometer.common.util.StringUtils;

@Service
public class CustomerWalletHistoryServiceImpl implements CustomerWalletHistoryService {

	@Autowired
	private CustomerWalletHistoryRepo customerWalletHistoryRepo;

	@Autowired
	private CustomerWalletRepo customerWalletRepo;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CustomerRepo customerRepo;

	@Override
	public CustomerWalletHistory saveCustomerWalletHistory(CustomerWalletHistoryDto customerWalletHistoryDto) {
		CustomerWalletHistory customerWalletHistory = new CustomerWalletHistory();
		BeanUtils.copyProperties(customerWalletHistoryDto, customerWalletHistory);
		customerWalletHistory = customerWalletHistoryRepo.save(customerWalletHistory);
		return customerWalletHistory;
	}

	@Override
	public List<CustomerWalletHistory> getCustomerWalletHistorys() {
		return customerWalletHistoryRepo.findAll();
	}

	@Override
	public CustomerWalletHistory getCustomerWalletHistoryById(Long id) {
		return customerWalletHistoryRepo.findById(id).orElseThrow();
	}

	@Override
	public void deleteCustomerWalletHistoryById(Long id) {
		customerWalletHistoryRepo.deleteById(id);
	}

	@Override
	public CustomerWalletHistory updateCustomerWalletHistory(Long id,
			CustomerWalletHistoryDto customerWalletHistoryDto) {
		CustomerWalletHistory customerWalletHistory = customerWalletHistoryRepo.findById(id).orElseThrow();
		BeanUtils.copyProperties(customerWalletHistoryDto, customerWalletHistory);
		return customerWalletHistoryRepo.save(customerWalletHistory);
	}

	@Override
	public Page<CustomerWalletHistory> getCustomerWalletHistoryByWalletId(Long walletId, DataFilter dataFilter,String fromDate,String toDate) {
		CustomerWallet customerWallet = customerWalletRepo.findById(walletId).orElseThrow();
		if (StringUtils.isNotEmpty(fromDate) && StringUtils.isNotEmpty(toDate)) {
			return customerWalletHistoryRepo.getCustomerWalletHistoryByDate(customerWallet,
					PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
							Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())),
					fromDate, toDate, dataFilter.getSearch());
		}else {
		return customerWalletHistoryRepo.getDataByCustomerWallet(customerWallet,
				PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
						Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())),
				dataFilter.getSearch());
		}
	}

	@Override
	public Page<CustomerWalletHistory> getCustomerWalletHistory(DataFilter dataFilter, Long id, String fromDate,
			String toDate) {
		if (Objects.nonNull(id)) {
			User user = userRepository.findById(id).orElseThrow();
			Customer customer = customerRepo.findById(user.getUserId()).orElseThrow();
			if (StringUtils.isNotEmpty(fromDate) && StringUtils.isNotEmpty(toDate)) {
				return customerWalletHistoryRepo.getCustomerWalletHistoryWithDateFilter(customer,
						PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
								Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())),
						fromDate, toDate, dataFilter.getSearch());
			} else {
				return customerWalletHistoryRepo
						.getCustomerWalletHistory(customer,
								PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
										Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())),
								dataFilter.getSearch());
			}
		}
		return null;
	}
}