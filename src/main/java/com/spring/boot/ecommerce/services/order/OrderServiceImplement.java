package com.spring.boot.ecommerce.services.order;

import com.spring.boot.ecommerce.auth.AuthUser;
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
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(UniqueID.getUUID());
        orderInfo.setCustomerId(authUser.getId());
        orderInfo.setPhoneNumber(orderRequest.getPhoneNumber());
        orderInfo.setAddressReceive(orderRequest.getAddressReceive());
        orderInfo.setTotal(orderRequest.getTotal());

        orderRepository.save(orderInfo);
        //        Order Detail
        List<Product> products = productRepository.findAll();
        List<OrderDetailResponse> orderDetailResponses = new ArrayList<>();
        List<OrderProduct> orderProducts = new ArrayList<>();

        orderRequest.getOrderDetail().forEach((item) -> {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setId(UniqueID.getUUID());
            orderProduct.setOrderId(orderInfo.getId());
            orderProduct.setProductId(item.getProductId());
            orderProduct.setQuantity(item.getQuantity());

            orderProducts.add(orderProduct);

            products.forEach((product) -> {
                if (item.getProductId().equals(product.getId())){
                    orderDetailResponses.add(new OrderDetailResponse(product, orderProduct));
                }
            });
        });

        orderDetailRepository.saveAll(orderProducts);
        return new OrderResponse(authUser, orderInfo, orderDetailResponses);
    }

    @Override
    public OrderResponse updateOrder(String id, OrderRequest orderRequest, AuthUser authUser) {
        //        Order
        OrderInfo orderInfo = orderRepository.getById(id);

        if (orderInfo == null) {
            throw new ApplicationException(RestAPIStatus.NOT_FOUND);
        }
        orderDetailRepository.deleteAllByOrderId(orderInfo.getId());

        orderInfo.setCustomerId(authUser.getId());
        orderInfo.setPhoneNumber(orderRequest.getPhoneNumber());
        orderInfo.setAddressReceive(orderRequest.getAddressReceive());
        orderInfo.setTotal(orderRequest.getTotal());

        orderRepository.save(orderInfo);

        //        Order Detail
        List<Product> products = productRepository.findAll();
        List<OrderDetailResponse> orderDetailResponses = new ArrayList<>();
        List<OrderProduct> orderProducts = new ArrayList<>();

        orderRequest.getOrderDetail().forEach((item) -> {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setId(UniqueID.getUUID());
            orderProduct.setOrderId(orderInfo.getId());
            orderProduct.setProductId(item.getProductId());
            orderProduct.setQuantity(item.getQuantity());

            orderProducts.add(orderProduct);

            products.forEach((product) -> {
                if (item.getProductId().equals(product.getId())){
                    orderDetailResponses.add(new OrderDetailResponse(product, orderProduct));
                }
            });
        });

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
    public void delete(String id) {
        OrderInfo orderInfo = orderRepository.getById(id);

        if (orderInfo == null) {
            throw new ApplicationException(RestAPIStatus.NOT_FOUND);
        }

        orderDetailRepository.findAllByOrderId(orderInfo.getId());
        orderRepository.delete(orderInfo);
    }

    @Override
    public Page<ListOrderResponse> getAllOrder(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);

        return orderRepository.getAllByIdExists(pageRequest);
    }
}
