package com.tm.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.Holidays;

@Repository
public interface HolidaysRepo extends JpaRepository<Holidays, Long> {

	@Query(value = "SELECT *, DATE_PART('month', th.holiday_date) AS extracted_month, DATE_PART('year', th.holiday_date) AS extracted_year FROM t_holidays th  WHERE DATE_PART('month', th.holiday_date) = ? AND DATE_PART('year', th.holiday_date) = ?", nativeQuery = true)
	List<Holidays> getHolidayByMonthAndYear(int month, int year);
}
