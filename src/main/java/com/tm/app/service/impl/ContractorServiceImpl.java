package com.tm.app.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.ContractorDto;
import com.tm.app.dto.DataFilter;
import com.tm.app.entity.Contractor;
import com.tm.app.repo.ContractorRepo;
import com.tm.app.service.ContractorService;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class ContractorServiceImpl implements ContractorService {

	@Autowired
	private ContractorRepo contractorRepo;

	@Override
	public Contractor saveContractor(ContractorDto contractorDto) {
		Contractor contractor = new Contractor();
		if (Objects.nonNull(contractorDto.getPhoneNumber())
				&& contractorRepo.existsByPhoneNumber(contractorDto.getPhoneNumber())) {
			throw new RuntimeException("Phone Number Already Exists");
		}
		if (Objects.nonNull(contractorDto.getAadhaarNumber())
				&& contractorRepo.existsByAadhaarNumber(contractorDto.getAadhaarNumber())) {
			throw new RuntimeException("Aadhaar Number Already Exists");
		}
		if (Objects.nonNull(contractorDto.getEmail()) && contractorRepo.existsByEmail(contractorDto.getEmail())) {
			throw new RuntimeException("Email Already Exists");
		}
		if (StringUtils.isNotEmpty(contractorDto.getPanNumber())
				&& contractorRepo.existsByPanNumber(contractorDto.getPanNumber())) {
			throw new RuntimeException("Pan Number Already Exists");
		}
		BeanUtils.copyProperties(contractorDto, contractor);
		return contractorRepo.save(contractor);
	}

	@Override
	public List<Contractor> getContractors() {
		return contractorRepo.findAll();
	}

	@Override
	public Contractor getContractorById(Long id) {
		return contractorRepo.findById(id).orElseThrow();
	}

	@Override
	public Contractor updateContractor(Long id, ContractorDto contractorDto) {
		log.info("[CONTRACT] updateContractor starts");
		Contractor contractor = contractorRepo.findById(id).orElseThrow();
		if (Objects.nonNull(contractorDto.getPhoneNumber())
				&& contractorRepo.existsByPhoneNumber(contractorDto.getPhoneNumber())
				&& !contractor.getPhoneNumber().equals(contractorDto.getPhoneNumber())) {
			throw new RuntimeException("Phone Number  already exists");
		}
		if (Objects.nonNull(contractorDto.getAadhaarNumber())
				&& contractorRepo.existsByAadhaarNumber(contractorDto.getAadhaarNumber())
				&& !contractor.getAadhaarNumber().equals(contractorDto.getAadhaarNumber())) {
			throw new RuntimeException("Aadhaar Number  already exists");
		}
		if (Objects.nonNull(contractorDto.getEmail()) && contractorRepo.existsByEmail(contractorDto.getEmail())
				&& !contractor.getEmail().equals(contractorDto.getEmail())) {
			throw new RuntimeException("Email  already exists");
		}
		if (Objects.nonNull(contractorDto.getPanNumber()) && contractorRepo.existsByPanNumber(contractorDto.getPanNumber())
				&& !contractor.getPanNumber().equals(contractorDto.getPanNumber())) {
			throw new RuntimeException("PAN Number already exists");
		}
		BeanUtils.copyProperties(contractorDto, contractor);
		return contractorRepo.save(contractor);
	}

	@Override
	public void deleteContractorById(Long id) {
		contractorRepo.deleteById(id);
	}

	@Override
	public Page<Contractor> getContractorList(DataFilter dataFilter) {
		return contractorRepo.getContractorList(dataFilter.getSearch(), PageRequest.of(dataFilter.getPage(),
				dataFilter.getSize(), Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}
}