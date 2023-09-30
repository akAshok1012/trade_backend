package com.tm.app.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tm.app.dto.CarrierDto;
import com.tm.app.entity.Carrier;
import com.tm.app.repo.CarrierRepo;
import com.tm.app.service.CarrierService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CarrierServiceImpl implements CarrierService {

	@Autowired
	private CarrierRepo carrierRepo;

	@Override
	@Transactional
	public Carrier saveCarrier(CarrierDto carrierDto) {
		Carrier carrier = new Carrier();
		try {
			BeanUtils.copyProperties(carrierDto, carrier);
			carrier = carrierRepo.save(carrier);
		} catch (Exception e) {
			log.error("[CARRIER] adding carrier failed", e);
			throw new RuntimeException("Adding carrier failed");
		}
		return carrier;
	}

	@Override
	public List<Carrier> getCarriers() {
		return carrierRepo.findAll();
	}

	@Override
	public Carrier getCarrierById(Long id) {
		return carrierRepo.findById(id).orElseThrow();
	}

	@Override
	@Transactional
	public void deleteCarrierById(Long id) {
		try {
			carrierRepo.deleteById(id);
		} catch (Exception e) {
			log.error("[CARRIER] deleting carrier failed", e);
			throw new RuntimeException("Deleting carrier failed");
		}
	}

	@Override
	@Transactional
	public Carrier updateCarrier(Long id, CarrierDto carrierDto) {
		Carrier carrier = carrierRepo.findById(id).orElseThrow();
		try {
			carrier.setAddress(carrierDto.getAddress());
			carrier.setCarrierCompany(carrierDto.getCarrierCompany());
			carrier.setContactEmail(carrierDto.getContactEmail());
			carrier.setContactPhone(carrierDto.getContactPhone());
			carrier.setContactName(carrierDto.getContactName());
			carrier = carrierRepo.save(carrier);
			return carrier;
		} catch (Exception e) {
			log.error("[CARRIER] updating carrier failed", e);
			throw new RuntimeException("Updating carrier" + carrier.getCarrierCompany() + "failed");
		}
	}
}