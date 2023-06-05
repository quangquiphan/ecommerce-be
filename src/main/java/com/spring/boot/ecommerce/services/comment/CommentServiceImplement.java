package com.spring.boot.ecommerce.services.comment;

import com.spring.boot.ecommerce.auth.AuthUser;
import com.spring.boot.ecommerce.common.exceptions.ApplicationException;
import com.spring.boot.ecommerce.common.utils.RestAPIStatus;
import com.spring.boot.ecommerce.common.utils.UniqueID;
import com.spring.boot.ecommerce.entity.Comment;
import com.spring.boot.ecommerce.model.request.comment.AddComment;
import com.spring.boot.ecommerce.repositories.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImplement implements CommentService {

    final private CommentRepository commentRepository;

    public CommentServiceImplement(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment addComment(AddComment addComment, AuthUser authUser) {
        Comment comment = new Comment();
        comment.setId(UniqueID.getUUID());
        comment.setProductId(addComment.getProductId());
        comment.setContent(addComment.getContent());
        comment.setUserId(authUser.getId());
        comment.setUserReplyId(addComment.getUserReplyId());
        comment.setCommentId(addComment.getCommentId());
        comment.setStatus(addComment.getStatus());

        return commentRepository.save(comment);
    }

    @Override
    public Page<Comment> getAllComment(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return commentRepository.findAll(pageRequest);
    }

    @Override
    public Page<Comment> getAllCommentByProductId(String productId, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return commentRepository.getAllByProductId(productId, pageRequest);
    }

    @Override
    public Comment approveComment(String id) {
        return null;
    }

    @Override
    public String removeComment(String id) {
        Comment comment = commentRepository.getById(id);

        if (comment == null) {
            throw new ApplicationException(RestAPIStatus.NOT_FOUND);
        }

        commentRepository.delete(comment);
        return "Delete successfully!";
    }
}
