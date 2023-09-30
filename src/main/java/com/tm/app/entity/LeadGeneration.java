package com.tm.app.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;

import com.tm.app.enums.LeadStatus;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name = "t_lead_generation")
public class LeadGeneration {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phone;

	@Column(name = "location")
	private String location;
	
	@ManyToMany
	@JoinTable(joinColumns = @JoinColumn(name = "item_master"), inverseJoinColumns = @JoinColumn(name = "interested_In"))
	private List<ItemMaster> intrestedIn;

	@Column(name = "referral_source_type")
	private String referralSourceType;

	@Column(name = "referral_name")
	private String referralName;

	@Column(name = "referral_phone")
	private String referralPhone;

	@Column(name = "notes")
	private String notes;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private LeadStatus status;
	
	@Column(name = "isSampleProvided")
	private Boolean isSampleProvided;

	@Column(name = "no_of_followups")
	private Integer noOfFollowups;
	
	@Column(name = "followup_date")
	private Date followupDate;
	
	@Column(name = "reference_image", columnDefinition = "bytea")
	@Basic(fetch = FetchType.EAGER)
	@JdbcTypeCode(Types.BINARY)
	private byte[] referenceImage;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@Column(name = "updated_by")
	private String updatedBy;
	
	@Transient
	private String referenceImageString;

}
