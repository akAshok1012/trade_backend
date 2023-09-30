package com.tm.app.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.VehicleDetailsDto;
import com.tm.app.entity.VehicleDetails;
import com.tm.app.repo.VehicleDetailsRepo;
import com.tm.app.service.VehicleDetailsService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class VehicleDetailsServiceImpl implements VehicleDetailsService {

	@Autowired
	private VehicleDetailsRepo vehicleDetailsRepo;

	@Override
	public VehicleDetails saveVehicleDetails(VehicleDetailsDto vehicleDetailsDto) {
		VehicleDetails vehicle = new VehicleDetails();
		try {
			if (vehicleDetailsRepo.existsByVehicleRegistrationNumberEqualsIgnoreCase(
					vehicleDetailsDto.getVehicleRegistrationNumber())) {
				throw new RuntimeException("Vehicle number already exists!");
			}
			if (vehicleDetailsRepo.existsByChassisNumber(vehicleDetailsDto.getChassisNumber())) {
				throw new RuntimeException("Chassis number already exists!");
			}
			if (vehicleDetailsRepo.existsByRcNumber(vehicleDetailsDto.getRcNumber())) {
				throw new RuntimeException("Rc number already exists!");
			}
			if (vehicleDetailsRepo.existsByPolicyNo(vehicleDetailsDto.getPolicyNo())) {
				throw new RuntimeException("Policy number already exists!");
			}
			BeanUtils.copyProperties(vehicleDetailsDto, vehicle);
			vehicle = vehicleDetailsRepo.save(vehicle);
		} catch (Exception e) {
			log.error("[VehicleServiceImpl] Adding Vehicle Details Failed", e);
			throw new RuntimeException(e.getMessage());
		}
		return vehicle;
	}

	@Override
	public List<VehicleDetails> getVehicleDetails() {
		return vehicleDetailsRepo.findAll();
	}

	@Override
	public VehicleDetails getVehicleDetailsById(Long id) {
		return vehicleDetailsRepo.findById(id).orElseThrow();
	}

	@Override
	public VehicleDetails updateVehicleDetails(Long id, VehicleDetailsDto vehicleDetailsDto) {
		VehicleDetails vehicle = vehicleDetailsRepo.findById(id).orElseThrow();
		try {
			if (vehicleDetailsRepo
					.existsByVehicleRegistrationNumberEqualsIgnoreCase(vehicleDetailsDto.getVehicleRegistrationNumber())
					&& !vehicle.getVehicleRegistrationNumber()
							.equalsIgnoreCase(vehicleDetailsDto.getVehicleRegistrationNumber())) {
				throw new RuntimeException("Vehicle number already exists!");
			}
			if (vehicleDetailsRepo.existsByChassisNumber(vehicleDetailsDto.getChassisNumber())
					&& !vehicle.getChassisNumber().equalsIgnoreCase(vehicleDetailsDto.getChassisNumber())) {
				throw new RuntimeException("Chassis number already exists!");
			}
			if (vehicleDetailsRepo.existsByRcNumber(vehicleDetailsDto.getRcNumber())
					&& !vehicle.getRcNumber().equalsIgnoreCase(vehicleDetailsDto.getRcNumber())) {
				throw new RuntimeException("Rc number already exists!");
			}
			if (vehicleDetailsRepo.existsByPolicyNo(vehicleDetailsDto.getPolicyNo())
					&& !vehicle.getPolicyNo().equalsIgnoreCase(vehicleDetailsDto.getPolicyNo())) {
				throw new RuntimeException("Policy number already exists!");
			}
			BeanUtils.copyProperties(vehicleDetailsDto, vehicle);
			return vehicleDetailsRepo.save(vehicle);
		} catch (Exception e) {
			log.error("[VehicleServiceImpl] Updating Vehicle Details Failed", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void deleteVehicleDetailsById(Long id) {
		vehicleDetailsRepo.deleteById(id);
	}

	@Override
	public Page<VehicleDetails> getVehicleDetailsList(DataFilter dataFilter) {
		Page<VehicleDetails> vehicleDetailsList = vehicleDetailsRepo.findByVehicleRegistrationNumberLikeIgnoreCase(
				dataFilter.getSearch(), PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
						Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
		return vehicleDetailsList;
	}
}