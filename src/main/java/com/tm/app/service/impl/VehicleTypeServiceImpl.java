package com.tm.app.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.VehicleTypeDto;
import com.tm.app.entity.VehicleType;
import com.tm.app.repo.VehicleTypeRepo;
import com.tm.app.service.VehicleTypeService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class VehicleTypeServiceImpl implements VehicleTypeService {

	@Autowired
	private VehicleTypeRepo vehicleTypeRepo;

	@Override
	public VehicleType saveVehicleType(VehicleTypeDto vehicleTypeDto) {
		log.info("[VEHICLE_TYPE] saveVehicleType starts");
		VehicleType vehicle = new VehicleType();
		if (vehicleTypeRepo.existsByVehicleTypeNameEqualsIgnoreCase(vehicleTypeDto.getVehicleTypeName())) {
			throw new RuntimeException("Vehicle Type Name already exists!");
		}
		BeanUtils.copyProperties(vehicleTypeDto, vehicle);
		return vehicleTypeRepo.save(vehicle);
	}

	@Override
	public List<VehicleType> getVehicleType() {
		return vehicleTypeRepo.findAll();
	}

	@Override
	public VehicleType getVehicleTypeById(Long id) {
		return vehicleTypeRepo.findById(id).orElseThrow();
	}

	@Override
	public VehicleType updateVehicleType(Long id, VehicleTypeDto vehicleTypeDto) {
		log.info("[VEHICLE_TYPE] updateVehicleType starts");
		VehicleType vehicle = vehicleTypeRepo.findById(id).orElseThrow();
		if (vehicleTypeRepo.existsByVehicleTypeNameEqualsIgnoreCase(vehicleTypeDto.getVehicleTypeName())
				&& !vehicle.getVehicleTypeName().equalsIgnoreCase(vehicleTypeDto.getVehicleTypeName())) {
			throw new RuntimeException("Vehicle Type Name already exists!");
		}
		BeanUtils.copyProperties(vehicleTypeDto, vehicle);
		return vehicleTypeRepo.save(vehicle);
	}

	@Override
	public void deleteVehicleTypeById(Long id) {
		vehicleTypeRepo.deleteById(id);
	}

	@Override
	public Page<VehicleType> getVehicleTypeList(DataFilter dataFilter) {
		return vehicleTypeRepo.findByVehicleTypeNameLikeIgnoreCase(dataFilter.getSearch(),
				PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
						Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}
}