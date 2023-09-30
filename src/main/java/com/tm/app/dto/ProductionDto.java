package com.tm.app.dto;

import java.sql.Date;
import java.util.List;

import com.tm.app.entity.ItemMaster;
import com.tm.app.entity.ProductionDetails;
import com.tm.app.enums.ProductionStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionDto {

	private List<ProductionDetails> productionDetails;
	private ItemMaster itemMaster;
	private Date startDate;
	private Date endDate;
	private ProductionStatus status;
	private String supervisor;
	private String notes;
	private String updatedBy;
}
