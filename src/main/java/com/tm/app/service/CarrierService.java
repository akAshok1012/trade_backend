package com.tm.app.service;

import java.util.List;

import com.tm.app.dto.CarrierDto;
import com.tm.app.entity.Carrier;

public interface CarrierService {

	public Carrier saveCarrier(CarrierDto carrierDto);

	public List<Carrier> getCarriers();

	public Carrier getCarrierById(Long id);

	public void deleteCarrierById(Long id);

	public Carrier updateCarrier(Long id, CarrierDto carrierDto);

}