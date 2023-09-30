package com.tm.app.rest;

import java.io.File;
import java.io.FileInputStream;
import java.util.Objects;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tm.app.dto.ImportCsvResponse;
import com.tm.app.service.ExcelImportExportService;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class ExcelImportExportController {

	@Autowired
	private ExcelImportExportService excelImportExportService;

	@PostMapping("/upload-contract-employees")
	public ResponseEntity<?> uploadContractEmployees(@RequestParam("file") MultipartFile file) {
		log.info("[ExcelImportExportController] uploadContractEmployees starts ");
		try {
			ImportCsvResponse importCsvResponse = excelImportExportService.uploadContractEmployees(file,
					Class.forName("com.tm.app.entity.ContractEmployee"));
			return ResponseEntity.ok().body(importCsvResponse);
		} catch (Exception e) {
			log.error("[ExcelImportExportController] uploadContractEmployees failed", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred during Excel import.");
		}
	}

	@PostMapping("/upload-customers")
	public ResponseEntity<?> uploadCustomer(@RequestParam("file") MultipartFile file) {
		log.info("[ExcelImportExportController] uploadCustomer starts ");
		try {
			ImportCsvResponse importCsvResponse = excelImportExportService.uploadCustomer(file,
					Class.forName("com.tm.app.entity.Customer"));
			return ResponseEntity.ok().body(importCsvResponse);
		} catch (Exception e) {
			log.error("[ExcelImportExportController] uploadCustomer failed", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred during Excel import.");
		}
	}

	@PostMapping("/upload-employees")
	public ResponseEntity<?> uploadEmployees(@RequestParam("file") MultipartFile file) {
		log.info("[ExcelImportExportController] uploadCustomer starts ");
		try {
			ImportCsvResponse importCsvResponse = excelImportExportService.uploadEmployees(file,
					Class.forName("com.tm.app.entity.Employee"));
			return ResponseEntity.ok().body(importCsvResponse);
		} catch (Exception e) {
			log.error("[ExcelImportExportController] uploadCustomer failed", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred during Excel import.");
		}
	}
	
	@PostMapping("/upload-employee-pay-hours")
	public ResponseEntity<?> uploadEmployeePayHours(@RequestParam("file") MultipartFile file) {
		log.info("[ExcelImportExportController] uploadEmployeePayHours Starts ");
		try {
			ImportCsvResponse importCsvResponse = excelImportExportService.uploadEmployeePayHours(file,
					Class.forName("com.tm.app.entity.Employee"));
			return ResponseEntity.ok().body(importCsvResponse);
		} catch (Exception e) {
			log.error("[ExcelImportExportController] uploadEmployeePayHours Failed", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred during Excel import.");
		}
	}

	@GetMapping("/download-sample-template")
	public ResponseEntity<?> downloadContractEmployeesExcel(@RequestParam("className") String className) {
		log.info("[ExcelImportExportController] downloadContractEmployeesExcel starts ");
		try {
			byte[] pdfBytes = excelImportExportService
					.downloadContractEmployeesExcel(Class.forName("com.tm.app.entity." + className));
			if (Objects.nonNull(pdfBytes)) {
				String fileName = className + "-Template.xlsx";
				MediaType mediaType = MediaType
						.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				File file = new File(fileName);
				FileUtils.writeByteArrayToFile(file, pdfBytes); // org.apache.commons.io.FileUtils
				InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
				return ResponseEntity.ok()
						.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
						.contentType(mediaType).contentLength(file.length()) //
						.body(resource);
			}
		} catch (Exception e) {
			log.error("[ExcelImportExportController] downloadContractEmployeesExcel failed", e);
			throw new RuntimeException(e.getMessage());
		}
		return null;
	}

	@GetMapping("/download-csv-template")
	public ResponseEntity<?> downloadSampleTemplate(@RequestParam("className") String className) {
		log.info("[ExcelImportExportController] downloadContractEmployeesExcel starts ");
		try {
			byte[] pdfBytes = excelImportExportService
					.downloadSampleTemplate(Class.forName("com.tm.app.entity." + className));
			if (Objects.nonNull(pdfBytes)) {
				String fileName = className + "-Template.csv";
				MediaType mediaType = MediaType.parseMediaType("text/csv");
				File file = new File(fileName);
				FileUtils.writeByteArrayToFile(file, pdfBytes); // org.apache.commons.io.FileUtils
				InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
				return ResponseEntity.ok()
						.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
						.contentType(mediaType).contentLength(file.length()) //
						.body(resource);
			}
		} catch (Exception e) {
			log.error("[ExcelImportExportController] downloadContractEmployeesExcel failed", e);
			throw new RuntimeException(e.getMessage());
		}
		return null;
	}
}