package com.tm.app.entity;

import org.hibernate.annotations.Subselect;
import org.springframework.data.annotation.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
@Immutable
@Subselect("select count(distinct ts.in_stock) as stock, count(distinct ts.item_master) as item, tb.id as brand from t_stock ts, t_brand tb group by item_master, tb.id")
public class StockCount {

	@Id
	private Integer stock;

	private Integer item;
	private Integer brand;

}
