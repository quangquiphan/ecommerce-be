package com.spring.boot.ecommerce.services.cart;

import com.spring.boot.ecommerce.entity.Cart;
import com.spring.boot.ecommerce.model.request.cart.CartRequest;

public interface CartService {
    Cart addToCart(CartRequest cartRequest);

    Cart updateCart(String id, CartRequest cartRequest);

    void deleteItem(String id);

    void deleteAll(String userId);
}
