package com.tm.app.dto;

import com.tm.app.enums.CustomerType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

	private Long id;
	private String name;
	private String email;
	private Long phoneNumber;
	private String organization;
	private String address;
	private String gstNo;
	private String panNo;
	private CustomerType customerType;
    private Integer followUpDays;
	private String updatedBy;

}
