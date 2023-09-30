package com.tm.app.utils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {
    public static Date convertToDate(String inputDate) {
	String[] dateFormatters = { "yyyy/MM/dd", "MM/dd/yyyy", "dd/MM/yyyy", "dd-MM-yy", "dd-MM-yyyy", "yyyy-MM-dd",
		"yyyy.MM.dd", "dd MMM yyyy", "dd MMMM yyyy",
		// Add more date formats as needed
	};
	for (String dateFormat : dateFormatters) {
	    try {
		LocalDate localDate = LocalDate.parse(inputDate, DateTimeFormatter.ofPattern(dateFormat));
		return Date.valueOf(localDate);
	    } catch (DateTimeParseException e) {
		// Ignore and try the next format
	    }
	}
	return null;
    }
}