package com.tm.app.entity;

import org.hibernate.annotations.Subselect;
import org.springframework.data.annotation.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
@Immutable
@Subselect(value = "SELECT EXTRACT(YEAR FROM updated_at) AS yr, TO_CHAR(updated_at, 'Month') AS month COUNT(DISTINCT client_name) AS customer_count FROM  t_customer tc inner join t_order to2 on to2.customer_id  =tc.id GROUP BY TO_CHAR(updated_at, 'Month'), EXTRACT(YEAR FROM updated_at) ORDER BY TO_CHAR(updated_at, 'Month');")
public class OrdersPerCustomer {

	@Id

	private Integer yr;
	private String month;
	private Integer customerCount;

}
