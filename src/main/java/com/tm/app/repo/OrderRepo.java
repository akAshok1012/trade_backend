package com.tm.app.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.dto.CustomerNotificationDto;
import com.tm.app.dto.ManageSalesOrderDto;
import com.tm.app.dto.OrderApprovedNotificationDto;
import com.tm.app.dto.OrderIDAndCountDto;
import com.tm.app.dto.OrderIdCustomerNameDto;
import com.tm.app.dto.OrderIdDateTimeDto;
import com.tm.app.dto.OrderPlacedNotificationDto;
import com.tm.app.entity.Customer;
import com.tm.app.entity.Order;
import com.tm.app.enums.OrderStatus;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

	Order findByOrderId(Integer orderId);

	@Query("select new com.tm.app.dto.OrderIdCustomerNameDto(o.orderId as orderId,o.updatedAt as dateTime) from Order o where o.customer=:customer and orderStatus ='PENDING'")
	List<OrderIdCustomerNameDto> findByCustomer(Customer customer);

	Order findByOrderStatus(OrderStatus orderStatus);

	@Query("select new com.tm.app.dto.OrderIDAndCountDto(im.orderId as orderId,count(*)as count) from Order im where orderStatus ='PENDING' group by im.orderId")
	List<OrderIDAndCountDto> getOrderIdAndCount();

	@Query("select new com.tm.app.dto.OrderIdDateTimeDto(o.orderId as orderId,o.customer as customer,o.updatedAt as dateTime) from Order o where orderStatus ='PENDING'")
	List<OrderIdDateTimeDto> getOrderIdDate();

	@Query("select new com.tm.app.dto.ManageSalesOrderDto(o.orderId as orderId,c.name as customer,c.id as customerId,o.orderStatus as orderStatus) from Order o Join Customer c on(c.id=o.customer) where o.orderStatus =:orderStatus And LOWER(c.name) like LOWER(:search)")
	Page<ManageSalesOrderDto> findByOrderStatusAndSearch(OrderStatus orderStatus, String search,
			PageRequest pageRequest);

	void deleteByOrderId(Integer orderId);

	Order findByOrderIdAndOrderStatus(Integer orderId, OrderStatus pending);

	@Query("select new com.tm.app.dto.CustomerNotificationDto(o.orderId as orderId,c.name as name,o.orderStatus as orderStatus,o.updatedAt as dateTime,u.id as user) from Order o JOIN Customer c ON(c.id=o.customer) JOIN User u ON(u.userId=c.id) where o.orderStatus IN ('APPROVED','REJECTED') and u.id=:id and o.isNotificationEnabled=false")
	List<CustomerNotificationDto> getCustomerNotification(Long id);

	boolean existsByOrderId(Integer orderNumber);

	@Query("select new com.tm.app.dto.OrderPlacedNotificationDto(o.orderId as orderId,c.name as name,o.orderStatus as orderStatus,oi.totalAmount as totalAmount) from Order o JOIN OrderItem oi on (o.orderId=oi.order) JOIN Customer c On(c.id = o.customer) where o.orderStatus = 'PENDING'")
	List<OrderPlacedNotificationDto> getOrderNotification();

	@Query("Select new com.tm.app.dto.OrderApprovedNotificationDto(o.orderId as orderId,c.name as name,o.orderStatus as orderStatus) from Order o join Customer c on(o.customer=c.id) where o.orderStatus = 'APPROVED'")
	List<OrderApprovedNotificationDto> getOrderApprovedNotification();

}