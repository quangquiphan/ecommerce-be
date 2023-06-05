package com.spring.boot.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.boot.ecommerce.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notification")
public class Notification extends BaseEntity {
    @Id
    @Column(name = "id", nullable = false, length = 64)
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "message")
    private String message;

    @Column(name = "is_read")
    private boolean isRead;
}
