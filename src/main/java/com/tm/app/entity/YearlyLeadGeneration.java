package com.tm.app.entity;

import java.sql.Date;

import org.hibernate.annotations.Subselect;
import org.springframework.data.annotation.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
@Immutable
@Subselect(value = "select DATE_TRUNC('year', updated_at) as date,count(id) as Leads from t_lead_generation where updated_at >= CURRENT_DATE - interval '365 days' group by DATE_TRUNC('year', updated_at)")
public class YearlyLeadGeneration {
	
	@Id
	
	private Date date;
	private Integer leads;

}
