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
@Subselect(value = "select DATE_TRUNC('week', updated_at) AS date,count(id) as Leads from t_lead_generation  where updated_at >= CURRENT_DATE - interval '7 days' GROUP BY DATE_TRUNC('week', updated_at) ORDER BY DATE_TRUNC('week', updated_at) ASC;")
public class WeeklyLeadGeneration {
	
	@Id
	
	private Date date;
	private Integer leads;

}
