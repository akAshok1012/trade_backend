package com.tm.app.entity;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

import org.hibernate.annotations.UpdateTimestamp;

import com.tm.app.enums.CustomerType;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "t_customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Column(name = "client_name")
	private String name;

	@Column(name = "email")
	private String email;

	@NotNull
	@Column(name = "phone_number", unique = true)
	private Long phoneNumber;

	@NotEmpty
	@Column(name = "organization")
	private String organization;

	@NotEmpty
	@Column(name = "address")
	private String address;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@NotEmpty
	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "gst_no")
	private String gstNo;

	@Column(name = "pan_no")
	private String panNo;

	@Column(name = "is_notification_enabled")
	private Boolean isNotificationEnabled;

	@Enumerated(EnumType.STRING)
	@Column(name = "customer_type")
	private CustomerType customerType;

	@Column(name = "follow_up_days")
	private Integer followUpDays;

	@Transient
	private String errorDescription;

	private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^[0-9]{10}$");
	private static final Pattern PAN_NUMBER_PATTERN = Pattern.compile("^[A-Z]{5}[0-9]{4}[A-Z]{1}$");
	private static final Pattern GST_NUMBER_PATTERN = Pattern
			.compile("^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$");
	private static final String[] CUSTOMER_TYPE = { "PERMANANT", "TEMPORARY" };

	public Customer(String name, String email, String phoneNumber, String organization, String address,
			String updatedBy, String gstNo, String panNo, String customerType, String followUpDays) {
		if (Objects.isNull(phoneNumber)) {
			throw new IllegalArgumentException("PhoneNumber should not be empty");
		}
		if (StringUtils.isEmpty(name)) {
			throw new IllegalArgumentException("Client Name should not be empty");
		}
		if (StringUtils.isEmpty(organization)) {
			throw new IllegalArgumentException("Organization should not be empty");
		}
		if (StringUtils.isEmpty(address)) {
			throw new IllegalArgumentException("Address should not be empty");
		}
		if (StringUtils.isEmpty(customerType)) {
			throw new IllegalArgumentException("Customer Type should be (PERMANANT or TEMPORARY) it should not be Empty");
		}
		if (StringUtils.isEmpty(followUpDays)) {
			throw new IllegalArgumentException("Follow Up Days should not be empty");
		}
		if (StringUtils.isEmpty(updatedBy)) {
			throw new IllegalArgumentException("UpdatedBy should not be empty");
		}
		if (!isValidPhoneNumber(phoneNumber)) {
			throw new IllegalArgumentException("Invalid phoneNumber.");
		}
		if (!isValidCustomerType(customerType)) {
			throw new IllegalArgumentException("Invalid Customer Type.Customer Type should be (PERMANANT or TEMPORARY)");
		}
		if (StringUtils.isNotEmpty(panNo) && (!isValidPanNumber(panNo))) {
			throw new IllegalArgumentException("Invalid PAN number.");
		}
		if (StringUtils.isNotEmpty(gstNo) && (!isValidGstNumber(gstNo))) {
			throw new IllegalArgumentException("Invalid GST number.");
		}

		this.name = name;
		this.email = email;
		this.phoneNumber = StringUtils.isNotEmpty(phoneNumber) ? Long.parseLong(phoneNumber) : null;
		this.organization = organization;
		this.address = address;
		this.updatedBy = updatedBy;
		this.gstNo = gstNo;
		this.panNo = panNo;
		this.customerType = StringUtils.isNotEmpty(customerType) ? CustomerType.parseCustomerType(customerType.toUpperCase()) : null;
		this.followUpDays = StringUtils.isNotEmpty(followUpDays) ? Integer.parseInt(followUpDays) : null;
	}

	public Customer() {

	}

	private boolean isValidGstNumber(String gstNo) {
		return gstNo != null && GST_NUMBER_PATTERN.matcher(gstNo).matches();
	}

	private boolean isValidPanNumber(String panNo) {
		return panNo != null && PAN_NUMBER_PATTERN.matcher(panNo).matches();
	}

	private boolean isValidPhoneNumber(String phoneNumber) {
		return phoneNumber != null && PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches();
	}

	private boolean isValidCustomerType(String customerType) {
		return customerType != null && Arrays.asList(CUSTOMER_TYPE).contains(customerType.toUpperCase());
	}
}