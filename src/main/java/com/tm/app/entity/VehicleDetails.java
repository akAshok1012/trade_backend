package com.tm.app.entity;

import java.sql.Date;
import java.sql.Timestamp;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "t_vehicle_details")
public class VehicleDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "vehicle_type")
	private VehicleType vehicleType;
	
	@Column(name = "vehicle_registration_number",unique = true)
	private String vehicleRegistrationNumber;
	
	@Column(name = "veicle_make")
	private String vehicleMake;
	
	@Column(name = "vehicle_model")
	private String vehicleModel;
	
	@Column(name = "chassisNumber",unique = true)
	private String chassisNumber;
	
	@Column(name = "rcNumber")
	private String rcNumber;
	
	@Column(name = "insuranceCompanyName")
	private String insuranceCompanyName;
	
	@Column(name = "policyNo")
	private String policyNo;
	
	@Column(name = "insuranceStartDate")
	private Date insuranceStartDate;
	
	@Column(name = "insuranceEndDate")
	private Date insuranceEndDate;
	
	@Column(name = "fastTag")
	private String fastTag;
	
	@Column(name = "purchase_date")
	private Date purchaseDate;
	
	@Column(name = "purchase_price")
	private Float purchasePrice;
	
	@UpdateTimestamp
	@Column(name = "update_at")
	private Timestamp updatedAt;
	
	@Column(name = "updatedBy")
	private String updatedBy;

}
