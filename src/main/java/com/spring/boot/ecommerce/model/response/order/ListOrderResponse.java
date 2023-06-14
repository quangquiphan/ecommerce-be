package com.spring.boot.ecommerce.model.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.boot.ecommerce.common.enums.OrderStatus;
import com.spring.boot.ecommerce.common.enums.Status;
import com.spring.boot.ecommerce.common.enums.UserRole;
import com.spring.boot.ecommerce.entity.OrderInfo;
import com.spring.boot.ecommerce.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListOrderResponse {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String country;
    private String customerId;
    private String addressReceive;
    private String phoneReceive;
    private double total;
    private OrderStatus status;
    private Date createdDate;
    private Date updatedDate;

    public ListOrderResponse(User user, OrderInfo orderInfo){
        this.id = orderInfo.getId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.customerId = orderInfo.getCustomerId();
        this.addressReceive = orderInfo.getAddressReceive();
        this.phoneReceive = orderInfo.getPhoneNumber();
        this.total = orderInfo.getTotal();
        this.status = orderInfo.getStatus();
        this.createdDate = orderInfo.getCreatedDate();
        this.updatedDate = orderInfo.getUpdatedDate();
    }
}
