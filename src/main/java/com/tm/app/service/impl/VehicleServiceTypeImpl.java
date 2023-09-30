package com.tm.app.service.impl;


import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.VehicleServiceTypeDto;
import com.tm.app.entity.VehicleServiceType;
import com.tm.app.repo.VehicleServiceTypeRepo;
import com.tm.app.service.VehicleServiceTypeService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class VehicleServiceTypeImpl implements VehicleServiceTypeService {
	
	@Autowired
	private VehicleServiceTypeRepo vehicleServiceTypeRepo;
	
	
	@Override
	public VehicleServiceType saveVehicleServiceType(VehicleServiceTypeDto vehicleServiceTypeDto) {
		log.info("[VEHICLE_SERVICE_TYPE] saveVehicleServiceType starts");
		try {
			if(vehicleServiceTypeRepo.existsByServiceTypeEqualsIgnoreCase(vehicleServiceTypeDto.getServiceType())) {
				throw new RuntimeException("Service type already exists!");
			}
			VehicleServiceType vehicleServiceType = new VehicleServiceType();
			BeanUtils.copyProperties(vehicleServiceTypeDto, vehicleServiceType);
			return vehicleServiceTypeRepo.save(vehicleServiceType);
		} catch (Exception e) {
			log.error("[VEHICLE] adding vehicleServiceType failed", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public List<VehicleServiceType> getVehicleServiceType() {
		return vehicleServiceTypeRepo.findAll();
	}

	@Override
	public VehicleServiceType getVehicleServiceTypeById(Long id) {
		return vehicleServiceTypeRepo.findById(id).orElseThrow();
	}

	@Override
	public VehicleServiceType updateVehicleServiceType(Long id, VehicleServiceTypeDto vehicleServiceTypeDto) {
		log.info("[VEHICLE_SERVICE_TYPE] updateVehicleServiceType starts");
		try {
			VehicleServiceType vehicleServiceType = vehicleServiceTypeRepo.findById(id).orElseThrow();
			if(vehicleServiceTypeRepo.existsByServiceTypeEqualsIgnoreCase(vehicleServiceTypeDto.getServiceType()) && !vehicleServiceType.getServiceType().equalsIgnoreCase(vehicleServiceTypeDto.getServiceType())) {
					throw new RuntimeException("Service type already exists!");	
				
			}
			BeanUtils.copyProperties(vehicleServiceTypeDto, vehicleServiceType);
			return vehicleServiceTypeRepo.save(vehicleServiceType);
		} catch (Exception e) {
			log.error("[VEHICLE] updating vehicleServiceType failed", e);
			throw new RuntimeException(e.getMessage());
		}
	}
	
	@Override
	public void deleteVehicleServiceTypeById(Long id) {
		try {
			vehicleServiceTypeRepo.deleteById(id);
		} catch (Exception e) {
			log.error("[VEHICLE] deleting vehicleServiceType failed", e);
			throw new RuntimeException("Deleting vehicleServiceType failed");
		}
	}

	@Override
	public Page<VehicleServiceType> getVehicleServiceTypeList(DataFilter dataFilter) {
		return vehicleServiceTypeRepo.findByServiceTypeLikeIgnoreCase(dataFilter.getSearch(),
				PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
						Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}
}