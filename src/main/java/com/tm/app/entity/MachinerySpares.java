package com.tm.app.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.UpdateTimestamp;

import com.tm.app.enums.SpareStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "t_machinery_spares")
public class MachinerySpares {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name = "machinery_spares_id")
	private List<MachinerySpareDetails> machinerySpareDetails;

	@Column(name = "service_given_date")
	private Date serviceGivenDate;

	@Column(name = "remarks")
	private String remarks;
	
	@Column(name = "technician_name")
	private String technicianName;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private SpareStatus status;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@Column(name = "updated_by")
	private String updatedBy;
}
