package com.tm.app.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tm.app.dto.DataFilter;
import com.tm.app.dto.MaintenanceDto;
import com.tm.app.entity.Maintenance;
import com.tm.app.repo.MaintenanceRepo;
import com.tm.app.service.MaintenanceService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MaintenanceServiceImpl implements MaintenanceService {

	@Autowired
	private MaintenanceRepo maintenanceRepo;
	
	@Autowired
	private JwtService jwtService;

	@Override
	@Transactional
	public Maintenance saveMaintenance(MaintenanceDto maintenanceDto,HttpServletRequest request)throws JsonProcessingException {
		Maintenance maintenance = new Maintenance();
		try {
			String authorizationHeaderValue = request.getHeader("Authorization");

			if (authorizationHeaderValue != null && authorizationHeaderValue.startsWith("Bearer")) {
				authorizationHeaderValue = authorizationHeaderValue.substring(7, authorizationHeaderValue.length());
			}
			String userName = jwtService.extractJwtObject(authorizationHeaderValue).getUserName();
			
			maintenance.setUpdatedBy(userName);
			BeanUtils.copyProperties(maintenanceDto, maintenance);
			maintenance = maintenanceRepo.save(maintenance);
		} catch (Exception e) {
			log.error("[MAINTENANCE] adding maintenance failed", e);
			throw new RuntimeException("Adding maintenance failed");
		}
		return maintenance;
	}

	@Override
	public List<Maintenance> getMaintenances() {
		return maintenanceRepo.findAll();
	}

	@Override
	public Maintenance getMaintenanceById(Long id) {
		return maintenanceRepo.getMaintenanceById(id);
	}

	@Override
	@Transactional
	public Maintenance updateMaintenance(Long id, MaintenanceDto maintenanceDto,HttpServletRequest request)throws JsonProcessingException {
		Maintenance maintenance = maintenanceRepo.findById(id).orElseThrow();
		try {
			String authorizationHeaderValue = request.getHeader("Authorization");

			if (authorizationHeaderValue != null && authorizationHeaderValue.startsWith("Bearer")) {
				authorizationHeaderValue = authorizationHeaderValue.substring(7, authorizationHeaderValue.length());
			}

			String userName = jwtService.extractJwtObject(authorizationHeaderValue).getUserName();
			maintenance.setMachineryId(maintenanceDto.getMachineryId());
			maintenance.setDescription(maintenanceDto.getDescription());
			maintenance.setMaintenanceDate(maintenanceDto.getMaintenanceDate());
			maintenance.setCost(maintenanceDto.getCost());
			maintenance.setNextMaintenanceDate(maintenanceDto.getNextMaintenanceDate());
			maintenance.setTechnicianName(maintenanceDto.getTechnicianName());
			maintenance.setTechnicianPhoneNo(maintenanceDto.getTechnicianPhoneNo());
			maintenance.setUpdatedBy(userName);
			maintenance = maintenanceRepo.save(maintenance);
		} catch (Exception e) {
			log.error("[MAINTENANCE] updating maintenance failed", e);
			throw new RuntimeException("Updating maintenance failed");
		}
		return maintenance;
	}

	@Override
	@Transactional
	public void deleteMaintenanceById(Long id) {
		try {
			maintenanceRepo.deleteById(id);
		} catch (Exception e) {
			log.error("[MAINTENANCE] deleting maintenance failed", e);
			throw new RuntimeException("Deleting maintenance failed");
		}
	}

	@Override
	public Page<Maintenance> getMaintenanceList(DataFilter dataFilter) {
		return maintenanceRepo.findBySerialNumber(dataFilter.getSearch(),
				PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
						Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}
}