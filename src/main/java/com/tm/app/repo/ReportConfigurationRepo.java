package com.tm.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.ReportConfiguration;

@Repository
public interface ReportConfigurationRepo extends JpaRepository<ReportConfiguration, Long>{

	ReportConfiguration findByTemplateName(String templateName);

	ReportConfiguration findByTableName(String name);

}
