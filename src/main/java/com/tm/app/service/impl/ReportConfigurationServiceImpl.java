package com.tm.app.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tm.app.dto.ReportConfigurationDto;
import com.tm.app.entity.ReportConfiguration;
import com.tm.app.repo.ReportConfigurationRepo;
import com.tm.app.service.ReportConfigurationService;

@Service
public class ReportConfigurationServiceImpl implements ReportConfigurationService {

	@Autowired
	private ReportConfigurationRepo reportConfigurationRepo;

	@Override
	public ReportConfiguration addReportConfiguration(ReportConfigurationDto configurationDto) {
		ReportConfiguration reportConfiguration = new ReportConfiguration();
		BeanUtils.copyProperties(configurationDto, reportConfiguration);
		return reportConfigurationRepo.save(reportConfiguration);
	}

	@Override
	public List<ReportConfiguration> getAllReportConfiguration() {
		return reportConfigurationRepo.findAll();
	}

	@Override
	public ReportConfiguration getReportConfigurationByTemplateName(String templateName) {
		return reportConfigurationRepo.findByTemplateName(templateName);
	}

	@Override
	public void deleteReportConfigurationById(Long id) {
		reportConfigurationRepo.deleteById(id);
	}
}