package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.UnitOfMeasureDto;
import com.tm.app.entity.UnitOfMeasure;

public interface UnitOfMeasureService {

	public List<UnitOfMeasure> getUnitOfMeasures();

	public UnitOfMeasure saveUnitOfMeasure(UnitOfMeasureDto unitOfMeasureDto);

	public UnitOfMeasure getUnitOfMeasureById(Long id);

	public void deleteUnitOfMeasureById(Long id);

	public UnitOfMeasure updateUnitOfMeasure(Long id, UnitOfMeasureDto unitOfMeasureDto);

	public Page<UnitOfMeasure> getUnitOfMeasureList(DataFilter dataFilter);

}