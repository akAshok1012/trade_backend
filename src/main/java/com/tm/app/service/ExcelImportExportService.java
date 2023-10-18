package com.tm.app.service;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.web.multipart.MultipartFile;

import com.tm.app.dto.ImportCsvResponse;

public interface ExcelImportExportService {

	ImportCsvResponse uploadContractEmployees(MultipartFile file, Class<?> forName) throws IOException;

	byte[] downloadContractEmployeesExcel(Class<?> forName);

	ImportCsvResponse uploadCustomer(MultipartFile file, Class<?> forName) throws IOException;

	ImportCsvResponse uploadEmployees(MultipartFile file, Class<?> forName) throws IOException, ParseException;

	byte[] downloadSampleTemplate(Class<?> forName);

	ImportCsvResponse uploadEmployeePayHours(MultipartFile file, Class<?> forName) throws IOException, ParseException;

	ImportCsvResponse uploadEmployeeWeeklyWages(MultipartFile file, Class<?> forName)throws IOException, ParseException;

}
