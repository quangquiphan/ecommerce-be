package com.spring.boot.ecommerce.model.response.cart;

import com.spring.boot.ecommerce.entity.Cart;
import com.spring.boot.ecommerce.entity.Product;
import lombok.Data;

@Data
public class CartResponse {
    private String id;

    private String userId;

    private String productName;

    private double price;

    private double discount;

    private int quantity;

    public CartResponse(Cart cart, Product product) {
        this.id = cart.getId();
        this.userId = cart.getUserId();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.discount = product.getDiscount();
        this.quantity = cart.getQuantity();
    }
}
