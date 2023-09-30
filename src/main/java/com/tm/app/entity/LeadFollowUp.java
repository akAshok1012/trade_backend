package com.tm.app.entity;

import java.sql.Date;
import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.tm.app.enums.LeadStatus;

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
@Table(name = "t_lead_follow_up")
public class LeadFollowUp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "lead_id")
	private LeadGeneration leadGeneration;

	@Column(name = "follow_up_datetime")
	private Date followUpDate;

	@Column(name = "notes", length = 1000)
	private String notes;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private LeadStatus status;

	@CreationTimestamp
	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "created_by")
	private String createdBy;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@Column(name = "updated_by")
	private String updatedBy;

}
