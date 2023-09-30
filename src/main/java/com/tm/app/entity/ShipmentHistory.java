package com.tm.app.entity;

import java.sql.Date;
import java.sql.Timestamp;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.context.annotation.Lazy;

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
@Table(name = "t_shipment_history")
public class ShipmentHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@Lazy
	@JoinColumn(name = "shipment_id")
	private Shipment shipment;

	@Column(name = "carrier")
	private String carrier;

	@Column(name = "tracking_number")
	private String trackingNumber;

	@Column(name = "shipment_date")
	private Date shipmentDate;

	@Column(name = "shipped_quantity")
	private Integer shippedQuantity;
	
	@Column(name = "remarks")
	private String remarks;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@Column(name = "updated_by")
	private String updatedBy;
}