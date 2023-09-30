package com.tm.app.entity;

import java.sql.Date;
import java.sql.Timestamp;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.context.annotation.Lazy;

import com.tm.app.enums.ShipmentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "t_shipment_details")
public class ShipmentDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@Lazy
	@JoinColumn(name = "shipment_id")
	private Shipment shipmentId;
	
	private Integer salesId;
	
	@Column(name = "carrier")
	private String carrier;
	
	@Column(name = "shipment_date")
	private Date shipmentDate;
	
	@Column(name = "tracking_number")
	private Integer trackingNumber;
	
	@Column(name = "item")
	private Long item;
	
	private Long orderItemId;
	
	@Column(name = "order_quantity")
	private Integer orderQuantity;
	
	@Column(name = "shipped_quantity")
	private Integer shippedQuantity;
	
	@Column(name = "balance_quantity")
	private Integer balanceQuantity;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private ShipmentStatus status;
	
	@Column(name = "updated_by")
	private String updatedBy;
	
	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;
	
	@OneToOne
	@JoinColumn(name = "unit_of_measure")
	private UnitOfMeasure unitOfMeasure;

}
