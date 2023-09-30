package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.VehicleTypeDto;
import com.tm.app.entity.VehicleType;

public interface VehicleTypeService {

	VehicleType saveVehicleType(VehicleTypeDto vehicleTypeDto);

	List<VehicleType> getVehicleType();

	VehicleType getVehicleTypeById(Long id);

	VehicleType updateVehicleType(Long id, VehicleTypeDto vehicleTypeDto);

	void deleteVehicleTypeById(Long id);

	Page<VehicleType> getVehicleTypeList(DataFilter dataFilter);

}
