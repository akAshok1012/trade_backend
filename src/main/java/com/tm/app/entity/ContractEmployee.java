package com.tm.app.entity;

import java.sql.Timestamp;
import java.util.regex.Pattern;

import org.hibernate.annotations.UpdateTimestamp;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "t_contract_employees")
public class ContractEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "phone_number")
    private Long phoneNumber;

    @NotEmpty
    @Column(name = "address")
    private String address;

    @NotNull
    @Column(name = "aadhaar_no")
    private Long aadhaarNumber;

    @Column(name = "notes")
    private String notes;

    @OneToOne
    @JoinColumn(name = "contractor_id")
    private Contractor contractor;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @NotEmpty
    @Column(name = "updated_by")
    private String updatedBy;

    @Transient
    private String errorDescription;

    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^[0-9]{10}$");
    private static final Pattern AADHAAR_NUMBER_PATTERN = Pattern.compile("^[0-9]{12}$");

    public ContractEmployee(String name, String phoneNumber, String address, String aadhaarNumber, String notes,
	    String updatedBy, Contractor contractor) {
	if (StringUtils.isEmpty(phoneNumber)) {
	    throw new IllegalArgumentException("PhoneNumber should not be empty");
	}
	if (StringUtils.isEmpty(aadhaarNumber)) {
	    throw new IllegalArgumentException("AadhaarNumber should not be empty");
	}
	if (StringUtils.isEmpty(name)) {
	    throw new IllegalArgumentException("Name should not be empty");
	}
	if (StringUtils.isEmpty(address)) {
	    throw new IllegalArgumentException("Address should not be empty");
	}
	if (StringUtils.isEmpty(updatedBy)) {
	    throw new IllegalArgumentException("UpdatedBy should not be empty");
	}
	if (!isValidPhoneNumber(phoneNumber)) {
	    throw new IllegalArgumentException("Invalid phone number.");
	}
	if (!isValidAadhaarNumber(aadhaarNumber)) {
	    throw new IllegalArgumentException("Invalid Aadhaar number.");
	}
	this.name = name;
	this.phoneNumber = StringUtils.isNotEmpty(phoneNumber) ? Long.parseLong(phoneNumber) : null;
	this.address = address;
	this.aadhaarNumber = StringUtils.isNotEmpty(aadhaarNumber) ? Long.parseLong(aadhaarNumber) : null;
	this.notes = notes;
	this.contractor = contractor;
	this.updatedBy = updatedBy;
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
	return phoneNumber != null && PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches();
    }

    private boolean isValidAadhaarNumber(String aadhaarNumber) {
	return aadhaarNumber != null && AADHAAR_NUMBER_PATTERN.matcher(aadhaarNumber).matches();
    }

    public ContractEmployee() {
    }
}