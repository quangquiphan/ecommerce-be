package com.spring.boot.ecommerce.model.response.comment;

import com.spring.boot.ecommerce.common.enums.Status;
import com.spring.boot.ecommerce.common.enums.UserRole;
import com.spring.boot.ecommerce.entity.Comment;
import com.spring.boot.ecommerce.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentLv1 {
    private String id;
    private String productId;
    private String userId;
    private String userReplyId;
    private String content;
    private String commentId;
    private Status status;
    private Date createdDate;
    private Date updatedDate;
    private String firstName;
    private String lastName;
    private String replyFirstName;
    private String replyLastName;
    private UserRole role;

    public CommentLv1(Comment comment, User user) {
        this.id = comment.getId();
        this.productId = comment.getProductId();
        this.userId = comment.getUserId();
        this.userReplyId = comment.getUserReplyId();
        this.content = comment.getContent();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.commentId = comment.getCommentId();
        this.status = comment.getStatus();
        this.createdDate = comment.getCreatedDate();
        this.updatedDate = comment.getUpdatedDate();
    }

    public CommentLv1(Comment comment, User user, User userReply) {
        this.id = comment.getId();
        this.productId = comment.getProductId();
        this.userId = comment.getUserId();
        this.userReplyId = comment.getUserReplyId();
        this.content = comment.getContent();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.replyFirstName = userReply.getFirstName();
        this.replyLastName = userReply.getLastName();
        this.role = userReply.getUserRole();
        this.commentId = comment.getCommentId();
        this.status = comment.getStatus();
        this.createdDate = comment.getCreatedDate();
        this.updatedDate = comment.getUpdatedDate();
    }
}
