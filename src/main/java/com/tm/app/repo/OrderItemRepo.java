package com.tm.app.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.dto.OrderStatusCustomerNameDto;
import com.tm.app.entity.Order;
import com.tm.app.entity.OrderItem;
import com.tm.app.enums.OrderStatus;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder(Order order);

    @Query("SELECT new com.tm.app.dto.OrderStatusCustomerNameDto (o.orderId as orderId,o.orderStatus as orderStatus,c.name as name,u.id as user,o.updatedAt as updatedAt,r.rejectReason as rejectReason ) from Order o JOIN Customer c ON(o.customer = c.id) JOIN User u on(c.id=u.userId) OUTER JOIN RejectReason r ON(r.id=o.rejectReason) where u.id= :id and o.orderStatus=:orderStatus")
    Page<OrderStatusCustomerNameDto> getOrderStatusByUserId(Long id, OrderStatus orderStatus, PageRequest pageRequest);

    void deleteAllByOrder(Order order);

}
