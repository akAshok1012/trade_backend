package com.tm.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.HolidaysDto;
import com.tm.app.entity.Holidays;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.service.HolidaysService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.Response;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HolidaysController {

	@Autowired
	private HolidaysService holidaysService;

	@PostMapping("/holiday")
	@IsSuperAdminOrAdmin
	public APIResponse<?> saveHoliday(@RequestBody HolidaysDto holidaysDto) {
		try {
			Holidays holidays = holidaysService.saveHoliday(holidaysDto);
			return Response.getSuccessResponse(holidays, "Holiday Saved Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/holidays")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getHolidays() {
		try {
			List<Holidays> holidays = holidaysService.getHolidays();
			return Response.getSuccessResponse(holidays, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/holiday/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getHolidayById(@PathVariable("id") Long id) {
		try {
			Holidays holidays = holidaysService.getHolidayById(id);
			return Response.getSuccessResponse(holidays, "success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/holiday/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> updateHoliday(@PathVariable Long id, @RequestBody HolidaysDto holidaysDto) {
		try {
			Holidays holidays = holidaysService.updateHoliday(id, holidaysDto);
			return Response.getSuccessResponse(holidays, "Holiday Updated Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/holiday/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> deleteHolidayById(@PathVariable("id") Long id) {
		try {
			holidaysService.deleteHolidayById(id);
			return Response.getSuccessResponse(null, "Holiday Deleted Successfully", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/holiday-list")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getHolidayList(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<Holidays> holidays = holidaysService.getHolidayList(dataFilter);
			return Response.getSuccessResponse(holidays, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/holiday-by-month")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getHolidayByMonthAndYear(@RequestParam(value = "month") int month,
			@RequestParam(value = "year") int year) {
		try {
			List<Holidays> holidays = holidaysService.getHolidayByMonthAndYear(month,year);
			return Response.getSuccessResponse(holidays, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
