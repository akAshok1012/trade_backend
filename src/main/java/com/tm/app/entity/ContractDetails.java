package com.tm.app.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.UpdateTimestamp;

import com.tm.app.enums.ContractStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name = "t_contract_details")
public class ContractDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "contract_name")
	private String contractName;

	@OneToOne
	@JoinColumn(name = "contractor_id")
	private Contractor contractor;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "contract_amount")
	private Float contractAmount;

	@ManyToMany
	@JoinTable(name = "t_contract_details_employee", joinColumns = @JoinColumn(name = "contract_details_id"), inverseJoinColumns = @JoinColumn(name = "contract_employee_id")) 
	private List<ContractEmployee> contractEmployees;

	@Column(name = "notes")
	private String notes;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "contract_status")
	private ContractStatus contractStatus;
	
	@UpdateTimestamp
	@Column(name = "update_at")
	private Timestamp updatedAt;

	@Column(name = "updated_by")
	private String updatedBy;
	
	@Transient
	private String contractEmployeesString;

}