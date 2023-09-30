package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.VehicleServiceDto;
import com.tm.app.entity.VehicleService;

public interface VehicleServices {

	VehicleService saveVehicleService(VehicleServiceDto vehicleServiceDetailsDto);

	List<VehicleService> getVehicleServices();

	VehicleService getVehicleServiceById(Long id);

	VehicleService updateVehicleService(Long id, VehicleServiceDto vehicleServiceDetailsDto);

	void deleteVehicleServiceById(Long id);

	Page<VehicleService> getVehicleServicesList(DataFilter dataFilter);

}
