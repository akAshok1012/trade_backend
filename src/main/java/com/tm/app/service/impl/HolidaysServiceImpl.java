package com.tm.app.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.HolidaysDto;
import com.tm.app.entity.Holidays;
import com.tm.app.repo.HolidaysRepo;
import com.tm.app.service.HolidaysService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class HolidaysServiceImpl implements HolidaysService {

	@Autowired
	private HolidaysRepo holidaysRepo;

	@Override
	public List<Holidays> getHolidays() {
		log.info("[HolidaysServiceImpl] getHolidays starts...");
		List<Holidays> holidays = null;
		try {
			holidays = holidaysRepo.findAll();
		} catch (Exception e) {
			log.info("[HolidaysServiceImpl] getHolidays failed...");
			throw new RuntimeException("Get Holidays Failed", e);
		}
		return holidays;
	}

	@Override
	public Holidays getHolidayById(Long id) {
		log.info("[HolidaysServiceImpl] getHolidayById starts...");
		return holidaysRepo.findById(id).orElseThrow();
	}

	@Override
	public Holidays updateHoliday(Long id, HolidaysDto holidaysDto) {
		Holidays holidays = holidaysRepo.findById(id).orElseThrow();
		try {
			BeanUtils.copyProperties(holidaysDto, holidays);
			holidays = holidaysRepo.save(holidays);
		} catch (Exception e) {
			log.error("updating Holiday failed", e);
			throw new RuntimeException("Updating Holiday Failed");
		}
		log.info("[HolidaysServiceImpl] updating Holiday ends...");
		return holidays;
	}

	@Override
	public void deleteHolidayById(Long id) {
		log.info("[HolidaysServiceImpl] deleteHolidayById starts...");
		try {
			holidaysRepo.deleteById(id);
		} catch (Exception e) {
			log.error("deleting Holiday failed", e);
			throw new RuntimeException("Deleting Holiday Failed");
		}
		log.info("[HolidaysServiceImpl] deleteHolidayById ends...");
	}

	@Override
	public Page<Holidays> getHolidayList(DataFilter dataFilter) {
		return holidaysRepo.findAll(PageRequest.of(dataFilter.getPage(), dataFilter.getSize(),
				Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}

	@Override
	public List<Holidays> getHolidayByMonthAndYear(int month, int year) {
		return holidaysRepo.getHolidayByMonthAndYear(month, year);
	}

	@Override
	public Holidays saveHoliday(HolidaysDto holidaysDto) {
		try {
			Holidays holiday = new Holidays();
			BeanUtils.copyProperties(holidaysDto, holiday);
			holidaysRepo.save(holiday);
			return holiday;
		} catch (Exception e) {
			log.info("[HolidaysServiceImpl] Saving Holiday Failed");
			throw new RuntimeException(e.getMessage());
		}
	}

}
