package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tm.app.dto.DataFilter;
import com.tm.app.dto.MachineryDto;
import com.tm.app.entity.Machinery;

import jakarta.servlet.http.HttpServletRequest;

public interface MachineryService {

	public Machinery saveMachinery(MachineryDto machineryDto, HttpServletRequest request)throws JsonProcessingException;

	public Machinery getMachineryById(Long id);

	public Machinery updateMachinery(Long id, MachineryDto machineryDto, HttpServletRequest request)throws JsonProcessingException;

	public void deleteMachineryById(Long id);

	List<Machinery> getMachineries();

	public Page<Machinery> getMachineryList(DataFilter dataFilter);

}