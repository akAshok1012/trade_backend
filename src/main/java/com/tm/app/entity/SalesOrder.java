package com.tm.app.entity;

import java.sql.Date;
import java.sql.Timestamp;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.context.annotation.Lazy;

import com.tm.app.enums.SalesStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "t_sales_order",indexes = @Index(name = "salesOrder_index",columnList = "order_id,sales_id,customer_id"))
public class SalesOrder {

	@Id
	@Column(name = "sales_id")
	private Integer salesId;

	@Column(name = "order_id")
	private Integer order;

	@OneToOne
	@Lazy
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@Column(name = "is_return")
	private Boolean isReturn;

	@Column(name = "delivery_date")
	private Date deliveryDate;

	@Enumerated(EnumType.STRING)
	private SalesStatus salesStatus;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@Column(name = "updated_by")
	private String updatedBy;

}
