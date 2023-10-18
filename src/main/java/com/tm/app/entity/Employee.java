package com.tm.app.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.regex.Pattern;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.context.annotation.Lazy;

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
@Table(name = "t_employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @Column(name = "designation")
    private String designation;

    @NotNull
    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @OneToOne
    @Lazy
    @JoinColumn(name = "department_id")
    private EmployeeDepartment employeeDepartment;

    @NotNull
    @Column(name = "date_of_joining")
    private Date dateOfJoining;

    @Column(name = "pf_no")
    private String pfNumber;

    @Column(name = "esi_no")
    private String esiNumber;

    @Column(name = "pan_no")
    private String panNumber;

    @Column(name = "uan_no")
    private Long uanNumber;

    @NotNull
    @Column(name = "aadhaar_no", unique = true)
    private Long aadhaarNumber;

    @NotNull
    @Column(name = "phone_number", unique = true)
    private Long phoneNumber;

    @Column(name = "email")
    private String email;

    @NotEmpty
    @Column(name = "address")
    private String address;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @NotEmpty
    @Column(name = "updated_by")
    private String updatedBy;

    @Transient
    private String errorDescription;
    
    @Transient
    private String userName;

    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^[0-9]{10}$");
    private static final Pattern AADHAAR_NUMBER_PATTERN = Pattern.compile("^[0-9]{12}$");
    private static final Pattern ESI_NUMBER_PATTERN = Pattern.compile("^[0-9]{17}$");
    private static final Pattern PAN_NUMBER_PATTERN = Pattern.compile("^[A-Z]{5}[0-9]{4}[A-Z]{1}$");
    private static final Pattern PF_NUMBER_PATTERN = Pattern.compile("^[A-Z]{2}/[0-9]{5}/[0-9]{7}$");
    private static final Pattern UAN_NUMBER_PATTERN = Pattern.compile("[0-9]{12}");

    public Employee(String name, String designation, Date dateOfBirth, EmployeeDepartment employeeDepartment,
	    Date dateOfJoining, String pfNumber, String esiNumber, String panNumber, String uanNumber,
	    String aadhaarNumber, String phoneNumber, String email, String address, String updatedBy) {

	if (StringUtils.isEmpty(phoneNumber)) {
	    throw new IllegalArgumentException("PhoneNumber should not be empty");
	}
	if (StringUtils.isEmpty(aadhaarNumber)) {
	    throw new IllegalArgumentException("AadhaarNumber should not be empty");
	}
	if (StringUtils.isEmpty(name)) {
	    throw new IllegalArgumentException("Name should not be empty");
	}

	if (StringUtils.isEmpty(designation)) {
	    throw new IllegalArgumentException("Designation should not be empty");
	}

	if (Objects.isNull(dateOfBirth)) {
	    throw new IllegalArgumentException("DateOfBirth should not be empty");
	}

	if (Objects.isNull(dateOfJoining)) {
	    throw new IllegalArgumentException("DateOfJoining should not be empty");
	}

	if (StringUtils.isEmpty(address)) {
	    throw new IllegalArgumentException("Address should not be empty");
	}
	if (StringUtils.isEmpty(updatedBy)) {
	    throw new IllegalArgumentException("UpdatedBy should not be empty");
	}
	if (StringUtils.isNotEmpty(phoneNumber) && (!isValidPhoneNumber(phoneNumber))) {
	    throw new IllegalArgumentException("Invalid phone number.");
	}
	if (StringUtils.isNotEmpty(aadhaarNumber) && (!isValidAadhaarNumber(aadhaarNumber))) {
	    throw new IllegalArgumentException("Invalid Aadhaar number.");
	}
	if (StringUtils.isNotEmpty(esiNumber) && (!isValidEsiNumber(esiNumber))) {
	    throw new IllegalArgumentException("Invalid ESI number.");
	}
	if (StringUtils.isNotEmpty(panNumber) && (!isValidPanNumber(panNumber))) {
	    throw new IllegalArgumentException("Invalid PAN number.");
	}
	if (StringUtils.isNotEmpty(pfNumber) && (!isValidPfNumber(pfNumber))) {
	    throw new IllegalArgumentException("Invalid PF number.");
	}
	if (StringUtils.isNotEmpty(uanNumber) && (!isValidUanNumber(uanNumber))) {
	    throw new IllegalArgumentException("Invalid UAN number.");
	}

	this.name = name;
	this.designation = designation;
	this.dateOfBirth = dateOfBirth;
	this.employeeDepartment = employeeDepartment;
	this.dateOfJoining = dateOfJoining;
	this.pfNumber = pfNumber;
	this.esiNumber = esiNumber;
	this.panNumber = panNumber;
	this.uanNumber = StringUtils.isNotEmpty(uanNumber) ? Long.parseLong(uanNumber) : null;
	this.aadhaarNumber = StringUtils.isNotEmpty(aadhaarNumber) ? Long.parseLong(aadhaarNumber) : null;
	this.phoneNumber = StringUtils.isNotEmpty(phoneNumber) ? Long.parseLong(phoneNumber) : null;
	this.email = email;
	this.address = address;
	this.updatedBy = updatedBy;
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
	return StringUtils.isNotEmpty(phoneNumber) && PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches();
    }

    private boolean isValidAadhaarNumber(String aadhaarNumber) {
	return StringUtils.isNotEmpty(aadhaarNumber) && AADHAAR_NUMBER_PATTERN.matcher(aadhaarNumber).matches();
    }

    private boolean isValidEsiNumber(String esiNumber) {
	return StringUtils.isNotEmpty(esiNumber) && ESI_NUMBER_PATTERN.matcher(esiNumber).matches();
    }

    private boolean isValidPanNumber(String panNumber) {
	return StringUtils.isNotEmpty(panNumber) && PAN_NUMBER_PATTERN.matcher(panNumber).matches();
    }

    private boolean isValidPfNumber(String pfNumber) {
	return StringUtils.isNotEmpty(pfNumber) && PF_NUMBER_PATTERN.matcher(pfNumber).matches();
    }

    private boolean isValidUanNumber(String uanNumber) {
	return StringUtils.isNotEmpty(uanNumber) && UAN_NUMBER_PATTERN.matcher(uanNumber).matches();
    }

    public Employee() {
    }
}