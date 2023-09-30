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
@Table(name = "t_vehicle_sevice_type")
public class VehicleServiceType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "service_type")
	private String serviceType;

	@Column(name = "vehicle_description")
	private String description;

	@UpdateTimestamp
	@Column(name = "update_at")
	private Timestamp updatedAt;

	@Column(name = "updatedBy")
	private String updatedBy;

}
