package com.spring.boot.ecommerce.services.notification;

import com.spring.boot.ecommerce.auth.AuthUser;
import com.spring.boot.ecommerce.common.enums.Filter;
import com.spring.boot.ecommerce.entity.Notification;
import com.spring.boot.ecommerce.model.response.notification.NotificationResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NotificationService {
    void createNotification(AuthUser authUser, String message);

    String markAsRead(String id);

    String markAllAsRead();

    List<NotificationResponse> getAllNotification(Filter filter);

    Page<Notification> getPage(int pageNumber, int pageSize);
}
