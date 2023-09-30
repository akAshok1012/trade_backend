package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.ShipmentCustomerListDto;
import com.tm.app.dto.ShipmentDto;
import com.tm.app.dto.ShipmentItemDto;
import com.tm.app.entity.Shipment;
import com.tm.app.entity.ShipmentDetails;

public interface ShipmentService {

	public List<Shipment> getShipments();

	public Shipment saveShipment(ShipmentDto shipmentDto);

	public Shipment getShipmentById(Long id);

	public void deleteShipmentById(Long id);

	public List<Shipment> updateShipment(ShipmentDto shipmentDto);

	public List<ShipmentItemDto> getShipmentBySalesId(Integer salesId);

	public List<ShipmentDetails> getCustomerShipmentBySalesId(Integer salesId, Long id);

	public Page<Shipment> getShipmentListing(DataFilter dataFilter);

	public Page<ShipmentCustomerListDto> getShipmentsBySalesId(Integer salesId, DataFilter dataFilter);

	public Page<ShipmentCustomerListDto> getShipmentListingWithCustomer(DataFilter dataFilter);
}