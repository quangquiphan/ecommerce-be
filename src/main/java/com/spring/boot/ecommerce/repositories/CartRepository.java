package com.spring.boot.ecommerce.repositories;

import com.spring.boot.ecommerce.entity.Cart;
import com.spring.boot.ecommerce.model.response.cart.CartResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
    @Query(value = "SELECT new com.spring.boot.ecommerce.model.response.cart.CartResponse(c, p ) " +
            "FROM Cart c, Product p, User u " +
            "WHERE c.userId = u.id AND c.productId = p.id")
    List<CartResponse> findCartByUserId(String id);

    Cart getCartById(String id);

    Cart getCartByProductId(String id);

    List<Cart> getAllByUserId(String id);
}
