package com.tm.app.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.ServiceItem;
@Repository
public interface ServiceItemRepo extends JpaRepository<ServiceItem, Long>{

	Page<ServiceItem> findByItemNameLikeIgnoreCase(String search, PageRequest of);

	boolean existsByItemNameEqualsIgnoreCase(String itemName);

}
