package com.tm.app.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "t_carriers")
public class Carrier {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "carrier_company")
	private String carrierCompany; 
	
	@Column(name = "contact_name")
	private String contactName;
	
	@Column(name = "contact_email")
	private String contactEmail;
	
	@Column(name = "contact_phone")
	private String contactPhone;
	
	@Column(name = "address")
	private String address;
	
	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@Column(name = "updated_by")
	private String updatedBy;
}
