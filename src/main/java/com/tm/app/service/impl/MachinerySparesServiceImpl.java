package com.tm.app.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.MachinerySparesDto;
import com.tm.app.entity.MachinerySpares;
import com.tm.app.repo.MachinerySparesRepo;
import com.tm.app.service.MachinerySparesService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class MachinerySparesServiceImpl implements MachinerySparesService {

	@Autowired
	private MachinerySparesRepo machinerySparesRepo;
	
	@Override
	public MachinerySpares saveMachinerySpares(MachinerySparesDto machinerySparesDto) {
		MachinerySpares machinerySpares = new MachinerySpares();
		try {
			BeanUtils.copyProperties(machinerySparesDto, machinerySpares);
			machinerySpares = machinerySparesRepo.save(machinerySpares);
		} catch (Exception e) {
			log.error("[MACHINERY] adding machinery failed", e);
			throw new RuntimeException("Adding machinery failed");
		}
		return machinerySpares;
	}

	@Override
	public List<MachinerySpares> getMachinerySpares() {
		return machinerySparesRepo.findAll();
	}

	@Override
	public MachinerySpares getMachinerySparesById(Long id) {
		return machinerySparesRepo.getMachinerySparesById(id);
	}

	@Override
	public MachinerySpares updateMachinerySpares(Long id, MachinerySparesDto machinerySparesDto) {
		log.info("[MACHINERY_SPARES] updateMachinerySpares starts");
		MachinerySpares machinerySpares = machinerySparesRepo.findById(id).orElseThrow();
		BeanUtils.copyProperties(machinerySparesDto, machinerySpares);
		return machinerySparesRepo.save(machinerySpares);
	}

	@Override
	public void deleteMachinerySparesById(Long id) {
		machinerySparesRepo.deleteById(id);
	}

	@Override
	public Page<MachinerySpares> getMachinerySparesList(DataFilter dataFilter) {
		return machinerySparesRepo.findByTechnicianNameLikeIgnoreCase(dataFilter.getSearch(),
				PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
						Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}

}
