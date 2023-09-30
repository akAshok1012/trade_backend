package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.MachinerySparesDto;
import com.tm.app.entity.MachinerySpares;

public interface MachinerySparesService {

	MachinerySpares saveMachinerySpares(MachinerySparesDto machinerySparesDto);

	List<MachinerySpares> getMachinerySpares();

	MachinerySpares getMachinerySparesById(Long id);

	MachinerySpares updateMachinerySpares(Long id, MachinerySparesDto machinerySparesDto);

	void deleteMachinerySparesById(Long id);

	Page<MachinerySpares> getMachinerySparesList(DataFilter dataFilter);

}
