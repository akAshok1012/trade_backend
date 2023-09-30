package com.tm.app.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.ShipmentCustomerUserStatusDto;
import com.tm.app.dto.ShipmentHistoryDto;
import com.tm.app.dto.ShipmentStatusDto;
import com.tm.app.dto.ShipmentStatusSalesIdDto;
import com.tm.app.entity.Customer;
import com.tm.app.entity.ShipmentDetails;
import com.tm.app.entity.User;
import com.tm.app.enums.ShipmentStatus;
import com.tm.app.repo.CustomerRepo;
import com.tm.app.repo.ShipmentDetailsRepo;
import com.tm.app.repo.ShipmentHistoryRepo;
import com.tm.app.repo.UserRepository;
import com.tm.app.service.ShipmentDetailsService;

import io.micrometer.common.util.StringUtils;

@Service
public class ShipmentDetailsServiceImpl implements ShipmentDetailsService {

	@Autowired
	private ShipmentDetailsRepo shipmentDetailsRepo;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private ShipmentHistoryRepo historyRepo;

	@Override
	public List<ShipmentDetails> getShipmentDetails() {
		return shipmentDetailsRepo.findAll();
	}

	@Override
	public Page<ShipmentStatusDto> getShipmentStatus(ShipmentStatus shipmentStatus, DataFilter dataFilter) {
		return shipmentDetailsRepo.getShipmentStatus(shipmentStatus, PageRequest.of(dataFilter.getPage(),
				dataFilter.getSize(), Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())),
				dataFilter.getSearch());
	}

	@Override
	public Page<ShipmentCustomerUserStatusDto> getShipmentUserId(Long id, ShipmentStatus shipmentStatus,
			DataFilter dataFilter) {
		return shipmentDetailsRepo.getShipmentUserId(id, shipmentStatus, PageRequest.of(dataFilter.getPage(),
				dataFilter.getSize(), Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}

	@Override
	public Page<ShipmentDetails> getShipmentDetailsListing(DataFilter dataFilter) {
		return shipmentDetailsRepo.findAll(PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
				Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}

	@Override
	public List<List<ShipmentHistoryDto>> getShipmentHistoryBySalesId(Integer salesId) {
		List<ShipmentHistoryDto> shipmentHistory = historyRepo.getShipmentHistoryBySalesId(salesId);
		Map<Object, List<ShipmentHistoryDto>> trackingNumberMap = shipmentHistory.stream()
				.collect(Collectors.groupingBy(r -> StringUtils.isEmpty(r.getTrackingNumber())));
		return trackingNumberMap.values().stream().toList();
	}

	@Override
	public Page<ShipmentStatusSalesIdDto> getShipmentStatusSalesId(Long id, Integer salesId,
			ShipmentStatus shipmentStatus, DataFilter dataFilter) {
		if (id != null) {
			User user = userRepository.findById(id).orElseThrow();
			Customer customer = customerRepo.findById(user.getUserId()).orElseThrow();
			if (Objects.nonNull(salesId)) {
				dataFilter.setSearch("%" + salesId.toString() + "%");
				return shipmentDetailsRepo.getShipmentStatusByCustomerAndSalesId(customer.getId(), shipmentStatus,
						dataFilter.getSearch(), PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
								Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
			} else {
				return shipmentDetailsRepo.getShipmentStatusByCustomerAndSalesId(customer.getId(), shipmentStatus,
						dataFilter.getSearch(), PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
								Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
			}
		} else {
			if (Objects.nonNull(salesId)) {
				dataFilter.setSearch("%" + salesId.toString() + "%");
				return shipmentDetailsRepo.getShipmentStatusSalesId(shipmentStatus, dataFilter.getSearch(),
						PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
								Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
			} else {
				return shipmentDetailsRepo.getShipmentStatusSalesId(shipmentStatus, dataFilter.getSearch(),
						PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
								Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
			}
		}
	}
}