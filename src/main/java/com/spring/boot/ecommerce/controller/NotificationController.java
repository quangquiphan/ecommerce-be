package com.spring.boot.ecommerce.controller;

import com.spring.boot.ecommerce.auth.AuthorizeValidator;
import com.spring.boot.ecommerce.common.AbstractBaseController;
import com.spring.boot.ecommerce.common.enums.Filter;
import com.spring.boot.ecommerce.common.enums.UserRole;
import com.spring.boot.ecommerce.common.utils.RestAPIResponse;
import com.spring.boot.ecommerce.model.response.PagingResponse;
import com.spring.boot.ecommerce.services.notification.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPath.NOTIFICATION_APIs)
public class NotificationController extends AbstractBaseController {

    final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @AuthorizeValidator(UserRole.ADMIN)
    @RequestMapping(path = ApiPath.ALL, method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getNotification(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam Filter filter
            ) {

        return responseUtil.successResponse(
                new PagingResponse(notificationService.getAllNotification(filter),
                        notificationService.getPage(pageNumber, pageSize))
        );
    }

    @AuthorizeValidator(UserRole.ADMIN)
    @RequestMapping(path = ApiPath.ID, method = RequestMethod.PUT)
    public ResponseEntity<RestAPIResponse> markAsRead(
            @PathVariable String id
    ) {
        return responseUtil.successResponse(notificationService.markAsRead(id));
    }

    @AuthorizeValidator(UserRole.ADMIN)
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<RestAPIResponse> markAllAsRead() {
        return responseUtil.successResponse(notificationService.markAllAsRead());
    }
}
