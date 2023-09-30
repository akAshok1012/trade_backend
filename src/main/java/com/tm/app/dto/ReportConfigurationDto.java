package com.tm.app.dto;

import java.util.List;

import lombok.Data;
@Data
public class ReportConfigurationDto {
	private String templateName;
	private List<String> headerFields;
	private String tableName;
	private List<String> uniqueFields;
	private List<String> manditoryFields;
}
