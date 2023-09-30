package com.tm.app.dto;

import lombok.Data;

@Data
public class CustomerUserDto {

	private Long user;
	private String name;
	private String email;
	private Long phoneNumber;
	private String organization;
	private String address;
	private String userName;
}
