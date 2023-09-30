package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.BrandDto;
import com.tm.app.dto.DataFilter;
import com.tm.app.entity.Brand;

public interface BrandService {

	public List<Brand> getBrands();

	public Brand getBrandById(Long id);

	public void deleteBrandById(Long id);

	public Brand updateBrand(Long id, BrandDto brandDto);

	public Brand saveBrand(BrandDto brandDto);

	public Page<Brand> getBrandList(DataFilter dataFilter);

}