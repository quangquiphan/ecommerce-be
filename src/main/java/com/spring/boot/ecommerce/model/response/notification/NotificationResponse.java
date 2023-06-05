package com.spring.boot.ecommerce.model.response.notification;

import com.spring.boot.ecommerce.entity.Notification;
import com.spring.boot.ecommerce.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {
    private String id;
    private String userId;
    private String firstName;
    private String lastName;
    private String message;
    private boolean isRead;
    private Date createdDate;
    private Date updatedDate;

    public NotificationResponse(User user, Notification notification) {
        this.id = notification.getId();
        this.userId = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.isRead = notification.isRead();
        this.createdDate = notification.getCreatedDate();
        this.updatedDate = notification.getUpdatedDate();
    }
}
