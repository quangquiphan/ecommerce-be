package com.spring.boot.ecommerce.model.response.order;

import com.spring.boot.ecommerce.auth.AuthUser;
import com.spring.boot.ecommerce.common.enums.OrderStatus;
import com.spring.boot.ecommerce.entity.OrderInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private String id;
    private String customerId;
    private String username;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String country;
    private OrderStatus status;
    private String addressReceive;
    private String phoneReceive;
    private double total;

    private List<OrderDetailResponse> orderDetails;

    public OrderResponse(AuthUser user, OrderInfo orderInfo, List<OrderDetailResponse> orderDetails) {
        this.id = orderInfo.getId();
        this.customerId = orderInfo.getCustomerId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.status = orderInfo.getStatus();
        this.addressReceive = orderInfo.getAddressReceive();
        this.phoneReceive = orderInfo.getPhoneNumber();
        this.total = orderInfo.getTotal();
        this.orderDetails = orderDetails;
    }
}
