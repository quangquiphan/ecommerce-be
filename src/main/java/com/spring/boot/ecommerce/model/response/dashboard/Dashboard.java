package com.spring.boot.ecommerce.model.response.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dashboard {
    private int totalUser;
    private int totalOrder;
    private double totalPrice;
}
