package com.spring.boot.ecommerce.repositories;

import com.spring.boot.ecommerce.entity.OrderInfo;
import com.spring.boot.ecommerce.model.response.dashboard.Chart;
import com.spring.boot.ecommerce.model.response.order.ListOrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderInfo, String> {

    OrderInfo getById(String id);

    @Query(value = " SELECT new com.spring.boot.ecommerce.model.response.order.ListOrderResponse(u, oi) " +
            "FROM OrderProduct op, OrderInfo oi, User u " +
            "WHERE op.orderId = oi.id and oi.customerId = u.id" +
            " ORDER BY oi.createdDate DESC")
    Page<ListOrderResponse> getAllByIdExists(Pageable pageable);

    @Query(value = "SELECT DATE_FORMAT(oi.createdDate, '%m-%Y') AS M_and_Y, COUNT(oi.id) AS count_order" +
            " FROM OrderInfo oi" +
            " GROUP BY M_and_Y")
    List<OrderInfo> countByCreatedDate();

    @Query(value = "SELECT COUNT(*) FROM OrderInfo")
    int countById();

    @Query(value = "SELECT SUM(oi.total) FROM OrderInfo oi")
    double queryTopByTotal();

    @Query(value = " SELECT new com.spring.boot.ecommerce.model.response.dashboard.Chart(DATE_FORMAT(oi.createdDate, '%d-%m-%Y'), COUNT(oi.id)) "
                 + " FROM OrderInfo oi GROUP BY DATE_FORMAT(oi.createdDate, '%m-%Y')"
                 + " ORDER BY DATE_FORMAT(oi.createdDate, '%m-%Y') DESC")
    List<Chart> countByCreatedDate_YearAndCreatedDate_Month();
}
