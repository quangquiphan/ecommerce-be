package com.spring.boot.ecommerce.repositories;

import com.spring.boot.ecommerce.entity.Comment;
import com.spring.boot.ecommerce.model.response.comment.CommentLv1;
import com.spring.boot.ecommerce.model.response.comment.CommentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, String> {
    Comment getById(String id);

    @Query(value = "SELECT c FROM Comment c WHERE c.productId = :productId AND c.commentId = ''")
    Page<Comment> getAllByProductId(@Param("productId") String productId, Pageable pageable);

    @Query(value = " SELECT new com.spring.boot.ecommerce.model.response.comment.CommentResponse(c, u)"
            + " FROM Comment c, User u"
            + " WHERE c.userId = u.id AND c.commentId = :commentId")
    List<CommentResponse> getAllByCommentId(@Param("commentId") String commentId);

    @Query(value = "SELECT c FROM Comment c WHERE c.commentId = ''")
    List<Comment> getAllCommentNotCommentId();

    @Query(value = " SELECT new com.spring.boot.ecommerce.model.response.comment.CommentResponse(c, u)"
                 + " FROM Comment c, User u"
                 + " WHERE c.userId = u.id")
    Page<CommentResponse> getAllComment(Pageable pageable);

    @Query(value = " SELECT new com.spring.boot.ecommerce.model.response.comment.CommentResponse(c, u)"
            + " FROM Comment c, User u"
            + " WHERE c.userId = u.id")
    List<CommentResponse> getAllByCommentId();

    @Query(value = " SELECT new com.spring.boot.ecommerce.model.response.comment.CommentLv1(c, u, ur)"
            + " FROM Comment c, User u, User ur"
            + " WHERE c.userId = u.id AND c.id = :id AND c.userReplyId = ur.id AND c.userReplyId = :userReplyId")
    CommentLv1 getByIdAndUserReplyId(@Param("id") String id, @Param("userReplyId") String userReplyId);

    @Query(value = " SELECT new com.spring.boot.ecommerce.model.response.comment.CommentLv1(c, u)"
            + " FROM Comment c, User u"
            + " WHERE c.userId = u.id AND c.id = :id")
    CommentLv1 getByIdNotUserReplyId(@Param("id") String id);
}
