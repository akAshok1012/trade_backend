package com.tm.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.dto.ShipmentHistoryDto;
import com.tm.app.entity.ShipmentHistory;

@Repository
public interface ShipmentHistoryRepo extends JpaRepository<ShipmentHistory, Long> {

	boolean existsByTrackingNumber(String trackingNumber);

	@Query("SELECT new com.tm.app.dto.ShipmentHistoryDto(s.salesId as salesId,sh.shipmentDate as shipmentDate,i.itemName as itemName,oi.orderedQuantity as orderQuantity,sh.shippedQuantity as shippedQuantity,s.balanceQuantity as balanceQuantity,sh.updatedAt as updatedAt,sh.trackingNumber as trackingNumber,sh.carrier as carrier,uom.unitName as unitName,b.name as brandName)from ShipmentHistory sh join Shipment s on (s.id=sh.shipment) Join OrderItem oi on(oi.id=s.orderItem) join  ItemMaster i on(i.id=oi.itemMaster) join UnitOfMeasure uom on(oi.unitOfMeasure=uom.id) join Brand b on(b.id=i.brand) where s.salesId =:salesId")
	List<ShipmentHistoryDto> getShipmentHistoryBySalesId(Integer salesId);

}
