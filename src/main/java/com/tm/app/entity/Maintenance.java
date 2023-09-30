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
@Table(name = "t_machine_maintenance")
public class Maintenance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@Lazy
	@JoinColumn(name = "machinery_id")
	private Machinery machineryId;
	
	@Column(name = "maintenance_date")
	private Date maintenanceDate; 
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "cost")
	private Float cost;
	
	@Column(name = "technician_name")
	private String technicianName;
	
	@Column(name = "technician_phone_no")
	private String technicianPhoneNo;
	
	@Column(name = "next_maintenance_date")
	private Date nextMaintenanceDate; 
	
	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;
	
	@Column(name = "updated_by")
	private String updatedBy;
}
