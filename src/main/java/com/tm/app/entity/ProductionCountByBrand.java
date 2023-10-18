package com.tm.app.entity;

import org.hibernate.annotations.Subselect;
import org.springframework.data.annotation.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
@Immutable
@Subselect("select tb.id as brand, tb.name as brandName, SUM(tpd.quantity) as quantity from t_production tp join t_production_details tpd on (tp.id = tpd.production_id) join t_item_master tim on (tim.id = tp.item_id) join t_brand tb on (tim.brand_id = tb.id) group by tb.id, tb.name;")
public class ProductionCountByBrand {

	@Id
	
	private Long brand;
	private String brandname;
	private Float quantity;

}
