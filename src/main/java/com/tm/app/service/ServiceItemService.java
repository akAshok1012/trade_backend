package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.ServiceItemDto;
import com.tm.app.entity.ServiceItem;

public interface ServiceItemService {

	ServiceItem saveServiceItem(ServiceItemDto serviceItemDto);

	List<ServiceItem> getServiceItems();

	ServiceItem getServiceItemById(Long id);

	ServiceItem updateServiceItem(Long id, ServiceItemDto serviceItemDto);

	void deleteServiceItemById(Long id);

	Page<ServiceItem> getServiceItemList(DataFilter dataFilter);

}
