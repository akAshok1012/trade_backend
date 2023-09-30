package com.tm.app.rest;

import java.io.ByteArrayInputStream;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tm.app.security.annotations.IsSuperAdminOrAdminOrCustomer;
import com.tm.app.security.annotations.IsSuperAdminOrAdminOrEmployee;
import com.tm.app.service.PdfGenerateService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class InvoiceController {

	@Autowired
	private PdfGenerateService pdfGenerateService;

	@GetMapping("/salesInvoice")
	@IsSuperAdminOrAdminOrCustomer
	public ResponseEntity<?> salesInvoice(@RequestParam("salesId") Integer id, HttpServletResponse response) {
		try {
			byte[] pdfBytes = pdfGenerateService.salesInvoice(id);
			if (Objects.nonNull(pdfBytes)) {
				String fileName = "KPR_INVOICE_" + id + ".pdf";
				MediaType mediaType = MediaType.parseMediaType("application/pdf");
				InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(pdfBytes));
				return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
						.contentType(mediaType).body(resource);
			} else {
			}

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return null;
	}

	@GetMapping("/employee-monthly-payslip")
	@IsSuperAdminOrAdminOrEmployee
	public ResponseEntity<?> payslipInvoice(@RequestParam("id") Long id, HttpServletResponse response) {
		try {
			byte[] pdfBytes = pdfGenerateService.payslipInvoice(id);
			if (Objects.nonNull(pdfBytes)) {
				String fileName = "PAYSLIP_INVOICE_1.pdf";
				MediaType mediaType = MediaType.parseMediaType("application/pdf");
				InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(pdfBytes));
				return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
						.contentType(mediaType).body(resource);
			} else {

			}

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return null;
	}
}