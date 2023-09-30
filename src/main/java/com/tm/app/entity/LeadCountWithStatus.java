package com.tm.app.entity;

import org.hibernate.annotations.Subselect;
import org.springframework.data.annotation.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
@Immutable
@Subselect(value = "select count(id) as leads_count,status from t_lead_generation group by status;")
public class LeadCountWithStatus {

	
	
	private Integer leadsCount;
	@Id
	private String status;

}
