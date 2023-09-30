package com.tm.app.dto;

import java.sql.Date;
import java.util.List;

import com.tm.app.entity.MachinerySpareDetails;
import com.tm.app.enums.SpareStatus;

import lombok.Data;

@Data
public class MachinerySparesDto {

	private List<MachinerySpareDetails> machinerySpareDetails;
	private Date serviceGivenDate;
	private String remarks;
	private String technicianName;
	private SpareStatus status;
	private String updatedBy;
}
