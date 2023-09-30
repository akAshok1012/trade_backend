package com.tm.app.enums;

import jakarta.validation.constraints.NotEmpty;

public enum CustomerType {

	PERMANENT, TEMPORARY;

	@NotEmpty
	public static CustomerType parseCustomerType(String customerType) {
		return PERMANENT.name().equalsIgnoreCase(customerType) ? PERMANENT : TEMPORARY;
	}
}
