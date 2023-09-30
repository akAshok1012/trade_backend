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
import com.tm.app.dto.EmployeeDto;
import com.tm.app.dto.EmployeeIdNameDto;
import com.tm.app.dto.EmployeeUserDto;
import com.tm.app.entity.Employee;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.security.annotations.IsSuperAdminOrAdminOrEmployee;
import com.tm.app.service.EmployeeService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.APIResponseConstants;
import com.tm.app.utils.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employee")
    @IsSuperAdminOrAdmin
    public APIResponse<?> saveEmployee(@RequestBody EmployeeDto employeeDto) {
	log.info("[EmployeeController] saveEmployee starts...");
	try {
	    Employee employees = employeeService.saveEmployee(employeeDto);
	    return Response.getSuccessResponse(employees,
		    String.format(APIResponseConstants.CREATION_SUCCESS_MESSAGE, employees.getName()), HttpStatus.OK);
	} catch (Exception e) {
	    log.error("[EmployeeController] saveEmployee failed...", e);
	    return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}

    }

    @GetMapping("/employees")
    @IsSuperAdminOrAdmin
    public APIResponse<?> getEmployees() {
	log.info("[EmployeeController] getEmployees starts...");
	try {
	    List<Employee> employees = employeeService.getEmployees();
	    return Response.getSuccessResponse(employees, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
	} catch (Exception e) {
	    log.error("[EmployeeController] getEmployees failed...", e);
	    return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}
    }

    @GetMapping("/employee/{id}")
    @IsSuperAdminOrAdmin
    public APIResponse<?> getEmployeeById(@PathVariable("id") Long id) {
	log.info("[EmployeeController] getEmployeeById starts...");
	try {
	    Employee employees = employeeService.getEmployeeById(id);
	    return Response.getSuccessResponse(employees, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
	} catch (Exception e) {
	    log.error("[EmployeeController] getEmployeeById failed...", e);
	    return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}
    }

    @PutMapping("/employee/{id}")
    @IsSuperAdminOrAdmin
    public APIResponse<?> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDto employeeDto) {
	log.info("[EmployeeController] updateEmployee starts...");
	try {
	    Employee employees = employeeService.updateEmployee(id, employeeDto);
	    return Response.getSuccessResponse(employees,
		    String.format(APIResponseConstants.UPDATED_SUCCESS_MESSAGE, employees.getName()), HttpStatus.OK);
	} catch (Exception e) {
	    log.error("[EmployeeController] updateEmployee failed...", e);
	    return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}
    }

    @DeleteMapping("/employee/{id}")
    @IsSuperAdminOrAdmin
    public APIResponse<?> deleteEmployeeById(@PathVariable("id") Long id) {
	log.info("[EmployeeController] deleteEmployeeById starts...");
	try {
	    employeeService.deleteEmployeeById(id);
	} catch (Exception e) {
	    log.error("[EmployeeController] deleteEmployeeById failed...", e);
	    return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	log.info("[EmployeeController] deleteEmployeeById ends...");
	return Response.getSuccessResponse(null, APIResponseConstants.DELETE_RESPONSE_MESSAGE, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/employee-name")
    @IsSuperAdminOrAdmin
    public APIResponse<?> getEmployeeAndName() {
	log.info("[EmployeeController] getEmployeeAndName starts...");
	try {
	    List<EmployeeIdNameDto> employees = employeeService.getEmployeeIdAndName();
	    return Response.getSuccessResponse(employees, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
	} catch (Exception e) {
	    log.error("[EmployeeController] getEmployeeAndName faileds...", e);
	    return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}
    }

    @GetMapping("/employee-by-userId")
    @IsSuperAdminOrAdminOrEmployee
    public APIResponse<?> getEmployeeByUserId(@RequestParam Long id) {
	log.info("[EmployeeController] getEmployeeByUserId starts...");
	try {
	    Employee employee = employeeService.getEmployeeByUserId(id);
	    return Response.getSuccessResponse(employee, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
	} catch (Exception e) {
	    log.error("[EmployeeController] getEmployeeByUserId failed...", e);
	    return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}
    }

    @GetMapping("/employee-list")
    @IsSuperAdminOrAdmin
    public APIResponse<?> getEmployeeList(@ModelAttribute DataFilter dataFilter) {
	log.info("[EmployeeController] getEmployeeList starts...");
	try {
	    Page<Employee> employees = employeeService.getEmployeeList(dataFilter);
	    return Response.getSuccessResponse(employees, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
	} catch (Exception e) {
	    log.error("[EmployeeController] getEmployeeList failed...", e);
	    return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}
    }

    @GetMapping("/employee-user")
    @IsSuperAdminOrAdminOrEmployee
    public APIResponse<?> getEmployeeUser(@RequestParam("id") Long id) {
	try {
	    Employee employeeUserDto = employeeService.getEmployeeUser(id);
	    return Response.getSuccessResponse(employeeUserDto, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
		    HttpStatus.OK);
	} catch (Exception e) {
	    e.printStackTrace();
	    return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}
    }

    @PutMapping("/employee-user")
    @IsSuperAdminOrAdminOrEmployee
    public APIResponse<?> updateEmployeeUser(@RequestParam("id") Long id,
	    @RequestBody EmployeeUserDto employeeUserDto) {
	try {
	    Employee employee = employeeService.updateEmployeeUser(id, employeeUserDto);
	    return Response.getSuccessResponse(employee, "Employee profile Updated Successfully", HttpStatus.OK);
	} catch (Exception e) {
	    e.printStackTrace();
	    return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}
    }
}