package com.tm.app.dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.tm.app.entity.Customer;
import com.tm.app.entity.ItemDetails;
import com.tm.app.entity.OrderItem;
import com.tm.app.enums.SalesStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrderDto {

	private Integer salesId;
	private OrderItem order;
	private Customer customer;
	private Boolean isReturn;
	private Date deliveryDate;
	private SalesStatus salesStatus;	
	private List<ItemDetails> itemDetails;
	private Timestamp updatedAt;
	private String updatedBy;

}
