package com.tm.app.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;
import com.tm.app.dto.EmployeeMonthlyPayDto;
import com.tm.app.dto.SalesInvoiceDto;
import com.tm.app.entity.EmployeeMonthlyPay;
import com.tm.app.entity.Order;
import com.tm.app.entity.OrderItem;
import com.tm.app.entity.SalesOrder;
import com.tm.app.repo.EmployeeMonthlyPayRepo;
import com.tm.app.repo.OrderItemRepo;
import com.tm.app.repo.OrderRepo;
import com.tm.app.repo.SalesOrderRepo;
import com.tm.app.service.PdfGenerateService;
import com.tm.app.utils.Currency;

@Service
public class PdfGenerateServiceImpl implements PdfGenerateService {

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private SalesOrderRepo salesOrderRepo;

	@Autowired
	private OrderItemRepo orderItemRepo;

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private EmployeeMonthlyPayRepo employeeMonthlyPayRepo;

	@Value("${application.logo}")
	private String path;

	public byte[] generatePdfFile(String templateName, Map<String, Object> data) throws DocumentException {
		Context context = new Context();
		context.setVariables(data);

		String htmlContent = templateEngine.process(templateName, context);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ITextRenderer renderer = new ITextRenderer();
		renderer.setDocumentFromString(htmlContent);
		renderer.layout();
		renderer.createPDF(outputStream, false);
		renderer.finishPDF();
		return outputStream.toByteArray();
	}

	@SuppressWarnings("deprecation")
	@Override
	public byte[] salesInvoice(Integer id) throws DocumentException {
		Map<String, Object> data = new HashMap<>();
		SalesOrder salesOrder = salesOrderRepo.findBySalesId(id);
		Order order = orderRepo.findByOrderId(salesOrder.getOrder());
		List<OrderItem> orderItems = orderItemRepo.findByOrder(order);
		List<SalesInvoiceDto> salesInvoiceList = new ArrayList<>();
		long grandTotal = 0;
		SalesInvoiceDto invoiceDto = new SalesInvoiceDto();
		invoiceDto.setSalesId(salesOrder.getSalesId().toString());
		invoiceDto.setClientOrganization(salesOrder.getCustomer().getOrganization());
		invoiceDto.setClientAddress(salesOrder.getCustomer().getAddress());
		invoiceDto.setClientEmail(salesOrder.getCustomer().getEmail());
		invoiceDto.setClientName(salesOrder.getCustomer().getName());
		invoiceDto.setClientPhoneNumber(salesOrder.getCustomer().getPhoneNumber());
		invoiceDto.setUpdatedAt(salesOrder.getUpdatedAt().toLocaleString());
		for (OrderItem orderItem : orderItems) {
			SalesInvoiceDto invoiceDtoList = new SalesInvoiceDto();
			invoiceDtoList.setClientItemName(orderItem.getItemMaster().getItemName());
			invoiceDtoList.setDescription(orderItem.getItemMaster().getItemDescription());
			invoiceDtoList.setQuantity(orderItem.getUnitOfMeasure().getUnitWeight() * orderItem.getOrderedQuantity());
			invoiceDtoList.setUnitPrice(orderItem.getUnitPrice());
			long total = (long) ((orderItem.getUnitOfMeasure().getUnitWeight() * orderItem.getOrderedQuantity())
					* orderItem.getUnitPrice());
			invoiceDtoList.setTotal(total);
			grandTotal = grandTotal + total;
			salesInvoiceList.add(invoiceDtoList);
		}
		data.put("client", invoiceDto);
		data.put("grandTotal", grandTotal);
		data.put("itemList", salesInvoiceList);
		data.put("image", path);
		data.put("totalInWords", Currency.numbersToWord(String.valueOf(grandTotal)));
		return generatePdfFile("invoice-template", data);
	}

	@Override
	public byte[] payslipInvoice(Long id) throws DocumentException {
		Map<String, Object> data = new HashMap<>();
		EmployeeMonthlyPay employeeMonthlyPay = new EmployeeMonthlyPay();

		EmployeeMonthlyPayDto employeeMonthlyPayDto = new EmployeeMonthlyPayDto();
		employeeMonthlyPay = employeeMonthlyPayRepo.findById(id).orElseThrow();

		employeeMonthlyPayDto.setBasicPay(employeeMonthlyPay.getBasicPay());
		employeeMonthlyPayDto.setBonus(employeeMonthlyPay.getBonus());
		employeeMonthlyPayDto.setConveyanceAllowance(employeeMonthlyPay.getConveyanceAllowance());
		employeeMonthlyPayDto.setDa(employeeMonthlyPay.getDa());
		employeeMonthlyPayDto.setDeductions(employeeMonthlyPay.getDeductions());
		employeeMonthlyPayDto.setMedicalAllowance(employeeMonthlyPay.getMedicalAllowance());
		employeeMonthlyPayDto.setSpecialAllowance(employeeMonthlyPay.getSpecialAllowance());
		employeeMonthlyPayDto.setEsi(employeeMonthlyPay.getEsi());
		employeeMonthlyPayDto.setEarnings(employeeMonthlyPay.getEarnings());
		employeeMonthlyPayDto.setEpf(employeeMonthlyPay.getEpf());
		employeeMonthlyPayDto.setIta(employeeMonthlyPay.getIta());
		employeeMonthlyPayDto.setEmployee(employeeMonthlyPay.getEmployee());
		employeeMonthlyPayDto.setNetPay(employeeMonthlyPay.getNetPay());
		employeeMonthlyPayDto.setHra(employeeMonthlyPay.getHra());
		employeeMonthlyPayDto.setAttendanceBonus(employeeMonthlyPay.getAttendanceBonus());
		employeeMonthlyPayDto.setMonthlyLeavesAllowed(employeeMonthlyPay.getMonthlyLeavesAllowed());
		employeeMonthlyPayDto.setOvertimeRate(employeeMonthlyPay.getOvertimeRate());
		employeeMonthlyPayDto.setPayDateTime(employeeMonthlyPay.getPayDateTime());
		employeeMonthlyPayDto.setTotalDays(employeeMonthlyPay.getTotalDays());
		employeeMonthlyPayDto.setTotalWorkingDays(employeeMonthlyPay.getTotalWorkingDays());
		employeeMonthlyPayDto.setNetPay(employeeMonthlyPay.getNetPay());

		data.put("data", employeeMonthlyPayDto);
		data.put("image", path);
		return generatePdfFile("payslip-template", data);
	}
}