package com.tm.app.service.impl;

import static com.tm.app.utils.CacheableConstants.CUSTOMER;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.CustomerDto;
import com.tm.app.dto.CustomerIdNameDto;
import com.tm.app.dto.CustomerUserDto;
import com.tm.app.dto.DataFilter;
import com.tm.app.entity.Customer;
import com.tm.app.entity.CustomerWallet;
import com.tm.app.entity.User;
import com.tm.app.enums.CustomerType;
import com.tm.app.repo.CreditPaymentTrackRepo;
import com.tm.app.repo.CustomerRepo;
import com.tm.app.repo.CustomerWalletRepo;
import com.tm.app.repo.UserRepository;
import com.tm.app.service.CustomerService;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private CreditPaymentTrackRepo creditTrackRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CustomerWalletRepo customerWalletRepo;

	@Override
	@CacheEvict(value = CUSTOMER, allEntries = true)
	public Customer saveCustomer(CustomerDto customerDto) {
		Customer customer = new Customer();
		try {
			if (StringUtils.isNotEmpty(customerDto.getEmail()) && customerRepo.existsByEmail(customerDto.getEmail())) {
				throw new RuntimeException("Email already exists");
			}
			if (Objects.nonNull(customerDto.getPhoneNumber())
					&& customerRepo.existsByPhoneNumber(customerDto.getPhoneNumber())) {
				throw new RuntimeException("Phone number already exists");
			}
			if (StringUtils.isNotEmpty(customerDto.getGstNo()) && customerRepo.existsByGstNo(customerDto.getGstNo())) {
				throw new RuntimeException("Gst Number  already exists");
			}
			if (StringUtils.isNotEmpty(customerDto.getPanNo()) && customerRepo.existsByPanNo(customerDto.getPanNo())) {
				throw new RuntimeException("Pan Number  already exists");
			}
			BeanUtils.copyProperties(customerDto, customer);
			customer = customerRepo.save(customer);
			//add wallet for customer
			addWalletForCustomer(customer);
		} catch (Exception e) {
			log.error("[CUSTOMER] adding customer failed", e);
			throw new RuntimeException(e.getMessage());
		}
		return customer;
	}

	@Override
	@Cacheable(value = CUSTOMER)
	public List<Customer> getCustomers() {
		return customerRepo.findAll();
	}

	@Override
	@Cacheable(value = CUSTOMER, key = "#id")
	public Customer getCustomerById(Long id) {
		return customerRepo.findById(id).orElseThrow();
	}

	@Override
	@CacheEvict(value = CUSTOMER, allEntries = true)
	public Customer updateCustomer(Long id, CustomerDto customerDto) {
		Customer customer = customerRepo.findById(id).orElseThrow();
		try {
			if (StringUtils.isNotEmpty(customerDto.getEmail()) && customerRepo.existsByEmail(customerDto.getEmail())
					&& !customer.getEmail().equals(customerDto.getEmail())) {
				throw new RuntimeException("Email already exists");
			}
			if (Objects.nonNull(customerDto.getPhoneNumber())
					&& customerRepo.existsByPhoneNumber(customerDto.getPhoneNumber())
					&& !customer.getPhoneNumber().equals(customerDto.getPhoneNumber())) {
				throw new RuntimeException("PhoneNumber  already exists");
			}
			if (StringUtils.isNotEmpty(customerDto.getPanNo()) && customerRepo.existsByPanNo(customerDto.getPanNo())
					&& !customer.getPanNo().equals(customerDto.getPanNo())) {
				throw new RuntimeException("Pan Number already exists");
			}
			if (StringUtils.isNotEmpty(customerDto.getGstNo()) && customerRepo.existsByGstNo(customerDto.getGstNo())
					&& !customer.getGstNo().equals(customerDto.getGstNo())) {
				throw new RuntimeException("GST Number already exists");
			}
			customer.setName(customerDto.getName());
			customer.setEmail(customerDto.getEmail());
			customer.setPhoneNumber(customerDto.getPhoneNumber());
			customer.setOrganization(customerDto.getOrganization());
			customer.setAddress(customerDto.getAddress());
			customer.setGstNo(customerDto.getGstNo());
			customer.setPanNo(customerDto.getPanNo());
			customer.setCustomerType(customerDto.getCustomerType());
			customer.setFollowUpDays(customerDto.getFollowUpDays());
			customer.setUpdatedBy(customerDto.getUpdatedBy());
			customer = customerRepo.save(customer);
			
			//if a customer has no wallet 
			addWalletForCustomerNotExists(customer);
			creditTrackRepo.truncateTable();
			creditTrackRepo.saveCreditPaymentTrack();

		} catch (Exception e) {
			log.error("[CUSTOMER] updating customer failed", e);
			throw new RuntimeException(e.getMessage());
		}
		return customer;
	}

	/**
	 * add Wallet if Not Exists
	 * @param customer
	 */
	private void addWalletForCustomerNotExists(Customer customer) {
		if(!customerWalletRepo.existsByCustomer(customer)) {
			addWalletForCustomer(customer);
		}
	}
	
	/**
	 * add Wallet for Customer
	 * @param customer
	 */
	private void addWalletForCustomer(Customer customer) {
		CustomerWallet customerWallet = new CustomerWallet();
		customerWallet.setBalance(0F);
		customerWallet.setCustomer(customer);
		customerWallet.setUpdatedBy(customer.getUpdatedBy());
		customerWallet = customerWalletRepo.save(customerWallet);
	}

	@Override
	@CacheEvict(value = CUSTOMER, key = "#id", allEntries = true)
	public void deleteCustomerById(Long id) {
		try {
			customerRepo.deleteById(id);
		} catch (Exception e) {
			log.error("[CUSTOMER] deleting customer failed", e);
			throw new RuntimeException("Deleting customer failed");
		}
	}

	@Override
	public List<CustomerIdNameDto> getCustomerIdName() {
		return customerRepo.findByCustomerIdName();
	}

	@Override
	@Cacheable(value = CUSTOMER)
	public Page<Customer> getCustomerList(DataFilter dataFilter) {
		return customerRepo.findByNameLikeIgnoreCase(dataFilter.getSearch(), PageRequest.of(dataFilter.getPage(),
				dataFilter.getSize(), Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}

	@Override
	public Customer getCustomerUserId(Long id) {
		User user = userRepo.findById(id).orElseThrow();
		return customerRepo.findByUserId(user.getUserId());
	}

	@Override
	@CacheEvict(value = CUSTOMER, key = "#id", allEntries = true)
	public Customer updateCustomerUser(Long id, CustomerUserDto customerUserDto) {
		try {
			User user = userRepo.findById(id).orElseThrow();
			Customer customer = customerRepo.findById(user.getUserId()).orElseThrow();
			BeanUtils.copyProperties(customerUserDto, customer);
			return customerRepo.save(customer);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("[CUSTOMER] Customer Updated Failed", e);
			throw new RuntimeException("Customer Updated Failed");
		}
	}

	@Override
	public Page<Customer> getCustomerTypeList(CustomerType customerType, DataFilter dataFilter) {
		if (Objects.nonNull(customerType)) {
			return customerRepo.getCustomerTypeList(customerType, dataFilter.getSearch(),
					PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
							Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
		} else {
			return customerRepo.findByNameLikeIgnoreCase(dataFilter.getSearch(), PageRequest.of(dataFilter.getPage(),
					dataFilter.getSize(), Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
		}
	}
}