package com.spring.boot.ecommerce.services.order;

import com.spring.boot.ecommerce.auth.AuthUser;
import com.spring.boot.ecommerce.model.request.order.OrderRequest;
import com.spring.boot.ecommerce.model.response.order.OrderResponse;
import com.spring.boot.ecommerce.model.response.order.ListOrderResponse;
import org.springframework.data.domain.Page;

public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest, AuthUser authUser);

    OrderResponse updateOrder(String id, OrderRequest orderRequest, AuthUser authUser);

    OrderResponse getOrder(String id);

    void delete(String id);

    Page<ListOrderResponse> getAllOrder(int pageNumber, int pageSize);
}
