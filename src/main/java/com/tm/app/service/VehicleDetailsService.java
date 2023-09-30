package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.VehicleDetailsDto;
import com.tm.app.entity.VehicleDetails;

public interface VehicleDetailsService {

	VehicleDetails saveVehicleDetails(VehicleDetailsDto vehicleDetailsDto);

	List<VehicleDetails> getVehicleDetails();

	VehicleDetails getVehicleDetailsById(Long id);

	VehicleDetails updateVehicleDetails(Long id, VehicleDetailsDto vehicleDetailsDto);

	void deleteVehicleDetailsById(Long id);

	Page<VehicleDetails> getVehicleDetailsList(DataFilter dataFilter);

}
