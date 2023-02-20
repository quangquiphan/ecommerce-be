package com.spring.boot.ecommerce.controller;

import com.spring.boot.ecommerce.auth.AuthUser;
import com.spring.boot.ecommerce.auth.AuthorizeValidator;
import com.spring.boot.ecommerce.common.AbstractBaseController;
import com.spring.boot.ecommerce.common.enums.UserRole;
import com.spring.boot.ecommerce.common.utils.Constant;
import com.spring.boot.ecommerce.common.utils.RestAPIResponse;
import com.spring.boot.ecommerce.model.request.order.OrderRequest;
import com.spring.boot.ecommerce.model.response.PagingResponse;
import com.spring.boot.ecommerce.services.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(ApiPath.ORDER_APIs)
public class OrderController extends AbstractBaseController {

    final private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "createOrder")
    @RequestMapping(method = RequestMethod.POST)
    @AuthorizeValidator({UserRole.CUSTOMER, UserRole.ADMIN})
    public ResponseEntity<RestAPIResponse> createOrder(
            @RequestBody OrderRequest orderRequest,
            HttpServletRequest request
    ) {
        AuthUser authUser = jwtTokenUtil.getUserIdFromJWT(request.getHeader(Constant.HEADER_TOKEN));
        return responseUtil.successResponse(orderService.createOrder(orderRequest, authUser));
    }

    @Operation(summary = "updateOrder")
    @AuthorizeValidator({UserRole.CUSTOMER, UserRole.ADMIN})
    @RequestMapping(path = ApiPath.ID, method = RequestMethod.PUT)
    public ResponseEntity<RestAPIResponse> updateOrder(
            @PathVariable String id,
            @RequestBody OrderRequest orderRequest,
            HttpServletRequest request
    ) {
        AuthUser authUser = jwtTokenUtil.getUserIdFromJWT(request.getHeader(Constant.HEADER_TOKEN));
        return responseUtil.successResponse(orderService.updateOrder(id, orderRequest, authUser));
    }

    @Operation(summary = "getOrder")
    @AuthorizeValidator({UserRole.CUSTOMER, UserRole.ADMIN})
    @RequestMapping(path = ApiPath.ID, method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getOrder(
            @PathVariable String id,
            HttpServletRequest request
    ) {
        return responseUtil.successResponse(orderService.getOrder(id));
    }

    @Operation(summary = "deleteOrder")
    @AuthorizeValidator({UserRole.CUSTOMER, UserRole.ADMIN})
    @RequestMapping(path = ApiPath.ID, method = RequestMethod.DELETE)
    public ResponseEntity<RestAPIResponse> deleteOrder(
            @PathVariable String id
    ) {
        orderService.delete(id);
        return responseUtil.successResponse("Delete successfully!");
    }

    @Operation(summary = "getAllOrder")
    @AuthorizeValidator(UserRole.ADMIN)
    @RequestMapping(path = ApiPath.ALL, method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getAllOrder(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return responseUtil.successResponse(new PagingResponse(orderService.getAllOrder(pageNumber, pageSize)));
    }
}
