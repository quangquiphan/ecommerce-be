package com.spring.boot.ecommerce.controller;

import com.spring.boot.ecommerce.auth.AuthUser;
import com.spring.boot.ecommerce.auth.AuthorizeValidator;
import com.spring.boot.ecommerce.common.AbstractBaseController;
import com.spring.boot.ecommerce.common.enums.UserRole;
import com.spring.boot.ecommerce.common.utils.Constant;
import com.spring.boot.ecommerce.common.utils.RestAPIResponse;
import com.spring.boot.ecommerce.model.request.cart.CartRequest;
import com.spring.boot.ecommerce.services.cart.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(ApiPath.CART_APIs)
public class CartController extends AbstractBaseController {

    final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @AuthorizeValidator({UserRole.ADMIN, UserRole.CUSTOMER})
    public ResponseEntity<RestAPIResponse> createCart(
            @RequestBody CartRequest cart,
            HttpServletRequest request
            ) {
        AuthUser auth = jwtTokenUtil.getUserIdFromJWT(request.getHeader(Constant.HEADER_TOKEN));
        return responseUtil.successResponse(cartService.addToCart(cart, auth));
    }

    @RequestMapping(method = RequestMethod.PUT)
    @AuthorizeValidator({UserRole.ADMIN, UserRole.CUSTOMER})
    public ResponseEntity<RestAPIResponse> updateCart(
            @PathVariable String id,
            @RequestBody CartRequest cart,
            HttpServletRequest request
    ) {
        AuthUser auth = jwtTokenUtil.getUserIdFromJWT(request.getHeader(Constant.HEADER_TOKEN));
        return responseUtil.successResponse(cartService.updateCart(id, cart, auth));
    }

    @RequestMapping(path = ApiPath.ID, method = RequestMethod.DELETE)
    @AuthorizeValidator({UserRole.ADMIN, UserRole.CUSTOMER})
    public ResponseEntity<RestAPIResponse> deleteItem(
            @PathVariable String id
    ) {
        cartService.deleteItem(id);
        return responseUtil.successResponse("Delete successfully!");
    }

    @RequestMapping(path = ApiPath.ALL + ApiPath.ID, method = RequestMethod.DELETE)
    @AuthorizeValidator({UserRole.ADMIN, UserRole.CUSTOMER})
    public ResponseEntity<RestAPIResponse> deleteAll(
            @PathVariable String id
    ) {
        cartService.deleteAll(id);
        return responseUtil.successResponse("Delete successfully!");
    }
}
