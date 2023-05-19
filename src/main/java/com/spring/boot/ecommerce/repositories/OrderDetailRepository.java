package com.spring.boot.ecommerce.repositories;

import com.spring.boot.ecommerce.entity.OrderProduct;
import com.spring.boot.ecommerce.model.response.order.OrderDetailResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderProduct, String> {

    @Query(value = "SELECT new com.spring.boot.ecommerce.model.response.order.OrderDetailResponse(p, op)" +
            " FROM Product p LEFT JOIN OrderProduct op ON p.id = op.productId" +
            " WHERE op.orderId = :orderId"
    )
    List<OrderDetailResponse> findAllByOrderId(@Param("orderId") String orderId);

    OrderProduct deleteAllByOrderId(String id);

    OrderProduct getById(String id);
}
