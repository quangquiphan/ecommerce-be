package com.spring.boot.ecommerce.services.cart;

import com.spring.boot.ecommerce.common.exceptions.ApplicationException;
import com.spring.boot.ecommerce.common.utils.RestAPIStatus;
import com.spring.boot.ecommerce.common.utils.UniqueID;
import com.spring.boot.ecommerce.entity.Cart;
import com.spring.boot.ecommerce.entity.Product;
import com.spring.boot.ecommerce.entity.User;
import com.spring.boot.ecommerce.model.request.cart.CartRequest;
import com.spring.boot.ecommerce.repositories.CartRepository;
import com.spring.boot.ecommerce.repositories.ProductRepository;
import com.spring.boot.ecommerce.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImplement implements CartService{
    final UserRepository userRepository;
    final ProductRepository productRepository;
    final CartRepository cartRepository;

    public CartServiceImplement(
            UserRepository userRepository,
            ProductRepository productRepository,
            CartRepository cartRepository
    ) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart addToCart(CartRequest cartRequest) {
        User user = userRepository.getById(cartRequest.getUserId());
        Product product = productRepository.getById(cartRequest.getProductId());
        Cart item = cartRepository.getCartByProductId(cartRequest.getProductId());

        if (user == null) {
            throw new ApplicationException(RestAPIStatus.NOT_FOUND);
        }

        if (product == null) {
            throw new ApplicationException(RestAPIStatus.NOT_FOUND);
        }

        if (item != null) {
            item.setQuantity(item.getQuantity() + cartRequest.getQuantity());
            return cartRepository.save(item);
        }

        Cart cart = new Cart();
        cart.setId(UniqueID.getUUID());
        cart.setUserId(user.getId());
        cart.setProductId(product.getId());
        cart.setQuantity(cartRequest.getQuantity());

        return cartRepository.save(cart);
    }

    @Override
    public Cart updateCart(String id, CartRequest cartRequest) {
        Cart cart = cartRepository.getCartById(id);

        if (cart == null) {
            throw new ApplicationException(RestAPIStatus.NOT_FOUND);
        }

        cart.setQuantity(cartRequest.getQuantity());
        cart.setProductId(cartRequest.getProductId());

        return cartRepository.save(cart);
    }

    @Override
    public void deleteItem(String id) {
        User user = userRepository.getById(id);

        if (user == null) {
            return;
        }

        cartRepository.deleteById(id);
    }

    @Override
    public void deleteAll(String userId) {
        User user = userRepository.getById(userId);

        if (user == null) {
            return;
        }

        cartRepository.deleteAll(cartRepository.getAllByUserId(userId));
    }
}
