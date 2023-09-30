package com.tm.app.entity;

import com.tm.app.enums.ServiceType;

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
@Table(name = "t_vehicle_service_details")
public class VehicleServiceDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "service_item")
	private ServiceItem serviceItem;

	@Enumerated(EnumType.STRING)
	@Column(name = "service_type")
	private ServiceType serviceType;

	@Column(name = "cost")
	private Long cost;

}
