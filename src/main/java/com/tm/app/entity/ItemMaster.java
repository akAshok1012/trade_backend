package com.tm.app.entity;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name = "t_item_master", indexes = @Index(name = "item_master_index", columnList = "item_category_id,brand_id"))
public class ItemMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "item_name")
	private String itemName;

	@Column(name = "item_description")
	private String itemDescription;

	@OneToOne
	@JoinColumn(name = "item_category_id")
	private ItemCategory itemCategory;

	@ManyToMany
	@JoinTable(name = "t_item_unit_of_measure", joinColumns = @JoinColumn(name = "item_id"), inverseJoinColumns = @JoinColumn(name = "unit_of_measure_id"))
	private List<UnitOfMeasure> unitOfMeasures;

	@Column(name = "fixed_price")
	private Float fixedPrice;

	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(name = "item_image", columnDefinition = "bytea")
	@JdbcTypeCode(Types.BINARY)
	private byte[] itemImage;

	@Column(name = "updated_by")
	private String updatedBy;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@OneToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;

	@Transient
	private String itemImageString;

	@Transient
	private String unitMeasureString;

}