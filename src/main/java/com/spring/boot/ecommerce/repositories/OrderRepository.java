package com.spring.boot.ecommerce.repositories;

import com.spring.boot.ecommerce.entity.OrderInfo;
import com.spring.boot.ecommerce.model.response.order.ListOrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderInfo, String> {

    OrderInfo getById(String id);

    @Query(value = " SELECT new com.spring.boot.ecommerce.model.response.order.ListOrderResponse(u, oi) " +
            "FROM OrderProduct op, OrderInfo oi, User u " +
            "WHERE op.orderId = oi.id and oi.customerId = u.id")
    Page<ListOrderResponse> getAllByIdExists(Pageable pageable);
}
