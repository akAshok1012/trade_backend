package com.tm.app.entity;

import java.sql.Date;
import java.sql.Timestamp;

import org.hibernate.annotations.UpdateTimestamp;

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
@Table(name = "t_vehicle_service_history")
public class VehicleServiceHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "vehicle_details")
	private VehicleDetails vehicleDetails;

	@OneToOne
	@JoinColumn(name="service_item")
	private ServiceItem serviceItem;

	@Enumerated(EnumType.STRING)
	@Column(name = "service_type")
	private ServiceType serviceType;
	
	@Column(name = "service_provider")
	private String serviceProvider;

	@Column(name = "cost")
	private Long cost;

	@Column(name = "notes")
	private String notes;
	
	@Column(name = "current_service")
	private Date currentService;
	
	@Column(name = "next_service")
	private Date nextService;

	@UpdateTimestamp
	@Column(name = "update_at")
	private Timestamp updatedAt;

	@Column(name = "updated_by")
	private String updatedBy;
}
