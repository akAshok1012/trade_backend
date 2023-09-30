package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.ShipmentCustomerUserStatusDto;
import com.tm.app.dto.ShipmentHistoryDto;
import com.tm.app.dto.ShipmentStatusDto;
import com.tm.app.dto.ShipmentStatusSalesIdDto;
import com.tm.app.entity.ShipmentDetails;
import com.tm.app.enums.ShipmentStatus;

public interface ShipmentDetailsService {

	List<ShipmentDetails> getShipmentDetails();

	Page<ShipmentStatusDto> getShipmentStatus(ShipmentStatus shipmentStatus, DataFilter dataFilter);

	Page<ShipmentCustomerUserStatusDto> getShipmentUserId(Long id, ShipmentStatus shipmentStatus,
			DataFilter dataFilter);

	Page<ShipmentDetails> getShipmentDetailsListing(DataFilter dataFilter);

	List<List<ShipmentHistoryDto>> getShipmentHistoryBySalesId(Integer salesId);

	Page<ShipmentStatusSalesIdDto> getShipmentStatusSalesId(Long id, Integer salesId, ShipmentStatus shipmentStatus,
			DataFilter dataFilter);
}
