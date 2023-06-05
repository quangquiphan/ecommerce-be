package com.spring.boot.ecommerce.controller;

import com.spring.boot.ecommerce.auth.AuthUser;
import com.spring.boot.ecommerce.auth.AuthorizeValidator;
import com.spring.boot.ecommerce.common.AbstractBaseController;
import com.spring.boot.ecommerce.common.enums.OrderStatus;
import com.spring.boot.ecommerce.common.enums.UserRole;
import com.spring.boot.ecommerce.common.exceptions.ApplicationException;
import com.spring.boot.ecommerce.common.utils.Constant;
import com.spring.boot.ecommerce.common.utils.RestAPIResponse;
import com.spring.boot.ecommerce.common.utils.RestAPIStatus;
import com.spring.boot.ecommerce.model.request.order.OrderRequest;
import com.spring.boot.ecommerce.model.response.PagingResponse;
import com.spring.boot.ecommerce.services.notification.NotificationService;
import com.spring.boot.ecommerce.services.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(ApiPath.ORDER_APIs)
public class OrderController extends AbstractBaseController {

    final private OrderService orderService;
    final private NotificationService notificationService;

    public OrderController(OrderService orderService, NotificationService notificationService) {
        this.orderService = orderService;
        this.notificationService = notificationService;
    }

    @Operation(summary = "createOrder")
    @RequestMapping(method = RequestMethod.POST)
    @AuthorizeValidator({UserRole.CUSTOMER, UserRole.ADMIN})
    public ResponseEntity<RestAPIResponse> createOrder(
            @RequestBody OrderRequest orderRequest,
            HttpServletRequest request
    ) {
        AuthUser authUser = jwtTokenUtil.getUserIdFromJWT(request.getHeader(Constant.HEADER_TOKEN));

        if (authUser == null) {
            throw new ApplicationException(RestAPIStatus.NOT_FOUND);
        }

        notificationService.createNotification(authUser, OrderStatus.ORDERED.toString());

        return responseUtil.successResponse(orderService.createOrder(orderRequest, authUser));
    }

    @Operation(summary = "changeStatus")
    @AuthorizeValidator(UserRole.ADMIN)
    @RequestMapping(path = ApiPath.ID, method = RequestMethod.PUT)
    public ResponseEntity<RestAPIResponse> changeStatus(
            @PathVariable String id,
            @RequestParam OrderStatus status
    ) {
        return responseUtil.successResponse(orderService.changeStatus(id, status));
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
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(name = "status") OrderStatus status
    ) {
        return responseUtil.successResponse(new PagingResponse(orderService.getAllOrder(pageNumber, pageSize, status)));
    }
}
