package com.tm.app.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.dto.ShipmentCustomerUserStatusDto;
import com.tm.app.dto.ShipmentHistoryDto;
import com.tm.app.dto.ShipmentItemDto;
import com.tm.app.dto.ShipmentStatusDto;
import com.tm.app.dto.ShipmentStatusSalesIdDto;
import com.tm.app.entity.Customer;
import com.tm.app.entity.Shipment;
import com.tm.app.entity.ShipmentDetails;
import com.tm.app.enums.ShipmentStatus;

@Repository
public interface ShipmentDetailsRepo extends JpaRepository<ShipmentDetails, Long> {

	List<ShipmentDetails> findByShipmentId(Shipment shipment);

	@Query("SELECT new com.tm.app.dto.ShipmentItemDto(o as orderItem, sd.orderQuantity as orderQuantity, sd.shippedQuantity as shippedQuantity, sd.balanceQuantity as balanceQuantity,sd.status as status) FROM ShipmentDetails sd join Shipment s on (s.id=sd.shipmentId) join SalesOrder so on (so.salesId=s.salesId) join OrderItem o on (o.id=sd.orderItemId) WHERE s.salesId=:salesId")
	List<ShipmentItemDto> getShipmentBySalesId(Integer salesId);

	@Query("SELECT new com.tm.app.dto.ShipmentStatusDto(s.salesId as salesId,c.name as customerName,s.shipmentStatus as shipmentStatus) from Shipment s Join SalesOrder so on (s.salesId = so.salesId) Join Customer c on(c.id=so.customer) where s.shipmentStatus =:shipmentStatus and LOWER(c.name) like LOWER(:search) group by s.salesId,c.name,s.shipmentStatus")
	Page<ShipmentStatusDto> getShipmentStatus(ShipmentStatus shipmentStatus, PageRequest of, String search);

	@Query("SELECT sd FROM ShipmentDetails sd join Shipment s on (s.id = sd.shipmentId) join SalesOrder so on (so.salesId=s.salesId) join OrderItem o on (o.order=so.order) join Order or on (or.orderId=o.order) where s.salesId=:salesId and or.customer=:customer")
	List<ShipmentDetails> getCustomerShipmentBySalesId(Integer salesId, Customer customer);

	@Query("SELECT new com.tm.app.dto.ShipmentCustomerUserStatusDto(s.salesId as salesId,c.name as name,sd.status as shipmentStatus,u.id as user) from ShipmentDetails sd join Shipment s on (s.id=sd.shipmentId) Join SalesOrder so on (s.salesId = so.salesId) Join Customer c on(c.id=so.customer) Join User u on (u.userId=c.id) where u.id=:id and sd.status=:shipmentStatus ")
	Page<ShipmentCustomerUserStatusDto> getShipmentUserId(Long id, ShipmentStatus shipmentStatus, PageRequest of);

	@Query("SELECT new com.tm.app.dto.ShipmentStatusDto(s.salesId as salesId,c.name as name,CASE WHEN COUNT(distinct  s.shipmentStatus) > 1 THEN s.shipmentStatus ELSE s.shipmentStatus end as shipmentStatus) from Shipment s Join SalesOrder so on (s.salesId = so.salesId) Join Customer c on(c.id=so.customer) where s.shipmentStatus =:shipmentStatus and cast(so.salesId as text) like (:salesIdString) GROUP BY s.salesId,c.name,s.shipmentStatus")
	Page<ShipmentStatusDto> getShipmentStatusSalesId(ShipmentStatus shipmentStatus,PageRequest of);

	@Query("SELECT new com.tm.app.dto.ShipmentHistoryDto(s.salesId as salesId,sh.shipmentDate as shipmentDate,i.itemName as itemName,oi.orderedQuantity as orderQuantity,sh.shippedQuantity as shippedQuantity,s.balanceQuantity as balanceQuantity,sh.updatedAt as updatedAt,sh.carrier as carrier)from ShipmentHistory sh join Shipment s on (s.id=sh.shipment) Join OrderItem oi on(oi.id=s.orderItem) join  ItemMaster i on(i.id=oi.itemMaster) where s.salesId =:salesId")
	List<ShipmentHistoryDto> getShipmentHistoryBySalesId(Integer salesId);

	@Query("SELECT new com.tm.app.dto.ShipmentStatusSalesIdDto(s.salesId as salesId,c.name as customerName,s.shipmentStatus as shipmentStatus) from Shipment s Join SalesOrder so on (s.salesId = so.salesId) Join Customer c on(c.id=so.customer)  where cast(s.salesId as text) like (:salesIdString) and s.shipmentStatus=:shipmentStatus GROUP BY s.salesId,c.name,s.shipmentStatus")
	Page<ShipmentStatusSalesIdDto> getShipmentStatusSalesId(ShipmentStatus shipmentStatus, String salesIdString, PageRequest of);

	@Query("SELECT new com.tm.app.dto.ShipmentStatusSalesIdDto(s.salesId as salesId,c.name as customerName,s.shipmentStatus as shipmentStatus) from Shipment s Join SalesOrder so on (s.salesId = so.salesId) Join Customer c on(so.customer=c.id) where s.shipmentStatus=:shipmentStatus and c.id=:customerId and cast(s.salesId as text) like (:salesIdString) GROUP BY s.salesId,c.name,s.shipmentStatus")
	Page<ShipmentStatusSalesIdDto> getShipmentStatusByCustomerAndSalesId(Long customerId,ShipmentStatus shipmentStatus, String salesIdString,
			PageRequest of);
}