package com.tm.app.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class EmployeeUserDto {

	private Long user;
	private String name;
	private Long phoneNumber;
	private Date dateOfBirth;
	private String address;
	private String email;
	private Long aadhaarNumber;
	private String pfNumber;
	private String esiNumber;
	private String panNumber;
	private String firstName;
	private String lastName;
	private String userName;
	private Boolean changeUserName;
}
