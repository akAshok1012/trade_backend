package com.tm.app.service;

import com.lowagie.text.DocumentException;

public interface PdfGenerateService {

	byte[] salesInvoice(Integer id) throws DocumentException;

	byte[] payslipInvoice(Long id) throws DocumentException;

}
