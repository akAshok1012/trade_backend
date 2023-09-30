package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.VehicleServiceTypeDto;
import com.tm.app.entity.VehicleServiceType;

public interface VehicleServiceTypeService {
	
	VehicleServiceType saveVehicleServiceType(VehicleServiceTypeDto vehicleServiceTypeDto);
	
	List<VehicleServiceType> getVehicleServiceType();
	
	VehicleServiceType getVehicleServiceTypeById(Long id);
	
	VehicleServiceType updateVehicleServiceType(Long id, VehicleServiceTypeDto vehicleServiceTypeDto);
	
	void deleteVehicleServiceTypeById(Long id);

	Page<VehicleServiceType> getVehicleServiceTypeList(DataFilter dataFilter);
	
	

}
