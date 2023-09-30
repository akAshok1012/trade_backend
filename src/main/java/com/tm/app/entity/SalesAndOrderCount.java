package com.tm.app.entity;

import java.sql.Date;

import org.springframework.data.annotation.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
@Immutable
public class SalesAndOrderCount {

	@Id
	@Column(name="date")
	private Date date;
	@Column(name="salescount")
	private Integer salesCount;
	@Column(name="ordercount")
	private Integer orderCount;

}
