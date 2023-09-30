package com.tm.app.entity;

import java.sql.Date;

import com.tm.app.enums.SpareReturns;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "t_machinery_spare_details")
public class MachinerySpareDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String spareItemName;
	private Date serviceReturnDate;
	private Date estimatedDate;
	private String description;
	@Enumerated(EnumType.STRING)
	private SpareReturns spareReturns;
}
