package com.tm.app.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tm.app.dto.DataFilter;
import com.tm.app.dto.MachineryDto;
import com.tm.app.entity.Machinery;
import com.tm.app.repo.MachineryRepo;
import com.tm.app.service.MachineryService;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MachineryServiceImpl implements MachineryService {

	@Autowired
	private MachineryRepo machineryRepo;

	@Autowired
	private JwtService jwtService;

	@Override
	@Transactional
	public Machinery saveMachinery(MachineryDto machineryDto, HttpServletRequest request)
			throws JsonProcessingException {
		Machinery machinery = new Machinery();
		try {
			if (StringUtils.isNotEmpty(machineryDto.getSerialNumber())
					&& machineryRepo.existsBySerialNumberIgnoreCase(machineryDto.getSerialNumber())) {
				throw new RuntimeException("Serial Number  already exists");
			}
			BeanUtils.copyProperties(machineryDto, machinery);
			machinery = machineryRepo.save(machinery);
		} catch (Exception e) {
			log.error("[MACHINERY] adding machinery failed", e);
			throw new RuntimeException(e.getMessage());
		}
		return machinery;
	}

	@Override
	public List<Machinery> getMachineries() {
		log.info("Get Machinery");
		return machineryRepo.findAll();
	}

	@Override
	public Machinery getMachineryById(Long id) {
		log.info("Get Machinery By Id");
		return machineryRepo.getMachineryById(id);
	}

	@Override
	@Transactional
	public Machinery updateMachinery(Long id, MachineryDto machineryDto, HttpServletRequest request)
			throws JsonProcessingException {
		log.info("Updated Machinery");
		Machinery machinery = machineryRepo.findById(id).orElseThrow();
		try {
			if (StringUtils.isNotEmpty(machineryDto.getSerialNumber())
					&& machineryRepo.existsBySerialNumberIgnoreCase(machineryDto.getSerialNumber())
					&& !machinery.getSerialNumber().equals(machineryDto.getSerialNumber())) {
				throw new RuntimeException("Serial Number  already exists");
			}
			String authorizationHeaderValue = request.getHeader("Authorization");

			if (authorizationHeaderValue != null && authorizationHeaderValue.startsWith("Bearer")) {
				authorizationHeaderValue = authorizationHeaderValue.substring(7, authorizationHeaderValue.length());
			}

			String userName = jwtService.extractJwtObject(authorizationHeaderValue).getUserName();

			BeanUtils.copyProperties(machineryDto, machinery);
			machinery = machineryRepo.save(machinery);
		} catch (Exception e) {
			log.error("[MACHINERY] updating machinery failed", e);
			throw new RuntimeException(e.getMessage());
		}
		return machinery;
	}

	@Override
	public void deleteMachineryById(Long id) {
		log.info("Deleted Machinery");
		try {
			machineryRepo.deleteById(id);
		} catch (Exception e) {
			log.error("[MACHINERY] deleting machinery failed", e);
			throw new RuntimeException("Deleting machinery failed");
		}
	}

	@Override
	public Page<Machinery> getMachineryList(DataFilter dataFilter) {
		log.info("[MachineryServiceImpl] Get MachineryList");
		return machineryRepo.findBySerialNumberLikeIgnoreCase(dataFilter.getSearch(),
				PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
						Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}
}