package com.tm.app.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.context.annotation.Lazy;

import com.tm.app.enums.ProductionStatus;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "t_production")
public class Production {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "production_id")
	private List<ProductionDetails> productionDetails;

	@OneToOne
	@Lazy
	@JoinColumn(name = "item_id")
	private ItemMaster itemMaster;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private ProductionStatus status;

	@Column(name = "supervisor")
	private String supervisor;

	@Column(name = "notes")
	private String notes;

	@UpdateTimestamp
	@Column(name = "update_at")
	private Timestamp updatedAt;

	@Column(name = "updated_by")
	private String updatedBy;
}
