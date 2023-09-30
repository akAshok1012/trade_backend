package com.tm.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "t_production_unit_of_measure")
public class ProductionUnitOfMeasure {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "production_id")
	private Production production;

	@ManyToOne
	@JoinColumn(name = "unit_of_measure_id")
	private UnitOfMeasure unitOfMeasure;
	
	@Column(name = "quantity")
	private Integer quantity;

}
