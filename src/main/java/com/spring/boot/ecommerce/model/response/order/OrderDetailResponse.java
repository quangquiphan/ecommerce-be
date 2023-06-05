package com.spring.boot.ecommerce.model.response.order;

import com.spring.boot.ecommerce.entity.OrderProduct;
import com.spring.boot.ecommerce.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponse {
    private String id;
    private String productName;
    private String description;
    private Double price;
    private Double discount;
    private int quantity;

    public OrderDetailResponse(Product product, OrderProduct orderProduct) {
        this.id = orderProduct.getId();
        this.productName = product.getProductName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.discount = product.getDiscount();
        this.quantity = orderProduct.getQuantity();
    }
}
