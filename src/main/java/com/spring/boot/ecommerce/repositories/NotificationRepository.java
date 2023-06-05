package com.spring.boot.ecommerce.repositories;

import com.spring.boot.ecommerce.common.enums.Filter;
import com.spring.boot.ecommerce.entity.Notification;
import com.spring.boot.ecommerce.model.response.notification.NotificationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, String> {
    Notification getById(String id);

    @Query(value = " SELECT noti"
                 + " FROM Notification noti"
                 + " WHERE noti.isRead =:isRead")
    List<Notification> findAllByRead(@Param("isRead") boolean isRead);

    @Query(value = " SELECT new com.spring.boot.ecommerce.model.response.notification.NotificationResponse(u, noti)"
                 + " FROM User u, Notification  noti"
                 + " WHERE u.id = noti.userId AND noti.isRead =:isRead"
                 + " ORDER BY noti.createdDate DESC")
    List<NotificationResponse> getAllByRead(@Param("isRead") boolean isRead);

    @Query(value = " SELECT new com.spring.boot.ecommerce.model.response.notification.NotificationResponse(u, noti)"
                 + " FROM User u, Notification  noti"
                 + " WHERE u.id = noti.userId"
                 + " ORDER BY noti.createdDate DESC")
    List<NotificationResponse> getAllByRead();
}
