package com.tm.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.tm.app.entity.Customer;
import com.tm.app.entity.Employee;
import com.tm.app.entity.User;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown =true)
@JsonInclude(value = Include.NON_NULL)
public class UserListDto {

	private User user;
	private Customer customer;
	private Employee employee;
}
