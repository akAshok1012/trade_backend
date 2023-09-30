package com.tm.app.entity;

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
@Table(name = "t_item_unit_of_measure")
public class ItemUnitOfMeasure {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "item_id")
	private ItemMaster itemMaster;

	@ManyToOne
	@JoinColumn(name = "unit_of_measure_id")
	private UnitOfMeasure unitOfMeasure;
}