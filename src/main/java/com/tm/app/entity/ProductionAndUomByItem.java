package com.tm.app.entity;

import org.hibernate.annotations.Subselect;
import org.springframework.data.annotation.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
@Immutable
@Subselect("select tp.item_id as itemid, tuom.unit_name as uom, SUM(tpd.quantity) as quantity from t_production tp join t_production_details tpd on (tp.id = tpd.production_id) join t_unit_of_measure tuom on (tuom.id = tpd.unit_of_measure) group by tuom.unit_name, tp.item_id")
public class ProductionAndUomByItem {


	private Long itemid;
	@Id
	private String uom;
	private Integer quantity;

}
