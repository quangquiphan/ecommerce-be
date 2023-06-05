package com.spring.boot.ecommerce.services.notification;

import com.spring.boot.ecommerce.auth.AuthUser;
import com.spring.boot.ecommerce.common.enums.Filter;
import com.spring.boot.ecommerce.common.exceptions.ApplicationException;
import com.spring.boot.ecommerce.common.utils.RestAPIStatus;
import com.spring.boot.ecommerce.common.utils.UniqueID;
import com.spring.boot.ecommerce.entity.Notification;
import com.spring.boot.ecommerce.model.response.notification.NotificationResponse;
import com.spring.boot.ecommerce.repositories.NotificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImplement implements NotificationService{
    final NotificationRepository notificationRepository;

    public NotificationServiceImplement(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void createNotification(AuthUser authUser, String message) {
        Notification notification = new Notification();
        notification.setId(UniqueID.getUUID());
        notification.setUserId(authUser.getId());
        notification.setMessage(message);
        notification.setRead(false);

        notificationRepository.save(notification);
    }

    @Override
    public String markAsRead(String id) {
        Notification notification = notificationRepository.getById(id);

        if (notification == null) {
            throw new ApplicationException(RestAPIStatus.NOT_FOUND);
        }

        notification.setRead(true);
        notificationRepository.save(notification);
        return "Successfully!";
    }

    @Override
    public String markAllAsRead() {
        List<Notification> notifications = notificationRepository.findAllByRead(false);

        if (notifications == null) return "";

        for (int i = 0; i < notifications.size(); i++) {
            notifications.get(i).setRead(true);
        }

        notificationRepository.saveAll(notifications);
        return "Successfully!";
    }

    @Override
    public Page<Notification> getPage(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return notificationRepository.findAll(pageRequest);
    }

    @Override
    public List<NotificationResponse> getAllNotification(Filter filter) {
        if (filter.equals(Filter.ALL)) {
            return notificationRepository.getAllByRead();
        }

        boolean isRead;

        if (filter.equals(Filter.TRUE))
            isRead = true;
        else isRead = false;

        return notificationRepository.getAllByRead(isRead);
    }
}
