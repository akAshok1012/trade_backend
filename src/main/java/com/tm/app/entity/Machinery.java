package com.tm.app.entity;

import java.sql.Date;
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
@Table(name = "t_machine")
public class Machinery {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String description;
	
	@Column(name = "serial_number")
	private String serialNumber;
	
	private String manufacturer;
	
	@Column(name = "model_number")
	private String modelNumber;
	
	@Column(name = "date_of_purchase")
	private Date dateOfPurchase;
	
	@Column(name = "purchase_cost")
	private Float purchaseCost;
	
	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;
	
	@Column(name = "updated_by")
	private String updatedBy;
}
