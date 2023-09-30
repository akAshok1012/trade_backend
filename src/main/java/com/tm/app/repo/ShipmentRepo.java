package com.tm.app.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.dto.ShipmentCustomerListDto;
import com.tm.app.dto.ShipmentItemDto;
import com.tm.app.dto.ShipmentStatusSalesIdDto;
import com.tm.app.entity.Customer;
import com.tm.app.entity.Shipment;
import com.tm.app.enums.ShipmentStatus;

@Repository
public interface ShipmentRepo extends JpaRepository<Shipment, Long> {

	List<Shipment> findBySalesId(Integer integer);

	@Query("SELECT NEW com.tm.app.dto.ShipmentCustomerListDto(s.salesId AS salesId, tc.name AS name,CASE WHEN SUM(s.balanceQuantity) = 0 THEN 'SHIPPED' WHEN SUM(s.balanceQuantity) = SUM(toi.orderedQuantity) THEN 'PENDING' ELSE 'PARTIAL' END AS shipmentStatus) FROM Shipment s JOIN SalesOrder so on (s.salesId =so.salesId) JOIN Customer  tc on(tc.id=so.customer) JOIN OrderItem toi on(toi.id=s.orderItem) where cast(s.salesId as text) like (:salesIdString) GROUP BY s.salesId, tc.name having sum(s.balanceQuantity) !=0")
	Page<ShipmentCustomerListDto> getShipmentsBySalesId(String salesIdString, PageRequest pageRequest);

	@Query("SELECT NEW com.tm.app.dto.ShipmentCustomerListDto(s.salesId AS salesId, tc.name AS name,CASE WHEN SUM(s.balanceQuantity) = 0 THEN 'SHIPPED' WHEN SUM(s.balanceQuantity) = SUM(toi.orderedQuantity) THEN 'PENDING' ELSE 'PARTIAL' END AS shipmentStatus) from Shipment s JOIN SalesOrder so ON(s.salesId=so.salesId) JOIN Customer tc ON(so.customer=tc.id) JOIN OrderItem toi on(toi.id=s.orderItem) WHERE Lower(tc.name) like LOWER(:search) group by s.salesId, tc.name having sum(s.balanceQuantity) !=0")
	Page<ShipmentCustomerListDto> getShipmentListingWithCustomer(PageRequest of, String search);

	@Query("select s from Shipment s where s.salesId=:salesId and shipmentStatus NOT IN ('SHIPPED')")
	List<Shipment> getShipmentDetailsBySalesId(Integer salesId);

	@Query("select ts from Shipment ts where ts.salesId=:salesId and ts.orderItem.id in(:orderItemList)")
	List<Shipment> findBySalesIdAndOrderItemIn(Integer salesId, List<Long> orderItemList);

	@Query("SELECT new com.tm.app.dto.ShipmentItemDto(o as orderItem, o.orderedQuantity as orderedQuantity, s.shippedQuantity as shippedQuantity, s.balanceQuantity as balanceQuantity,s.shipmentStatus as shipmentStatus) FROM Shipment s join OrderItem o on (o.id=s.orderItem) WHERE s.salesId=:salesId")
	List<ShipmentItemDto> getShipmentBySalesId(Integer salesId);

	@Query("SELECT new com.tm.app.dto.ShipmentStatusSalesIdDto(s.salesId as salesId,c.name as customerName,s.shipmentStatus as shipmentStatus) from Shipment s Join SalesOrder so on (s.salesId = so.salesId) Join Customer c on(c.id=so.customer)  where cast(s.salesId as text) like (:salesIdString) and s.shipmentStatus=:shipmentStatus ")
	Page<ShipmentStatusSalesIdDto> getShipmentStatusSalesId(String salesIdString, ShipmentStatus shipmentStatus,
			PageRequest of);

	@Query("SELECT new com.tm.app.dto.ShipmentStatusSalesIdDto(s.salesId as salesId,c.name as customerName,s.shipmentStatus as shipmentStatus) from Shipment s Join SalesOrder so on (s.salesId = so.salesId) Join Customer c on(c.id=so.customer.id) where so.customer=:customer and s.shipmentStatus=:shipmentStatus and cast(s.salesId as text) like (:salesIdString) or Lower(c.name) like LOWER(:search)")
	Page<ShipmentStatusSalesIdDto> getShipmentStatusByCustomerAndSalesId(Customer customer, String salesIdString,
			ShipmentStatus shipmentStatus, String search, PageRequest of);
}