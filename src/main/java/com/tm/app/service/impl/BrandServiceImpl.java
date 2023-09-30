package com.tm.app.service.impl;

import static com.tm.app.utils.CacheableConstants.BRAND;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.BrandDto;
import com.tm.app.dto.DataFilter;
import com.tm.app.entity.Brand;
import com.tm.app.repo.BrandRepo;
import com.tm.app.service.BrandService;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BrandServiceImpl implements BrandService {

	@Autowired
	private BrandRepo brandRepo;

	@Override
	@Cacheable(value = BRAND)
	public List<Brand> getBrands() {
		log.info("[Brand] Get Brands");
		List<Brand> brandData = brandRepo.findAll();
		for (Brand brand : brandData) {
			if (Objects.nonNull(brand.getLogo())) {
				brand.setLogoString(new String(brand.getLogo()));
				brand.setLogo(null);
			}
		}
		return brandData;
	}

	@Override
	@Transactional
	@CacheEvict(value = BRAND, allEntries = true)
	public Brand saveBrand(BrandDto brandDto) {
		Brand brand = new Brand();
		try {
			BeanUtils.copyProperties(brandDto, brand);
			if (StringUtils.isNotEmpty(brand.getLogoString())) {
				brand.setLogo(brandDto.getLogoString().getBytes());
			}
			brand = brandRepo.save(brand);
		} catch (Exception e) {
			log.error("[BRAND] adding brand failed", e);
			throw new RuntimeException("Brand save failed");
		}
		return brand;
	}

	@Override
	@Cacheable(value = BRAND, key = "#id")
	public Brand getBrandById(Long id) {
		log.info("[Brand] Get brandById");
		Brand brandDetails = brandRepo.findById(id).orElseThrow();
		if (Objects.nonNull(brandDetails.getLogo())) {
			brandDetails.setLogoString(new String(brandDetails.getLogo()));
		}
		return brandDetails;
	}

	@Override
	@Transactional
	@CacheEvict(cacheNames = BRAND, key = "#id", allEntries = true)
	public void deleteBrandById(Long id) {
		log.info("[Brand] Delete brandById");
		try {
			brandRepo.deleteById(id);
		} catch (Exception e) {
			log.error("[BRAND] Deleting brand failed", e);
			throw new RuntimeException("Deleting brand failed");
		}
	}

	@Override
	@Transactional
	@CacheEvict(value = BRAND, allEntries = true)
	public Brand updateBrand(Long id, BrandDto brandDto) {
		log.info("[Brand] Update brand");
		Brand brand = brandRepo.findById(id).orElseThrow();
		try {
			brand.setDescription(brandDto.getDescription());
			brand.setName(brandDto.getName());
			if (StringUtils.isNotEmpty(brandDto.getLogoString())) {
				brand.setLogo(brandDto.getLogoString().getBytes());
			}
			brand = brandRepo.save(brand);
		} catch (Exception e) {
			log.error("[BRAND] updating brand failed", e);
			throw new RuntimeException("Updating brand failed");
		}
		return brand;
	}

	@Override
	@Cacheable(value = BRAND)
	public Page<Brand> getBrandList(DataFilter dataFilter) {
		log.info("[Brand] Get brandList");
		Page<Brand> brandData = brandRepo.findByNameLikeIgnoreCase(dataFilter.getSearch(),
				PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
						Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
		for (Brand brand : brandData) {
			if (Objects.nonNull(brand.getLogo())) {
				brand.setLogoString(new String(brand.getLogo()));
				brand.setLogo(null);
			}
		}
		return brandData;
	}
}