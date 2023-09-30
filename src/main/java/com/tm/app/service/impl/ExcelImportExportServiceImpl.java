package com.tm.app.service.impl;

import static com.tm.app.utils.CacheableConstants.CUSTOMER;
import static com.tm.app.utils.CacheableConstants.EMPLOYEE;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tm.app.dto.ImportCsvResponse;
import com.tm.app.entity.ContractEmployee;
import com.tm.app.entity.Contractor;
import com.tm.app.entity.Customer;
import com.tm.app.entity.Employee;
import com.tm.app.entity.EmployeeDepartment;
import com.tm.app.entity.EmployeePayHours;
import com.tm.app.entity.ReportConfiguration;
import com.tm.app.enums.CustomerType;
import com.tm.app.repo.ContractEmployeeRepo;
import com.tm.app.repo.ContractorRepo;
import com.tm.app.repo.CustomerRepo;
import com.tm.app.repo.EmployeeDepartmentRepo;
import com.tm.app.repo.EmployeePayHoursRepo;
import com.tm.app.repo.EmployeeRepo;
import com.tm.app.repo.ReportConfigurationRepo;
import com.tm.app.service.ExcelImportExportService;
import com.tm.app.utils.TableMetaData;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Table;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ExcelImportExportServiceImpl implements ExcelImportExportService {
	private static final String INVALID_LIST = "Invalid List";

	@Autowired
	private DSLContext dslContext;

	@Autowired
	private ContractEmployeeRepo contractEmployeeRepo;

	@Autowired
	private ContractorRepo contractorRepo;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private EmployeeDepartmentRepo employeeDepartmentRepo;

	@Autowired
	private EmployeePayHoursRepo employeePayHoursRepo;

	@Autowired
	private ReportConfigurationRepo configurationRepo;

	@Override
	public ImportCsvResponse uploadContractEmployees(MultipartFile file, Class<?> clazz) throws IOException {
		log.info("[ExcelImportExportServiceImpl] uploadContractEmployees starts");
		ImportCsvResponse importCsvResponse = new ImportCsvResponse();
		Annotation annotation = clazz.getAnnotation(Table.class);
		Table tableAnnotation = (Table) annotation;
		ReportConfiguration reportConfiguration = configurationRepo.findByTableName(tableAnnotation.name());
		// Generate insert query
		List<ContractEmployee> errorList = convertExcelToContractEmployeeList(file, importCsvResponse);
		if (!errorList.isEmpty()) {
			try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
				excelByteConverion(errorList, outputStream, reportConfiguration);
				importCsvResponse.setErrorCsvData(outputStream.toByteArray());
				return importCsvResponse;
			} catch (IOException e) {
				log.error("[ExcelImportExportServiceImpl] uploadContractEmployees failed", e);
				throw new RuntimeException("uploadContractEmployees failed", e);
			}
		}
		return importCsvResponse;
	}

	/**
	 * converting errorList to csv bytes
	 * 
	 * @param errorList
	 * @param outputStream
	 * @throws IOException
	 */
	private void csvByteConversion(List<XSSFRow> errorList, ByteArrayOutputStream outputStream) throws IOException {
		for (Row row : errorList) {
			StringBuilder csvRow = new StringBuilder();
			int lastCellNum = row.getLastCellNum();
			for (int cellIndex = 0; cellIndex < lastCellNum; cellIndex++) {
				Cell cell = row.getCell(cellIndex);
				DataFormatter formatter = new DataFormatter();
				String cellValue = formatter.formatCellValue(cell);
				csvRow.append(cellValue).append(",");
			}
			csvRow.deleteCharAt(csvRow.length() - 1);
			outputStream.write(csvRow.toString().getBytes());
			outputStream.write("\n".getBytes());
		}
	}

	@Override
	public byte[] downloadContractEmployeesExcel(Class<?> clazz) {
		try (XSSFWorkbook workbook = new XSSFWorkbook();
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			XSSFSheet sheet = workbook.createSheet("Sheet1");
			Row headerRow = sheet.createRow(0);
			Annotation annotation = clazz.getAnnotation(Table.class);
			Table tableAnnotation = (Table) annotation;
			ReportConfiguration reportConfiguration = configurationRepo.findByTableName(tableAnnotation.name());
			for (int cellIndex = 0; cellIndex < reportConfiguration.getHeaderFields().size(); cellIndex++) {
				Cell headerCell = headerRow.createCell(cellIndex);
				headerCell.setCellValue(reportConfiguration.getHeaderFields().get(cellIndex));
			}
			workbook.write(outputStream);
			return outputStream.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException("downloadContractEmployeesExcel failed", e);
		}
	}

	@Override
	@CacheEvict(value = CUSTOMER, allEntries = true)
	public ImportCsvResponse uploadCustomer(MultipartFile file, Class<?> clazz) throws IOException {
		log.info("[ExcelImportExportServiceImpl] uploadCustomer starts");
		ImportCsvResponse importCsvResponse = new ImportCsvResponse();
		Annotation annotation = clazz.getAnnotation(Table.class);
		Table tableAnnotation = (Table) annotation;
		ReportConfiguration reportConfiguration = configurationRepo.findByTableName(tableAnnotation.name());
		// Generate insert query
		List<Customer> errorList = convertExcelToCustomerList(file, importCsvResponse);
		if (!errorList.isEmpty()) {
			try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
				customerListByteConverion(errorList, outputStream, reportConfiguration);
				importCsvResponse.setErrorCsvData(outputStream.toByteArray());
				return importCsvResponse;
			} catch (IOException e) {
				log.error("[ExcelImportExportServiceImpl] uploadCustomers failed", e);
				throw new RuntimeException("uploadCustomers failed", e);
			}
		}
		return importCsvResponse;
	}

	@Override
	@CacheEvict(value = EMPLOYEE, allEntries = true)
	public ImportCsvResponse uploadEmployees(MultipartFile file, Class<?> clazz) throws IOException, ParseException {
		log.info("[ExcelImportExportServiceImpl] uploadEmployees starts");
		ImportCsvResponse importCsvResponse = new ImportCsvResponse();
		Annotation annotation = clazz.getAnnotation(Table.class);
		Table tableAnnotation = (Table) annotation;
		ReportConfiguration reportConfiguration = configurationRepo.findByTableName(tableAnnotation.name());
		// Generate insert query
		SimpleDateFormat excelDateFormat = new SimpleDateFormat("MM/dd/yy");
		List<Employee> errorList = convertExcelToEmployeeList(file, importCsvResponse, excelDateFormat);
		if (!errorList.isEmpty()) {
			try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
				employeeListByteConverion(errorList, outputStream, excelDateFormat, reportConfiguration);
				importCsvResponse.setErrorCsvData(outputStream.toByteArray());
				return importCsvResponse;
			} catch (IOException e) {
				log.error("[ExcelImportExportServiceImpl] uploadEmployees failed", e);
				throw new RuntimeException("uploadEmployees failed", e);
			}
		}
		return importCsvResponse;
	}

	/**
	 * employeeListByteConverion
	 * 
	 * @param errorList
	 * @param outputStream
	 * @param excelDateFormat
	 * @param reportConfiguration
	 * @throws ParseException
	 */
	private void employeeListByteConverion(List<Employee> errorList, ByteArrayOutputStream outputStream,
			SimpleDateFormat excelDateFormat, ReportConfiguration reportConfiguration) {
		try (XSSFWorkbook workbook = new XSSFWorkbook()) {
			XSSFSheet sheet = workbook.createSheet(INVALID_LIST);
			// Create header row
			createHeaders(sheet, reportConfiguration);
			// Populate data rows
			int rowNum = 1;
			for (Employee employee : errorList) {
				XSSFRow row = sheet.createRow(rowNum++);
				writeEmployeeExistsData(employee, row, excelDateFormat);
			}
			workbook.write(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * writeEmployeeExistsData
	 * 
	 * @param employee
	 * @param row
	 * @param excelDateFormat
	 * @throws ParseException
	 */
	private void writeEmployeeExistsData(Employee employee, XSSFRow row, SimpleDateFormat excelDateFormat) {
		row.createCell(0).setCellValue(Objects.nonNull(employee.getAadhaarNumber()) ? employee.getAadhaarNumber() : 0);
		row.createCell(1).setCellValue(StringUtils.isNotEmpty(employee.getAddress()) ? employee.getAddress() : "");
		row.createCell(2).setCellValue(
				Objects.nonNull(employee.getDateOfBirth()) ? excelDateFormat.format(employee.getDateOfBirth()) : "");
		row.createCell(3)
				.setCellValue(Objects.nonNull(employee.getDateOfJoining())
						? excelDateFormat.format(employee.getDateOfJoining())
						: "");
		row.createCell(4)
				.setCellValue(StringUtils.isNotEmpty(employee.getDesignation()) ? employee.getDesignation() : "");
		row.createCell(5).setCellValue(StringUtils.isNotEmpty(employee.getEmail()) ? employee.getEmail() : "");
		row.createCell(6).setCellValue(StringUtils.isNotEmpty(employee.getEsiNumber()) ? employee.getEsiNumber() : "");
		row.createCell(7).setCellValue(StringUtils.isNotEmpty(employee.getName()) ? employee.getName() : "");
		row.createCell(8).setCellValue(StringUtils.isNotEmpty(employee.getPanNumber()) ? employee.getPanNumber() : "");
		row.createCell(9).setCellValue(StringUtils.isNotEmpty(employee.getPfNumber()) ? employee.getPfNumber() : "");
		row.createCell(10).setCellValue(Objects.nonNull(employee.getPhoneNumber()) ? employee.getPhoneNumber() : 0);
		row.createCell(11).setCellValue(Objects.nonNull(employee.getUanNumber()) ? employee.getUanNumber() : 0);
		row.createCell(12).setCellValue(StringUtils.isNotEmpty(employee.getUpdatedBy()) ? employee.getUpdatedBy() : "");
		row.createCell(13).setCellValue(
				Objects.nonNull(employee.getEmployeeDepartment()) ? employee.getEmployeeDepartment().getId() : 0);
		row.createCell(14).setCellValue(
				StringUtils.isNotEmpty(employee.getErrorDescription()) ? employee.getErrorDescription() : "");
	}

	@Override
	public byte[] downloadSampleTemplate(Class<?> clazz) {
		Annotation annotation = clazz.getAnnotation(Table.class);
		Table tableAnnotation = (Table) annotation;
		List<String> headers = TableMetaData.getHeaders(dslContext, tableAnnotation.name());
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			StringBuilder csvRow = new StringBuilder();
			for (String header : headers) {
				csvRow.append(header).append(",");
			}
			outputStream.write(csvRow.toString().getBytes());
			outputStream.write("\n".getBytes());
			return outputStream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param file
	 * @param importCsvResponse
	 * @return
	 * @throws IOException
	 */
	public List<ContractEmployee> convertExcelToContractEmployeeList(MultipartFile file,
			ImportCsvResponse importCsvResponse) throws IOException {
		List<ContractEmployee> data = new ArrayList<>();
		List<ContractEmployee> errorList = new ArrayList<>();
		Map<Long, Contractor> contractorMap = contractorRepo.findAll().stream()
				.collect(Collectors.toMap(Contractor::getId, r -> r));
		try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
			int successCount = 0;
			int failureCount = 0;
			XSSFSheet sheet = workbook.getSheetAt(0); // Assuming the data is in the first sheet
			for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				XSSFRow row = sheet.getRow(rowIndex);
				if (StringUtils.isNotEmpty(getStringCellValue(row, 0))) {
					String aadhaarNumber = getStringCellValue(row, 0);
					String address = getStringCellValue(row, 1);
					String name = getStringCellValue(row, 2);
					String notes = getStringCellValue(row, 3);
					String phoneNumber = getStringCellValue(row, 4);
					String updatedBy = getStringCellValue(row, 5);
					Long contractorId = getLongCellValue(row, 6);
					Contractor contractor = contractorMap.get(contractorId);
					// Create the ContractEmployee object and add it to the list
					if (!contractorMap.containsKey(contractorId)) {
						ContractEmployee contractEmployees = new ContractEmployee();
						contractEmployees.setAadhaarNumber(
								StringUtils.isNotEmpty(aadhaarNumber) ? Long.parseLong(aadhaarNumber) : null);
						contractEmployees.setAddress(address);
						contractEmployees.setName(name);
						contractEmployees.setNotes(notes);
						contractEmployees.setPhoneNumber(
								StringUtils.isNotEmpty(phoneNumber) ? Long.parseLong(phoneNumber) : null);
						contractEmployees.setUpdatedBy(updatedBy);
						contractEmployees.setContractor(contractor);
						if (Objects.isNull(contractorId)) {
							contractEmployees.setErrorDescription("Contractor should not be empty");
						} else {
							contractEmployees.setErrorDescription(contractorId + " contractor not present");
						}
						errorList.add(contractEmployees);
						failureCount++;
						continue;
					}
					try {
						ContractEmployee contractEmployee = new ContractEmployee(name, phoneNumber, address,
								aadhaarNumber, notes, updatedBy, contractor);
						data.add(contractEmployee);
					} catch (Exception e) {
						ContractEmployee contractEmployees = new ContractEmployee();
						contractEmployees.setAadhaarNumber(
								StringUtils.isNotEmpty(aadhaarNumber) ? Long.parseLong(aadhaarNumber) : null);
						contractEmployees.setAddress(address);
						contractEmployees.setName(name);
						contractEmployees.setNotes(notes);
						contractEmployees.setPhoneNumber(
								StringUtils.isNotEmpty(phoneNumber) ? Long.parseLong(phoneNumber) : null);
						contractEmployees.setUpdatedBy(updatedBy);
						contractEmployees.setContractor(contractor);
						contractEmployees.setErrorDescription(e.getMessage());
						errorList.add(contractEmployees);
						failureCount++;
					}
				}
			}
			List<ContractEmployee> containsList = checkExistsDatasInContractEmployee(data);
			for (ContractEmployee employee : containsList) {
				errorList.add(employee);
				failureCount++;
			}
			data.removeAll(containsList);
			for (ContractEmployee contractEmployee : data) {
				contractEmployeeRepo.save(contractEmployee);
				successCount++;
			}
			importCsvResponse.setFailureCount(failureCount);
			importCsvResponse.setSuccessCount(successCount);
		}
		return errorList;
	}

	/**
	 * checkExistsDatasInContractEmployee
	 * 
	 * @param data
	 * @return
	 */
	private List<ContractEmployee> checkExistsDatasInContractEmployee(List<ContractEmployee> data) {
		List<Long> aadhaarList = data.stream().map(r -> r.getAadhaarNumber()).toList();
		List<Long> phoneNumberList = data.stream().map(r -> r.getPhoneNumber()).toList();
		List<ContractEmployee> existsList = contractEmployeeRepo.getExistingList(aadhaarList, phoneNumberList);
		List<Long> existsAadhaarList = existsList.stream().map(ContractEmployee::getAadhaarNumber).toList();
		List<Long> existsPhoneNumberList = existsList.stream().map(ContractEmployee::getPhoneNumber).toList();
		List<ContractEmployee> containsList = data.stream()
				.filter(contractEmployee -> existsAadhaarList.contains(contractEmployee.getAadhaarNumber())
						|| existsPhoneNumberList.contains(contractEmployee.getPhoneNumber()))
				.toList();
		containsList.stream().forEach(r -> r.setErrorDescription("Aadhaar / phoneNumber already exists"));
		return containsList;
	}

	/**
	 * 
	 * @param errorList
	 * @param outputStream
	 * @param reportConfiguration
	 */
	private void excelByteConverion(List<ContractEmployee> errorList, ByteArrayOutputStream outputStream,
			ReportConfiguration reportConfiguration) {
		try (XSSFWorkbook workbook = new XSSFWorkbook()) {
			XSSFSheet sheet = workbook.createSheet(INVALID_LIST);
			// Create header row
			createHeaders(sheet, reportConfiguration);
			// Populate data rows
			int rowNum = 1;
			for (ContractEmployee contractEmployee : errorList) {
				XSSFRow row = sheet.createRow(rowNum++);
				writeContractEmployeeExistsData(contractEmployee, row);
			}
			workbook.write(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * writeContractEmployeeExistsData
	 * 
	 * @param contractEmployee
	 * @param row
	 */
	private void writeContractEmployeeExistsData(ContractEmployee contractEmployee, XSSFRow row) {
		row.createCell(0).setCellValue(
				Objects.nonNull(contractEmployee.getAadhaarNumber()) ? contractEmployee.getAadhaarNumber() : 0);
		row.createCell(1).setCellValue(
				StringUtils.isNotEmpty(contractEmployee.getAddress()) ? contractEmployee.getAddress() : "");
		row.createCell(2)
				.setCellValue(StringUtils.isNotEmpty(contractEmployee.getName()) ? contractEmployee.getName() : "");
		row.createCell(3)
				.setCellValue(StringUtils.isNotEmpty(contractEmployee.getNotes()) ? contractEmployee.getNotes() : "");
		row.createCell(4).setCellValue(
				Objects.nonNull(contractEmployee.getPhoneNumber()) ? contractEmployee.getPhoneNumber() : 0);
		row.createCell(5).setCellValue(
				StringUtils.isNotEmpty(contractEmployee.getUpdatedBy()) ? contractEmployee.getUpdatedBy() : "");
		row.createCell(6).setCellValue(
				Objects.nonNull(contractEmployee.getContractor()) ? contractEmployee.getContractor().getId() : 0);
		row.createCell(7)
				.setCellValue(StringUtils.isNotEmpty(contractEmployee.getErrorDescription())
						? contractEmployee.getErrorDescription()
						: "");
	}

	/**
	 * 
	 * @param file
	 * @param filePath
	 * @param importCsvResponse
	 * @return
	 * @throws IOException
	 */
	public List<Customer> convertExcelToCustomerList(MultipartFile file, ImportCsvResponse importCsvResponse)
			throws IOException {
		List<Customer> data = new ArrayList<>();
		List<Customer> errorList = new ArrayList<>();
		try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
			int successCount = 0;
			int failureCount = 0;
			XSSFSheet sheet = workbook.getSheetAt(0); // Assuming the data is in the first sheet
			for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				XSSFRow row = sheet.getRow(rowIndex);
				if (StringUtils.isNotEmpty(row.getCell(0).getStringCellValue())) {
					String address = getStringCellValue(row, 0);
					String customerType = getStringCellValue(row, 1);
					String email = getStringCellValue(row, 2);
					String followUpDays = getStringCellValue(row, 3);
					String gstNo = getStringCellValue(row, 4);
					String name = getStringCellValue(row, 5);
					String organization = getStringCellValue(row, 6);
					String panNo = getStringCellValue(row, 7);
					String phoneNumber = getStringCellValue(row, 8);
					String updatedBy = getStringCellValue(row, 9);
					try {
						Customer customer = new Customer(name, email, phoneNumber, organization, address, updatedBy,
								gstNo, panNo, customerType, followUpDays);
						data.add(customer);
					} catch (Exception e) {
						Customer customer = new Customer();
						customer.setEmail(email);
						customer.setAddress(address);
						customer.setName(name);
						customer.setOrganization(organization);
						customer.setPhoneNumber(Long.parseLong(phoneNumber));
						customer.setUpdatedBy(updatedBy);
						customer.setGstNo(gstNo);
						customer.setPanNo(panNo);
						customer.setCustomerType(CustomerType.parseCustomerType(customerType));
						customer.setFollowUpDays(Integer.parseInt(followUpDays));
						customer.setErrorDescription(e.getMessage());
						errorList.add(customer);
						failureCount++;
					}
				}
			}
			List<Customer> containsList = checkExistsDatasInCustomer(data);
			for (Customer customer : containsList) {
				errorList.add(customer);
				failureCount++;
			}
			data.removeAll(containsList);
			for (Customer customer : data) {
				customerRepo.save(customer);
				successCount++;
			}
			importCsvResponse.setFailureCount(failureCount);
			importCsvResponse.setSuccessCount(successCount);
		}
		return errorList;
	}

	/**
	 * checkExistsDatasInCustomer
	 */
	private List<Customer> checkExistsDatasInCustomer(List<Customer> data) {
		List<String> emailList = data.stream().filter(r -> StringUtils.isNotEmpty(r.getEmail())).map(r -> r.getEmail())
				.toList();
		List<Long> phoneNumberList = data.stream().filter(r -> Objects.nonNull(r.getEmail()))
				.map(r -> r.getPhoneNumber()).toList();
		List<String> gstNumberList = data.stream().filter(r -> StringUtils.isNotEmpty(r.getGstNo()))
				.map(r -> r.getGstNo()).toList();
		List<String> panNumberList = data.stream().filter(r -> StringUtils.isNotEmpty(r.getPanNo()))
				.map(r -> r.getPanNo()).toList();
		List<Customer> existsList = customerRepo.getExistingList(phoneNumberList, emailList, gstNumberList,
				panNumberList);
		List<String> existsEmailList = existsList.stream().map(Customer::getEmail).toList();
		List<Long> existsPhoneNumberList = existsList.stream().map(Customer::getPhoneNumber).toList();
		List<String> existsGstNumberList = existsList.stream().map(Customer::getGstNo).toList();
		List<String> existsPanNumberList = existsList.stream().map(Customer::getPanNo).toList();
		List<Customer> containsList = data.stream()
				.filter(customer -> existsEmailList.contains(customer.getEmail())
						|| existsPhoneNumberList.contains(customer.getPhoneNumber())
						|| existsGstNumberList.contains(customer.getGstNo())
						|| existsPanNumberList.contains(customer.getPanNo()))
				.toList();
		containsList.stream().forEach(r -> r.setErrorDescription("email/phoneNumber/pan/gst already exists"));
		return containsList;
	}

	/**
	 * customerListByteConverion
	 * 
	 * @param errorList
	 * @param outputStream
	 * @param reportConfiguration
	 */
	private void customerListByteConverion(List<Customer> errorList, ByteArrayOutputStream outputStream,
			ReportConfiguration reportConfiguration) {
		try (XSSFWorkbook workbook = new XSSFWorkbook()) {
			XSSFSheet sheet = workbook.createSheet(INVALID_LIST);
			// Create header row
			createHeaders(sheet, reportConfiguration);
			// Populate data rows
			int rowNum = 1;
			for (Customer customer : errorList) {
				XSSFRow row = sheet.createRow(rowNum++);
				writeCustomerExistsData(customer, row);
			}
			workbook.write(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * createCustomerHeaders
	 * 
	 * @param sheet
	 * @param reportConfiguration
	 */
	public void createHeaders(XSSFSheet sheet, ReportConfiguration reportConfiguration) {
		XSSFRow headerRow = sheet.createRow(0);
		for (int cellIndex = 0; cellIndex < reportConfiguration.getHeaderFields().size(); cellIndex++) {
			headerRow.createCell(cellIndex).setCellValue(reportConfiguration.getHeaderFields().get(cellIndex));
		}
		headerRow.createCell(reportConfiguration.getHeaderFields().size()).setCellValue("description");
	}

	/**
	 * writeCustomerExistsData
	 * 
	 * @param customer
	 * @param row
	 */
	private void writeCustomerExistsData(Customer customer, XSSFRow row) {
		row.createCell(0).setCellValue(StringUtils.isNotEmpty(customer.getAddress()) ? customer.getAddress() : "");
		row.createCell(1).setCellValue(
				Objects.nonNull(customer.getCustomerType()) ? customer.getCustomerType() != null : "" != null);
		row.createCell(2).setCellValue(StringUtils.isNotEmpty(customer.getEmail()) ? customer.getEmail() : "");
		row.createCell(3).setCellValue(Objects.nonNull(customer.getFollowUpDays()) ? customer.getFollowUpDays() : 0);
		row.createCell(4).setCellValue(StringUtils.isNotEmpty(customer.getGstNo()) ? customer.getGstNo() : "");
		row.createCell(5).setCellValue(StringUtils.isNotEmpty(customer.getName()) ? customer.getName() : "");
		row.createCell(6)
				.setCellValue(StringUtils.isNotEmpty(customer.getOrganization()) ? customer.getOrganization() : "");
		row.createCell(7).setCellValue(StringUtils.isNotEmpty(customer.getPanNo()) ? customer.getPanNo() : "");
		row.createCell(8).setCellValue(Objects.nonNull(customer.getPhoneNumber()) ? customer.getPhoneNumber() : 0);
		row.createCell(9).setCellValue(StringUtils.isNotEmpty(customer.getUpdatedBy()) ? customer.getUpdatedBy() : "");
		row.createCell(10).setCellValue(
				StringUtils.isNotEmpty(customer.getErrorDescription()) ? customer.getErrorDescription() : "");
	}

	/**
	 * 
	 * @param file
	 * @param filePath
	 * @param importCsvResponse
	 * @param excelDateFormat
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	private List<Employee> convertExcelToEmployeeList(MultipartFile file, ImportCsvResponse importCsvResponse,
			SimpleDateFormat excelDateFormat) throws IOException, ParseException {
		List<Employee> data = new ArrayList<>();
		List<Employee> errorList = new ArrayList<>();
		Map<Long, EmployeeDepartment> empDepartmentMap = employeeDepartmentRepo.findAll().stream()
				.collect(Collectors.toMap(EmployeeDepartment::getId, r -> r));
		try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
			int successCount = 0;
			int failureCount = 0;
			XSSFSheet sheet = workbook.getSheetAt(0); // Assuming the data is in the first sheet
			for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				XSSFRow row = sheet.getRow(rowIndex);
				if (StringUtils.isNotEmpty(getStringCellValue(row, 0))) {
					Date dob = null;
					Date doj = null;
					String aadhaarNumber = getStringCellValue(row, 0);
					String address = getStringCellValue(row, 1);
					String dateOfBirth = getStringCellValue(row, 2);
					String dateOfJoining = getStringCellValue(row, 3);
					String designation = getStringCellValue(row, 4);
					String email = getStringCellValue(row, 5);
					String esiNumber = getStringCellValue(row, 6);
					String name = getStringCellValue(row, 7);
					String panNumber = getStringCellValue(row, 8);
					String pfNumber = getStringCellValue(row, 9);
					String phoneNumber = getStringCellValue(row, 10);
					String uanNumber = getStringCellValue(row, 11);
					String updatedBy = getStringCellValue(row, 12);
					Long departmentId = getLongCellValue(row, 13);
					EmployeeDepartment employeeDepartment = empDepartmentMap.get(departmentId);

					if (StringUtils.isNotEmpty(dateOfBirth)) {
						try {
							java.util.Date excelDate = excelDateFormat.parse(dateOfBirth);
							dob = new java.sql.Date(excelDate.getTime());
						} catch (ParseException e) {
							Employee employee = new Employee();
							employee.setDateOfBirth(dob);
							employee.setDateOfJoining(doj);
							employee.setDesignation(designation);
							employee.setEmail(email);
							employee.setAddress(address);
							employee.setName(name);
							if (StringUtils.isNotEmpty(aadhaarNumber)) {
								employee.setAadhaarNumber(Long.parseLong(aadhaarNumber));
							} else {
								employee.setAadhaarNumber(0L);
							}
							if (StringUtils.isNotEmpty(phoneNumber)) {
								employee.setPhoneNumber(Long.parseLong(phoneNumber));
							} else {
								employee.setPhoneNumber(0L);
							}
							employee.setUpdatedBy(updatedBy);
							employee.setEsiNumber(esiNumber);
							employee.setEmployeeDepartment(employeeDepartment);
							employee.setPanNumber(panNumber);
							employee.setPfNumber(pfNumber);
							if (StringUtils.isNotEmpty(uanNumber)) {
								employee.setUanNumber(Long.parseLong(uanNumber));
							} else {
								employee.setUanNumber(0L);
							}
							employee.setErrorDescription("Invalid Date format");
							errorList.add(employee);
							failureCount++;
							continue;
						}
					}

					if (StringUtils.isNotEmpty(dateOfJoining)) {
						try {
							java.util.Date excelDate = excelDateFormat.parse(dateOfJoining);
							doj = new java.sql.Date(excelDate.getTime());
						} catch (ParseException e) {
							Employee employee = new Employee();
							employee.setDateOfBirth(dob);
							employee.setDateOfJoining(doj);
							employee.setDesignation(designation);
							employee.setEmail(email);
							employee.setAddress(address);
							employee.setName(name);
							if (StringUtils.isNotEmpty(aadhaarNumber)) {
								employee.setAadhaarNumber(Long.parseLong(aadhaarNumber));
							} else {
								employee.setAadhaarNumber(0L);
							}
							if (StringUtils.isNotEmpty(phoneNumber)) {
								employee.setPhoneNumber(Long.parseLong(phoneNumber));
							} else {
								employee.setPhoneNumber(0L);
							}
							employee.setUpdatedBy(updatedBy);
							employee.setEsiNumber(esiNumber);
							employee.setEmployeeDepartment(employeeDepartment);
							employee.setPanNumber(panNumber);
							employee.setPfNumber(pfNumber);
							if (StringUtils.isNotEmpty(uanNumber)) {
								employee.setUanNumber(Long.parseLong(uanNumber));
							} else {
								employee.setUanNumber(0L);
							}
							employee.setErrorDescription("Invalid Date format");
							errorList.add(employee);
							failureCount++;
							continue;
						}
					}

					if (!empDepartmentMap.containsKey(departmentId)) {
						Employee employee = new Employee();
						employee.setDateOfBirth(dob);
						employee.setDateOfJoining(doj);
						employee.setDesignation(designation);
						employee.setEmail(email);
						employee.setAddress(address);
						employee.setName(name);
						if (StringUtils.isNotEmpty(aadhaarNumber)) {
							employee.setAadhaarNumber(Long.parseLong(aadhaarNumber));
						} else {
							employee.setAadhaarNumber(0L);
						}
						if (StringUtils.isNotEmpty(phoneNumber)) {
							employee.setPhoneNumber(Long.parseLong(phoneNumber));
						} else {
							employee.setPhoneNumber(0L);
						}
						employee.setUpdatedBy(updatedBy);
						employee.setEsiNumber(esiNumber);
						employee.setEmployeeDepartment(employeeDepartment);
						employee.setPanNumber(panNumber);
						employee.setPfNumber(pfNumber);
						if (StringUtils.isNotEmpty(uanNumber)) {
							employee.setUanNumber(Long.parseLong(uanNumber));
						} else {
							employee.setUanNumber(0L);
						}
						if (Objects.isNull(departmentId)) {
							employee.setErrorDescription("Employee Department should not be empty");
						} else {
							employee.setErrorDescription(departmentId + " Employee Department not present");
						}
						errorList.add(employee);
						failureCount++;
						continue;
					}
					try {
						Employee employee = new Employee(name, designation, dob, employeeDepartment, doj, pfNumber,
								esiNumber, panNumber, uanNumber, aadhaarNumber, phoneNumber, email, address, updatedBy);
						data.add(employee);
					} catch (Exception e) {
						Employee employee = new Employee();
						employee.setEmail(email);
						employee.setAddress(address);
						employee.setName(name);
						if (StringUtils.isNotEmpty(aadhaarNumber)) {
							employee.setAadhaarNumber(Long.parseLong(aadhaarNumber));
						} else {
							employee.setAadhaarNumber(0L);
						}
						if (StringUtils.isNotEmpty(phoneNumber)) {
							employee.setPhoneNumber(Long.parseLong(phoneNumber));
						} else {
							employee.setPhoneNumber(0L);
						}
						employee.setUpdatedBy(updatedBy);
						employee.setEsiNumber(esiNumber);
						employee.setEmployeeDepartment(employeeDepartment);
						employee.setPanNumber(panNumber);
						employee.setPfNumber(pfNumber);
						if (StringUtils.isNotEmpty(uanNumber)) {
							employee.setUanNumber(Long.parseLong(uanNumber));
						} else {
							employee.setUanNumber(0L);
						}
						employee.setDateOfBirth(dob);
						employee.setDateOfJoining(doj);
						employee.setDesignation(designation);
						employee.setErrorDescription(e.getMessage());
						errorList.add(employee);
						failureCount++;
					}
				}
			}
			List<Employee> containsList = checkExistsDatasInEmployee(data);
			for (Employee employee : containsList) {
				errorList.add(employee);
				failureCount++;
			}
			data.removeAll(containsList);
			for (Employee employee : data) {
				employeeRepo.save(employee);
				successCount++;
			}
			importCsvResponse.setFailureCount(failureCount);
			importCsvResponse.setSuccessCount(successCount);
		}
		return errorList;
	}

	private List<Employee> checkExistsDatasInEmployee(List<Employee> data) {
		List<String> emailList = data.stream().filter(r -> StringUtils.isNotEmpty(r.getEmail())).map(r -> r.getEmail())
				.toList();
		List<Long> phoneNumberList = data.stream().map(r -> r.getPhoneNumber()).toList();
		List<String> pfNumberList = data.stream().filter(r -> StringUtils.isNotEmpty(r.getPfNumber()))
				.map(r -> r.getPfNumber()).toList();
		List<String> panNumberList = data.stream().filter(r -> StringUtils.isNotEmpty(r.getPanNumber()))
				.map(r -> r.getPanNumber()).toList();
		List<String> esiNumberList = data.stream().filter(r -> StringUtils.isNotEmpty(r.getEsiNumber()))
				.map(r -> r.getEsiNumber()).toList();
		List<Long> uanNumberList = data.stream().filter(r -> Objects.nonNull(r.getUanNumber()))
				.map(r -> r.getUanNumber()).toList();
		List<Long> aadhaarNumberList = data.stream().map(r -> r.getAadhaarNumber()).toList();
		List<Employee> existsList = employeeRepo.getExistingList(phoneNumberList, emailList, pfNumberList,
				panNumberList, esiNumberList, uanNumberList, aadhaarNumberList);
		List<String> existsEmailList = existsList.stream().map(Employee::getEmail).toList();
		List<Long> existsPhoneNumberList = existsList.stream().map(Employee::getPhoneNumber).toList();
		List<String> existsPfNumberList = existsList.stream().map(Employee::getPfNumber).toList();
		List<String> existsPanNumberList = existsList.stream().map(Employee::getPanNumber).toList();
		List<String> existsEsiNumberList = existsList.stream().map(Employee::getEsiNumber).toList();
		List<Long> existsUanNumberList = existsList.stream().map(Employee::getUanNumber).toList();
		List<Long> existsAadhaarNumberList = existsList.stream().map(Employee::getAadhaarNumber).toList();
		List<Employee> containsList = data.stream()
				.filter(employee -> existsEmailList.contains(employee.getEmail())
						|| existsPhoneNumberList.contains(employee.getPhoneNumber())
						|| existsPfNumberList.contains(employee.getPfNumber())
						|| existsPanNumberList.contains(employee.getPanNumber())
						|| existsEsiNumberList.contains(employee.getEsiNumber())
						|| existsUanNumberList.contains(employee.getUanNumber())
						|| existsAadhaarNumberList.contains(employee.getAadhaarNumber()))
				.toList();
		containsList.stream().forEach(r -> r.setErrorDescription("email/phoneNumber/pan/gst already exists"));
		return containsList;
	}

	private static Date getDateCellValue(XSSFRow row, int columnIndex) throws ParseException {
		Cell cell = row.getCell(columnIndex);
		Date date = null;
		if (cell != null && cell.getCellType() == Cell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted(cell)) {
			DataFormatter formatter = new DataFormatter();
			SimpleDateFormat excelDateFormat = new SimpleDateFormat("MM/dd/yy");
			String cellValue = formatter.formatCellValue(cell);
			java.util.Date utilDate = excelDateFormat.parse(cellValue);
			date = new Date(utilDate.getTime());
			return date;
		}
		if (cell != null && cell.getCellType() == Cell.CELL_TYPE_STRING) {
			java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(cell.getStringCellValue());
			date = new Date(utilDate.getTime());
			return date;
		}
		return date;
	}

	private String getStringCellValue(XSSFRow row, int columnIndex) {
		Cell cell = row.getCell(columnIndex);
		DataFormatter formatter = new DataFormatter();
		String cellValue = formatter.formatCellValue(cell);
		return cellValue != null ? cellValue : null;
	}

	private Long getLongCellValue(Row row, int columnIndex) {
		Cell cell = row.getCell(columnIndex);
		if (cell != null && cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			return (long) cell.getNumericCellValue();
		}
		if (cell != null && cell.getCellType() == Cell.CELL_TYPE_STRING) {
			return Long.parseLong(cell.getStringCellValue());
		}
		return null;
	}

	@Override
	public ImportCsvResponse uploadEmployeePayHours(MultipartFile file, Class<?> clazz)
			throws IOException, ParseException {
		log.info("[ExcelImportExportServiceImpl] uploadEmployeePayHours starts");
		ImportCsvResponse importCsvResponse = new ImportCsvResponse();
		Annotation annotation = clazz.getAnnotation(Table.class);
		Table tableAnnotation = (Table) annotation;
		ReportConfiguration reportConfiguration = configurationRepo.findByTableName(tableAnnotation.name());
		// Generate insert query
		SimpleDateFormat excelDateFormat = new SimpleDateFormat("MM/dd/yy");
		List<EmployeePayHours> errorList = convertExcelToEmployeePayHoursList(file, importCsvResponse, excelDateFormat);
		if (!errorList.isEmpty()) {
			try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
				employeePayHoursListByteConverion(errorList, outputStream, excelDateFormat, reportConfiguration);
				importCsvResponse.setErrorCsvData(outputStream.toByteArray());
				return importCsvResponse;
			} catch (IOException e) {
				log.error("[ExcelImportExportServiceImpl] uploadEmployeePayHours failed", e);
				throw new RuntimeException("uploadEmployeePayHours failed", e);
			}
		}
		return importCsvResponse;
	}

	private void employeePayHoursListByteConverion(List<EmployeePayHours> errorList, ByteArrayOutputStream outputStream,
			SimpleDateFormat excelDateFormat, ReportConfiguration reportConfiguration) {
		try (XSSFWorkbook workbook = new XSSFWorkbook()) {
			XSSFSheet sheet = workbook.createSheet(INVALID_LIST);
			// Create header row
			createHeaders(sheet, reportConfiguration);
			// Populate data rows
			int rowNum = 1;
			for (EmployeePayHours employeePayHours : errorList) {
				XSSFRow row = sheet.createRow(rowNum++);
				writeEmployeePayHoursExistsData(employeePayHours, row, excelDateFormat);
			}
			workbook.write(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeEmployeePayHoursExistsData(EmployeePayHours employeePayHours, XSSFRow row,
			SimpleDateFormat excelDateFormat) {
		row.createCell(0).setCellValue(
				Objects.nonNull(employeePayHours.getHoursWorked()) ? employeePayHours.getHoursWorked() : 0);
		row.createCell(1)
				.setCellValue(Objects.nonNull(employeePayHours.getHourlyPay()) ? employeePayHours.getHourlyPay() : 0);
		row.createCell(2)
				.setCellValue(Objects.nonNull(employeePayHours.getWorkDate())
						? excelDateFormat.format(employeePayHours.getWorkDate())
						: "");
		row.createCell(3).setCellValue(
				StringUtils.isNotEmpty(employeePayHours.getUpdatedBy()) ? employeePayHours.getUpdatedBy() : "");
		row.createCell(4).setCellValue(
				Objects.nonNull(employeePayHours.getEmployee()) ? employeePayHours.getEmployee().getId() : 0);
		row.createCell(5)
				.setCellValue(StringUtils.isNotEmpty(employeePayHours.getErrorDescription())
						? employeePayHours.getErrorDescription()
						: "");
	}

	private List<EmployeePayHours> convertExcelToEmployeePayHoursList(MultipartFile file,
			ImportCsvResponse importCsvResponse, SimpleDateFormat excelDateFormat) throws IOException {
		List<EmployeePayHours> data = new ArrayList<>();
		List<EmployeePayHours> errorList = new ArrayList<>();
		Map<Long, Employee> employeeMap = employeeRepo.findAll().stream()
				.collect(Collectors.toMap(Employee::getId, r -> r));
		try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
			int successCount = 0;
			int failureCount = 0;
			XSSFSheet sheet = workbook.getSheetAt(0); // Assuming the data is in the first sheet
			for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				XSSFRow row = sheet.getRow(rowIndex);
				if (StringUtils.isNotEmpty(getStringCellValue(row, 0))) {
					Date wDate = null;
					String hoursWorked = getStringCellValue(row, 0);
					String hourlyPay = getStringCellValue(row, 1);
					String workDate = getStringCellValue(row, 2);
					String updatedBy = getStringCellValue(row, 3);
					Long employee = getLongCellValue(row, 4);
					Employee employees = employeeMap.get(employee);

					if (StringUtils.isNotEmpty(workDate)) {
						try {
							java.util.Date excelDate = excelDateFormat.parse(workDate);
							wDate = new java.sql.Date(excelDate.getTime());
						} catch (ParseException e) {
							EmployeePayHours employeePayHours = new EmployeePayHours();
							employeePayHours.setHoursWorked(Integer.parseInt(hoursWorked));
							employeePayHours.setHourlyPay(Integer.parseInt(hourlyPay));
							employeePayHours.setWorkDate(wDate);
							employeePayHours.setUpdatedBy(updatedBy);
							employeePayHours.setEmployee(employees);
							employeePayHours.setErrorDescription("Invalid Date format");
							errorList.add(employeePayHours);
							failureCount++;
							continue;
						}
					}

					// Create the EmployeePayHours object and add it to the list
					if (!employeeMap.containsKey(employee)) {
						EmployeePayHours employeePayHours = new EmployeePayHours();
						employeePayHours.setHoursWorked((Integer.parseInt(hoursWorked)));
						employeePayHours.setHourlyPay(Integer.parseInt(hourlyPay));
						employeePayHours.setWorkDate(wDate);
						employeePayHours.setUpdatedBy(updatedBy);
						employeePayHours.setEmployee(employees);
						if (Objects.isNull(employee)) {
							employeePayHours.setErrorDescription("Employee should not be empty");
						} else {
							employeePayHours.setErrorDescription(employee + " contractor not present");
						}
						errorList.add(employeePayHours);
						failureCount++;
						continue;
					}

					try {
						EmployeePayHours employeePayHours = new EmployeePayHours(hoursWorked, hourlyPay, wDate,
								updatedBy, employees);
						data.add(employeePayHours);
					} catch (Exception e) {
						EmployeePayHours employeePayHours = new EmployeePayHours();
						employeePayHours.setHoursWorked(Integer.parseInt(hoursWorked));
						employeePayHours.setHourlyPay(Integer.parseInt(hourlyPay));
						employeePayHours.setWorkDate(wDate);
						employeePayHours.setUpdatedBy(updatedBy);
						employeePayHours.setEmployee(employees);
						employeePayHours.setErrorDescription(e.getMessage());
						errorList.add(employeePayHours);
						failureCount++;
					}
				}
			}
			for (EmployeePayHours employeePayHours : data) {
				employeePayHoursRepo.save(employeePayHours);
				successCount++;
			}
			importCsvResponse.setFailureCount(failureCount);
			importCsvResponse.setSuccessCount(successCount);
		}
		return errorList;
	}

}