package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.CustomerDto;
import com.tm.app.dto.CustomerIdNameDto;
import com.tm.app.dto.CustomerUserDto;
import com.tm.app.dto.DataFilter;
import com.tm.app.entity.Customer;
import com.tm.app.enums.CustomerType;

public interface CustomerService {

	public Customer saveCustomer(CustomerDto customerDto);

	public List<Customer> getCustomers();

	public Customer getCustomerById(Long id);

	public void deleteCustomerById(Long id);

	public Customer updateCustomer(Long id, CustomerDto customerDto);

	public List<CustomerIdNameDto> getCustomerIdName();

	public Page<Customer> getCustomerList(DataFilter dataFilter);

	public Customer getCustomerUserId(Long id);

	public Customer updateCustomerUser(Long id, CustomerUserDto customerUserDto);

	public Page<Customer> getCustomerTypeList(CustomerType customerType, DataFilter dataFilter);

}