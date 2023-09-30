package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tm.app.dto.DataFilter;
import com.tm.app.dto.MaintenanceDto;
import com.tm.app.entity.Maintenance;

import jakarta.servlet.http.HttpServletRequest;

public interface MaintenanceService {

	public Maintenance saveMaintenance(MaintenanceDto maintenanceDto, HttpServletRequest request)throws JsonProcessingException;

	List<Maintenance> getMaintenances();

	public Maintenance getMaintenanceById(Long id);

	public Maintenance updateMaintenance(Long id, MaintenanceDto maintenanceDto, HttpServletRequest request)throws JsonProcessingException;

	public void deleteMaintenanceById(Long id);

	public Page<Maintenance> getMaintenanceList(DataFilter dataFilter);

}
