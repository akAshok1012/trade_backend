package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tm.app.dto.DataFilter;
import com.tm.app.dto.ProductionDto;
import com.tm.app.entity.Production;

import jakarta.servlet.http.HttpServletRequest;


public interface ProductionService {

	Production saveProduction(ProductionDto productionDto,HttpServletRequest request) throws JsonProcessingException;

	List<Production> getAllProduction();

	Production getProductionById(Long id);

	Production updateProductionById(Long id, ProductionDto productionDto,HttpServletRequest request) throws JsonProcessingException;

	void deleteProductionById(Long id);

	Page<Production> getProductionList(DataFilter dataFilter);


}
