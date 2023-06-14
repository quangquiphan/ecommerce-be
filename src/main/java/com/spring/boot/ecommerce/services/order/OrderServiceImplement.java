package com.spring.boot.ecommerce.services.order;

import com.spring.boot.ecommerce.auth.AuthUser;
import com.spring.boot.ecommerce.common.enums.OrderStatus;
import com.spring.boot.ecommerce.common.exceptions.ApplicationException;
import com.spring.boot.ecommerce.common.utils.RestAPIStatus;
import com.spring.boot.ecommerce.common.utils.UniqueID;
import com.spring.boot.ecommerce.entity.OrderInfo;
import com.spring.boot.ecommerce.entity.OrderProduct;
import com.spring.boot.ecommerce.entity.Product;
import com.spring.boot.ecommerce.model.request.order.OrderRequest;
import com.spring.boot.ecommerce.model.response.order.OrderDetailResponse;
import com.spring.boot.ecommerce.model.response.order.OrderResponse;
import com.spring.boot.ecommerce.model.response.order.ListOrderResponse;
import com.spring.boot.ecommerce.repositories.OrderDetailRepository;
import com.spring.boot.ecommerce.repositories.OrderRepository;
import com.spring.boot.ecommerce.repositories.ProductRepository;
import com.spring.boot.ecommerce.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImplement implements OrderService {

    final private OrderRepository orderRepository;
    final private OrderDetailRepository orderDetailRepository;
    final private ProductRepository productRepository;
    final private UserRepository userRepository;

    public OrderServiceImplement(
            OrderRepository orderRepository,
            OrderDetailRepository orderDetailRepository,
            ProductRepository productRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest, AuthUser authUser) {
        //        Order
        double total = 0;

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(UniqueID.getUUID());
        orderInfo.setCustomerId(authUser.getId());
        orderInfo.setPhoneNumber(orderRequest.getPhoneNumber());
        orderInfo.setAddressReceive(orderRequest.getAddressReceive());
        //        Order Detail
        List<OrderDetailResponse> orderDetailResponses = new ArrayList<>();
        List<OrderProduct> orderProducts = new ArrayList<>();

        for (int i = 0; i < orderRequest.getOrderDetail().size(); i++) {
            Product product = productRepository.getById(orderRequest.getOrderDetail().get(i).getProductId());

            if (product == null) {
                break;
            }

            if (product.getQuantity() < orderRequest.getOrderDetail().get(i).getQuantity()) {
                throw new ApplicationException(RestAPIStatus.BAD_REQUEST, "");
            }

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setId(UniqueID.getUUID());
            orderProduct.setOrderId(orderInfo.getId());
            orderProduct.setProductId(orderRequest.getOrderDetail().get(i).getProductId());
            orderProduct.setQuantity(orderRequest.getOrderDetail().get(i).getQuantity());

            product.setQuantity(product.getQuantity() - orderProduct.getQuantity());
            total += calcTotal(product.getPrice(), product.getDiscount(), orderProduct.getQuantity());
            orderProducts.add(orderProduct);

            OrderDetailResponse orderDetailResponse = new OrderDetailResponse(product, orderProduct);
            orderDetailResponses.add(orderDetailResponse);

            productRepository.save(product);
        }

        orderInfo.setTotal(total);
        orderInfo.setStatus(OrderStatus.ORDERED);

        orderRepository.save(orderInfo);
        orderDetailRepository.saveAll(orderProducts);
        return new OrderResponse(authUser, orderInfo, orderDetailResponses);
    }

    @Override
    public OrderResponse getOrder(String id) {
        OrderInfo orderInfo = orderRepository.getById(id);

        if (orderInfo == null) {
            throw new ApplicationException(RestAPIStatus.NOT_FOUND);
        }

        AuthUser authUser = new AuthUser(userRepository.getById(orderInfo.getCustomerId()));

        List<OrderDetailResponse> orderDetailResponses = orderDetailRepository.findAllByOrderId(orderInfo.getId());
        return new OrderResponse(authUser, orderInfo, orderDetailResponses);
    }

    @Override
    public OrderInfo changeStatus(String id, String status) {
        OrderInfo order = orderRepository.getById(id);

        if (order == null) {
            throw new ApplicationException(RestAPIStatus.NOT_FOUND);
        }

        order.setStatus(OrderStatus.valueOf(status));
        return orderRepository.save(order);
    }

    @Override
    public void delete(String id) {
        OrderInfo orderInfo = orderRepository.getById(id);

        if (orderInfo == null) {
            throw new ApplicationException(RestAPIStatus.NOT_FOUND);
        }

        orderDetailRepository.findAllByOrderId(orderInfo.getId());
        orderRepository.delete(orderInfo);
    }

    @Override
    public Page<ListOrderResponse> getAllOrder(int pageNumber, int pageSize, OrderStatus status) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);

        return orderRepository.getAllByStatus(pageRequest, status);
    }

    private double calcTotal(double price, double discount, int quantity) {
        return (price - discount) * quantity;
    }
}
