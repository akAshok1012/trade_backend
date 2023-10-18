package com.tm.app.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.context.annotation.Lazy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "t_order_items", indexes = @Index(name = "order_item_index", columnList = "order_id,item_id"))
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@Lazy
	@JoinColumn(name = "order_id")
	private Order order;

	@OneToOne
	@Lazy
	@JoinColumn(name = "item_id")
	private ItemMaster itemMaster;

	@Column(name = "ordered _quantity")
	private Integer orderedQuantity;

	@OneToOne
	@JoinColumn(name = "unit_of_measure")
	private UnitOfMeasure unitOfMeasure;

	@Column(name = "unit_price")
	private Float unitPrice;

	@Column(name = "total_amount", columnDefinition = "FLOAT default 0.00")
	private Float totalAmount;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@Column(name = "updated_by")
	private String updatedBy;

}
