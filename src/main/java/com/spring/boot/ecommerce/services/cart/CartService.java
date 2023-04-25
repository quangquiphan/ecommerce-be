package com.spring.boot.ecommerce.services.cart;

import com.spring.boot.ecommerce.auth.AuthUser;
import com.spring.boot.ecommerce.entity.Cart;
import com.spring.boot.ecommerce.model.request.cart.CartRequest;

public interface CartService {
    Cart addToCart(CartRequest cartRequest, AuthUser authUser);

    Cart updateCart(String id, CartRequest cartRequest, AuthUser authUser);

    void deleteItem(String id);

    void deleteAll(String userId);
}
