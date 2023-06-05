package com.spring.boot.ecommerce.services.comment;

import com.spring.boot.ecommerce.auth.AuthUser;
import com.spring.boot.ecommerce.entity.Comment;
import com.spring.boot.ecommerce.model.request.comment.AddComment;
import org.springframework.data.domain.Page;

public interface CommentService {
    Comment addComment(AddComment addComment, AuthUser authUser);

    Page<Comment> getAllComment(int pageNumber, int pageSize);

    Page<Comment> getAllCommentByProductId(String productId, int pageNumber, int pageSize);

    Comment approveComment(String id);

    String removeComment(String id);
}
