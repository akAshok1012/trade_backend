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
import org.springframework.web.bind.annotation.RestController;

import com.tm.app.dto.DataFilter;
import com.tm.app.dto.StockDto;
import com.tm.app.entity.Stock;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.service.StockService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.Response;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StockController {

	@Autowired
	private StockService stockService;

	@PostMapping("/stock")
	@IsSuperAdminOrAdmin
	public APIResponse<?> createStock(@RequestBody StockDto stockDto) {
		try {
			Stock stock = stockService.createStock(stockDto);
			return Response.getSuccessResponse(stock, "Success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/stocks")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getStock() {
		try {
			List<Stock> stock = stockService.getStock();
			return Response.getSuccessResponse(stock, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/stock/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getStockById(@PathVariable("id") Long id) {
		try {
			Stock stock = stockService.getStockById(id);
			return Response.getSuccessResponse(stock, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/stock/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> updateStock(@PathVariable Long id, @RequestBody StockDto stockDto) {
		try {
			Stock stock = stockService.updateStock(id, stockDto);
			return Response.getSuccessResponse(stock, "Updated Successfully", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/stock/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> deleteStockById(@PathVariable("id") Long id) {
		try {
			stockService.deleteStockById(id);
			return Response.getSuccessResponse(null, "Deleted Successfully", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/stock-list")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getStockList(@ModelAttribute DataFilter dataFilter ) {
		try {
			Page<Stock> stock = stockService.getStockList(dataFilter);
			return Response.getSuccessResponse(stock, "Success", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
