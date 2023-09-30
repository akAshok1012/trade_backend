package com.tm.app.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.VehicleServiceDto;
import com.tm.app.entity.VehicleService;
import com.tm.app.entity.VehicleServiceHistory;
import com.tm.app.repo.VehicleServiceHistoryRepo;
import com.tm.app.repo.VehicleServiceRepository;
import com.tm.app.service.VehicleServices;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class VehicleServiceImpl implements VehicleServices {

	@Autowired
	private VehicleServiceRepository vehicleServiceRepository;

	@Autowired
	private VehicleServiceHistoryRepo historyRepo;

	@Override
	public VehicleService saveVehicleService(VehicleServiceDto vehicleServiceDetailsDto) {
		log.info("[VEHICLE_SERVICE] saveVehicleService starts");
		VehicleService service = new VehicleService();
		BeanUtils.copyProperties(vehicleServiceDetailsDto, service);
		service = vehicleServiceRepository.save(service);
		
		// insert into t_vehicle_service_history for every vehicle service
		log.info("[VEHICLE_SERVICE_history] saveVehicleServiceHistory starts");
		VehicleServiceHistory serviceHistory = new VehicleServiceHistory();
		BeanUtils.copyProperties(service, serviceHistory);
		historyRepo.save(serviceHistory);
		return service;
	}

	@Override
	public List<VehicleService> getVehicleServices() {
		return vehicleServiceRepository.findAll();
	}

	@Override
	public VehicleService getVehicleServiceById(Long id) {
		return vehicleServiceRepository.findById(id).orElseThrow();
	}

	@Override
	public VehicleService updateVehicleService(Long id, VehicleServiceDto vehicleServiceDetailsDto) {
		log.info("[VEHICLE_SERVICE] updateVehicleService starts");
		VehicleService service = vehicleServiceRepository.findById(id).orElseThrow();
		BeanUtils.copyProperties(vehicleServiceDetailsDto, service);
		service = vehicleServiceRepository.save(service);

		// insert into t_vehicle_service_history for every vehicle service
		VehicleServiceHistory serviceHistory = new VehicleServiceHistory();
		BeanUtils.copyProperties(service, serviceHistory);
		historyRepo.save(serviceHistory);
		return service;
	}

	@Override
	public void deleteVehicleServiceById(Long id) {
		vehicleServiceRepository.deleteById(id);
	}
	
	@Override
	public Page<VehicleService> getVehicleServicesList(DataFilter dataFilter) {
		Page<VehicleService> vehicleServicesList = vehicleServiceRepository.getVehicleServicesList(dataFilter.getSearch(),
				PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
						Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
		vehicleServicesList.getContent().stream().forEach(r->r.setVehicleTypeName(r.getVehicleDetails().getVehicleType().getVehicleTypeName()));
		return vehicleServicesList;
	}
}