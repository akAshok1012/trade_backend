package com.tm.app.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.Brand;

@Repository
public interface BrandRepo extends JpaRepository<Brand, Long> {

	Page<Brand> findByNameLikeIgnoreCase(String search, PageRequest of);

	boolean existsByNameIgnoreCase(String name);

}
