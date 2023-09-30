package com.tm.app.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name = "t_vehicle_service")
public class VehicleService {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "vehicle_service_id")
	private List<VehicleServiceDetails> vehicleServiceDetails;

	@OneToOne
	@JoinColumn(name = "vehicle_details")
	private VehicleDetails vehicleDetails;

	@Column(name = "service_provider")
	private String serviceProvider;

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

	@Transient
	private String vehicleTypeName;

}
