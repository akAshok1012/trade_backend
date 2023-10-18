package com.tm.app.entity;

import org.hibernate.annotations.Subselect;
import org.springframework.data.annotation.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
@Immutable
@Subselect("select tb.id as brand, tp.item_id as itemid, tim.item_name as item, SUM(tpd.quantity) as quantity from t_production tp join t_production_details tpd on (tp.id = tpd.production_id) join t_item_master tim on (tim.id = tp.item_id) join t_brand tb on (tim.brand_id = tb.id) join t_unit_of_measure tuom on (tuom.id = tb.id) group by tb.id, tp.item_id, tim.item_name")
public class ProductionCount {

	private Integer brand;
	@Id
	private String item;
	private Integer itemid;
	private Integer quantity;

}
