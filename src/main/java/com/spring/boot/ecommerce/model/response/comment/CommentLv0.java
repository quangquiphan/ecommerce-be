package com.spring.boot.ecommerce.model.response.comment;

import com.spring.boot.ecommerce.common.enums.Status;
import com.spring.boot.ecommerce.entity.Comment;
import com.spring.boot.ecommerce.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentLv0 {
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
    private List<CommentLv1> list;

    public CommentLv0(Comment comment, User user) {
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

    public CommentLv0(Comment comment, User user, List<CommentLv1> list) {
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
        this.list = list;
    }
}
