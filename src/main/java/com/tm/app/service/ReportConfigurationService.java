package com.tm.app.service;

import java.util.List;

import com.tm.app.dto.ReportConfigurationDto;
import com.tm.app.entity.ReportConfiguration;

public interface ReportConfigurationService {

	ReportConfiguration addReportConfiguration(ReportConfigurationDto configurationDto);

	List<ReportConfiguration> getAllReportConfiguration();

	ReportConfiguration getReportConfigurationByTemplateName(String templateName);

	void deleteReportConfigurationById(Long id);

}