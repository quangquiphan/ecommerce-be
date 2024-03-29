package com.spring.boot.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.boot.ecommerce.common.BaseEntity;
import com.spring.boot.ecommerce.common.enums.Status;
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
@Table(name = "comment")
public class Comment extends BaseEntity {
    @Id
    @Column(name = "id", length = 64, nullable = false)
    private String id;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_reply_id")
    private String userReplyId;

    @Column(name = "content")
    private String content;

    @Column(name = "comment_id")
    private String commentId;

    @Column(name = "status")
    private Status status;
}
