package com.tm.app.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.ServiceItemDto;
import com.tm.app.entity.ServiceItem;
import com.tm.app.repo.ServiceItemRepo;
import com.tm.app.service.ServiceItemService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class ServiceItemImpl implements ServiceItemService {

	@Autowired
	private ServiceItemRepo serviceItemRepo;

	@Override
	public ServiceItem saveServiceItem(ServiceItemDto serviceItemDto) {
		log.info("[SERIVE_ITEM] saveServiceItem starts");
		ServiceItem serviceItem = new ServiceItem();
		if (serviceItemRepo.existsByItemNameEqualsIgnoreCase(serviceItemDto.getItemName())) {
			throw new RuntimeException("Service Item Name already exists!");
		}
		BeanUtils.copyProperties(serviceItemDto, serviceItem);
		return serviceItemRepo.save(serviceItem);
	}

	@Override
	public List<ServiceItem> getServiceItems() {
		return serviceItemRepo.findAll();
	}

	@Override
	public ServiceItem getServiceItemById(Long id) {
		return serviceItemRepo.findById(id).orElseThrow();
	}

	@Override
	public ServiceItem updateServiceItem(Long id, ServiceItemDto serviceItemDto) {
		log.info("[SERIVE_ITEM] updateServiceItem starts");
		ServiceItem serviceItem = serviceItemRepo.findById(id).orElseThrow();
		if (serviceItemRepo.existsByItemNameEqualsIgnoreCase(serviceItemDto.getItemName())
				&& !serviceItem.getItemName().equalsIgnoreCase(serviceItemDto.getItemName())) {
			throw new RuntimeException("Service Item Name already exists!");
		}
		BeanUtils.copyProperties(serviceItemDto, serviceItem);
		return serviceItemRepo.save(serviceItem);
	}

	@Override
	public void deleteServiceItemById(Long id) {
		serviceItemRepo.deleteById(id);
	}

	@Override
	public Page<ServiceItem> getServiceItemList(DataFilter dataFilter) {
		return serviceItemRepo.findByItemNameLikeIgnoreCase(dataFilter.getSearch(), PageRequest.of(dataFilter.getPage(),
				dataFilter.getSize(), Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}
}