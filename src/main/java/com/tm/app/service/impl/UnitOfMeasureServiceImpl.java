package com.tm.app.service.impl;

import static com.tm.app.utils.CacheableConstants.UNIT_OF_MEASURE;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.UnitOfMeasureDto;
import com.tm.app.entity.UnitOfMeasure;
import com.tm.app.repo.UnitOfMeasureRepo;
import com.tm.app.service.UnitOfMeasureService;
import com.tm.app.utils.APIResponseConstants;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

	@Autowired
	private UnitOfMeasureRepo unitOfMeasureRepo;

	@Override
	@Cacheable(value = UNIT_OF_MEASURE)
	public List<UnitOfMeasure> getUnitOfMeasures() {
		log.info("[UnitOfMeasure] Get unitOfMeasure");
		return unitOfMeasureRepo.findAll();
	}

	@Override
	@Transactional
	@CacheEvict(value = UNIT_OF_MEASURE, allEntries = true)
	public UnitOfMeasure saveUnitOfMeasure(UnitOfMeasureDto unitOfMeasureDto) {
		log.info("[UnitOfMeasure] Get unitOfMeasure");
		UnitOfMeasure unitOfMeasures = new UnitOfMeasure();
		try {
			if (unitOfMeasureRepo.existsByUnitNameEqualsIgnoreCaseAndUnitWeight(unitOfMeasureDto.getUnitName(),
					unitOfMeasureDto.getUnitWeight())) {
				throw new RuntimeException(String.format(APIResponseConstants.UNIT_OF_MEASURES_ALREADY_EXISTS,
						unitOfMeasureDto.getUnitName(), unitOfMeasureDto.getUnitWeight()));
			}
			BeanUtils.copyProperties(unitOfMeasureDto, unitOfMeasures);
			unitOfMeasures = unitOfMeasureRepo.save(unitOfMeasures);
		} catch (Exception e) {
			log.error("[UNIT_OF_MEASURE] adding unitOfMeasure failed", e);
			throw new RuntimeException(e.getMessage());
		}
		return unitOfMeasures;
	}

	@Override
	@Cacheable(value = UNIT_OF_MEASURE, key = "#id")
	public UnitOfMeasure getUnitOfMeasureById(Long id) {
		log.info("[UnitOfMeasure] Get unitOfMeasureById");
		return unitOfMeasureRepo.findById(id).orElseThrow();
	}

	@Override
	@Transactional
	@CacheEvict(cacheNames = UNIT_OF_MEASURE, key = "#id", allEntries = true)
	public void deleteUnitOfMeasureById(Long id) {
		log.info("[UnitOfMeasure] Deleted unitOfMeasureById");
		try {
			unitOfMeasureRepo.deleteById(id);
		} catch (Exception e) {
			log.error("[UNIT_OF_MEASURE] deleting unitOfMeasure failed", e);
			throw new RuntimeException("Deleting unitOfMeasure failed");
		}

	}

	@Override
	@Transactional
	@CacheEvict(value = UNIT_OF_MEASURE, allEntries = true)
	public UnitOfMeasure updateUnitOfMeasure(Long id, UnitOfMeasureDto unitOfMeasureDto) {
		log.info("[UnitOfMeasure] Updated unitOfMeasure");
		UnitOfMeasure unitOfMeasure = unitOfMeasureRepo.findById(id).orElseThrow();
		try {
			if (unitOfMeasureRepo.existsByUnitNameEqualsIgnoreCaseAndUnitWeight(unitOfMeasureDto.getUnitName(),
					unitOfMeasureDto.getUnitWeight())
					&& !(unitOfMeasure.getUnitName().equalsIgnoreCase(unitOfMeasureDto.getUnitName())
							&& unitOfMeasure.getUnitWeight().equals(unitOfMeasureDto.getUnitWeight()))) {
				throw new RuntimeException(String.format(APIResponseConstants.UNIT_OF_MEASURES_ALREADY_EXISTS,
						unitOfMeasureDto.getUnitName(), unitOfMeasureDto.getUnitWeight()));
			}
			unitOfMeasure.setUnitName(unitOfMeasureDto.getUnitName());
			unitOfMeasure.setUnitDescription(unitOfMeasureDto.getUnitDescription());
			unitOfMeasure.setUnitWeight(unitOfMeasureDto.getUnitWeight());
			unitOfMeasure = unitOfMeasureRepo.save(unitOfMeasure);
		} catch (Exception e) {
			log.error("[UNIT_OF_MEASURE] updating unitOfMeasure failed", e);
			throw new RuntimeException(e.getMessage());
		}
		return unitOfMeasure;
	}

	@Override
	@Cacheable(value = UNIT_OF_MEASURE)
	public Page<UnitOfMeasure> getUnitOfMeasureList(DataFilter dataFilter) {
		log.info("[UnitOfMeasure] Get unitOfMeasureList");
		return unitOfMeasureRepo.findByUnitNameLikeIgnoreCase(dataFilter.getSearch(),
				PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
						Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}
}