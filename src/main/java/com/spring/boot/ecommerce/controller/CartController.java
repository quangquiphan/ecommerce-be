package com.spring.boot.ecommerce.controller;

import com.spring.boot.ecommerce.common.AbstractBaseController;
import com.spring.boot.ecommerce.common.utils.RestAPIResponse;
import com.spring.boot.ecommerce.model.request.cart.CartRequest;
import com.spring.boot.ecommerce.services.cart.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPath.CART_APIs)
public class CartController extends AbstractBaseController {

    final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<RestAPIResponse> createCart(
            @RequestBody CartRequest cart
            ) {
        return responseUtil.successResponse(cartService.addToCart(cart));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<RestAPIResponse> updateCart(
            @PathVariable String id,
            @RequestBody CartRequest cart
    ) {
        return responseUtil.successResponse(cartService.updateCart(id, cart));
    }

    @RequestMapping(path = ApiPath.ID, method = RequestMethod.DELETE)
    public ResponseEntity<RestAPIResponse> deleteItem(
            @PathVariable String id
    ) {
        cartService.deleteItem(id);
        return responseUtil.successResponse("Delete successfully!");
    }

    @RequestMapping(path = ApiPath.ALL + ApiPath.ID, method = RequestMethod.DELETE)
    public ResponseEntity<RestAPIResponse> deleteAll(
            @PathVariable String id
    ) {
        cartService.deleteAll(id);
        return responseUtil.successResponse("Delete successfully!");
    }
}
