package com.spring.boot.ecommerce.services.order;

import com.spring.boot.ecommerce.auth.AuthUser;
import com.spring.boot.ecommerce.common.enums.Status;
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
        List<Product> products = productRepository.findAll();
        List<OrderDetailResponse> orderDetailResponses = new ArrayList<>();
        List<OrderProduct> orderProducts = new ArrayList<>();

        for (int i = 0; i < orderRequest.getOrderDetail().size(); i++) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setId(UniqueID.getUUID());
            orderProduct.setOrderId(orderInfo.getId());
            orderProduct.setProductId(orderRequest.getOrderDetail().get(i).getProductId());
            orderProduct.setQuantity(orderRequest.getOrderDetail().get(i).getQuantity());

            double totalSub = 0;
            for (int j = 0; j < products.size(); j++) {
                if (products.get(j).getId().equals(orderRequest.getOrderDetail().get(i).getProductId())) {
                    totalSub = calcTotal(products.get(j).getPrice(), products.get(j).getDiscount(),
                            orderRequest.getOrderDetail().get(i).getQuantity());
                }
            }

            total += totalSub;
            orderProducts.add(orderProduct);
        }

        orderInfo.setTotal(total);
        orderInfo.setStatus(Status.PENDING);

        orderRepository.save(orderInfo);
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

        double total = 0;

        orderDetailRepository.deleteAllByOrderId(orderInfo.getId());

        orderInfo.setCustomerId(authUser.getId());
        orderInfo.setPhoneNumber(orderRequest.getPhoneNumber());
        orderInfo.setAddressReceive(orderRequest.getAddressReceive());

        //        Order Detail
        List<Product> products = productRepository.findAll();
        List<OrderDetailResponse> orderDetailResponses = new ArrayList<>();
        List<OrderProduct> orderProducts = new ArrayList<>();

        for (int i = 0; i < orderRequest.getOrderDetail().size(); i++) {
            if (orderRequest.getOrderDetail().get(i).getStatus().equals(Status.IN_ACTIVE) &&
                    (!orderRequest.getOrderDetail().get(i).getId().isEmpty() ||
                            orderRequest.getOrderDetail().get(i).getId() != null)) {
                OrderProduct orderProduct = orderDetailRepository.getById(orderRequest.getOrderDetail().get(i).getProductId());
                orderDetailRepository.delete(orderProduct);
                break;
            }

            double totalSub = 0;
            if ((orderRequest.getOrderDetail().get(i).getId().isEmpty() ||
                    orderRequest.getOrderDetail().get(i).getId() == null) &&
                    orderRequest.getOrderDetail().get(i).getStatus().equals(Status.IN_ACTIVE))  {
                break;
            }

            if ((orderRequest.getOrderDetail().get(i).getId().isEmpty() ||
                    orderRequest.getOrderDetail().get(i).getId() == null)) {
                OrderProduct orderProduct = new OrderProduct();
                orderProduct.setId(UniqueID.getUUID());
                orderProduct.setOrderId(orderInfo.getId());
                orderProduct.setProductId(orderRequest.getOrderDetail().get(i).getProductId());
                orderProduct.setQuantity(orderRequest.getOrderDetail().get(i).getQuantity());

                for (int j = 0; j < products.size(); j++) {
                    if (products.get(j).equals(orderRequest.getOrderDetail().get(i))) {
                        totalSub = calcTotal(products.get(j).getPrice(), products.get(j).getDiscount(),
                                orderRequest.getOrderDetail().get(i).getQuantity());
                    }
                }

                total += totalSub;
                orderProducts.add(orderProduct);
                break;
            }

            OrderProduct orderProduct = orderDetailRepository.getById(orderRequest.getOrderDetail().get(i).getId());
            orderProduct.setQuantity(orderRequest.getOrderDetail().get(i).getQuantity());

            for (int j = 0; j < products.size(); j++) {
                if (products.get(j).equals(orderRequest.getOrderDetail().get(i))) {
                    totalSub = calcTotal(products.get(j).getPrice(), products.get(j).getDiscount(),
                            orderRequest.getOrderDetail().get(i).getQuantity());
                }
            }

            total += totalSub;
            orderProducts.add(orderProduct);
        }
        orderInfo.setTotal(total);

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

    private double calcTotal(double price, double discount, int quantity) {
        return (price - discount) * quantity;
    }
}
