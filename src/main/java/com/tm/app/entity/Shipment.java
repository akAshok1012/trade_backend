package com.tm.app.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.UpdateTimestamp;

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
@Table(name = "t_shipment")
public class Shipment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "sales_id")
	private Integer salesId;
	
	@OneToOne
	@JoinColumn(name = "order_item_id")
	private OrderItem orderItem;

	@Column(name = "shipped_quantity")
	private Integer shippedQuantity;

	@Column(name = "balance_quantity")
	private Integer balanceQuantity;

	@Enumerated(EnumType.STRING)
	@Column(name = "shipment _status")
	private ShipmentStatus shipmentStatus;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@Column(name = "updated_by")
	private String updatedBy;

}