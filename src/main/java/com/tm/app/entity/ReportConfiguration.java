package com.tm.app.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "t_report_configuration")
public class ReportConfiguration {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="template_name",unique = true)
	private String templateName;
	
	@Column(name="header_fields")
	private List<String> headerFields;
	
	@Column(name="table_name")
	private String tableName;
	
	@Column(name="unique_fields")
	private List<String> uniqueFields;
	
	@Column(name="mandatory_fields")
	private List<String> mandatoryFields;
}
