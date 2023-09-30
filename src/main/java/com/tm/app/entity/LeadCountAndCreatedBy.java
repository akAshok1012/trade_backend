package com.tm.app.entity;

import org.hibernate.annotations.Subselect;
import org.springframework.data.annotation.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
@Immutable
@Subselect(value = "select count(id) as leads_count,updated_by from t_lead_generation group by updated_by")
public class LeadCountAndCreatedBy {
	
	@Id
	
	private Integer leadsCount;
	private String updatedBy;

}
