package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.HolidaysDto;
import com.tm.app.entity.Holidays;

public interface HolidaysService {

	Holidays saveHoliday(HolidaysDto holidaysDto);

	List<Holidays> getHolidays();

	Holidays getHolidayById(Long id);

	Holidays updateHoliday(Long id, HolidaysDto holidaysDto);

	void deleteHolidayById(Long id);

	Page<Holidays> getHolidayList(DataFilter dataFilter);

	List<Holidays> getHolidayByMonthAndYear(int month, int year);

}
