package com.tm.app.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.UpdateTimestamp;

import com.tm.app.enums.ContractorType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "t_contractor")
public class Contractor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "phone_number")
	private Long phoneNumber;

	@Column(name = "email")
	private String email;

	@Column(name = "address")
	private String address;

	@Column(name = "aadhaar_no")
	private Long aadhaarNumber;

	@Column(name = "pan_no")
	private String panNumber;

	@Enumerated(EnumType.STRING)
	@Column(name = "contractor_type ")
	private ContractorType contractorType;

	@Column(name = "notes")
	private String notes;

	@UpdateTimestamp
	@Column(name = "update_at")
	private Timestamp updatedAt;

	@Column(name = "updated_by")
	private String updatedBy;
}
