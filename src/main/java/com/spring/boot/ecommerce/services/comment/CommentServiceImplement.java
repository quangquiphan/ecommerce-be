package com.spring.boot.ecommerce.services.comment;

import com.spring.boot.ecommerce.auth.AuthUser;
import com.spring.boot.ecommerce.common.exceptions.ApplicationException;
import com.spring.boot.ecommerce.common.utils.RestAPIStatus;
import com.spring.boot.ecommerce.common.utils.UniqueID;
import com.spring.boot.ecommerce.entity.Comment;
import com.spring.boot.ecommerce.entity.User;
import com.spring.boot.ecommerce.model.request.comment.AddComment;
import com.spring.boot.ecommerce.model.response.PagingResponse;
import com.spring.boot.ecommerce.model.response.comment.CommentLv1;
import com.spring.boot.ecommerce.model.response.comment.CommentLv0;
import com.spring.boot.ecommerce.model.response.comment.CommentResponse;
import com.spring.boot.ecommerce.repositories.CommentRepository;
import com.spring.boot.ecommerce.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImplement implements CommentService {

    final private CommentRepository commentRepository;
    final private UserRepository userRepository;

    public CommentServiceImplement(CommentRepository commentRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
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
    public Page<CommentResponse> getAllComment(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        commentRepository.getAllComment(pageRequest);
        return null;
    }

    @Override
    public Page<Comment> getAllCommentByProductId(String productId, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return commentRepository.getAllByProductId(productId, pageRequest);
    }

    @Override
    public CommentResponse getListByProductId(String productId, int pageNumber, int pageSize) {

        List<CommentLv0> commentLv0s = new ArrayList<>();

        List<Comment> comments = commentRepository.getAllCommentNotCommentId(productId);

        for (int i = 0; i < comments.size(); i++) {
            User user = userRepository.getById(comments.get(i).getUserId());
            List<CommentLv0> getAllByCommentId = commentRepository.getAllByCommentId(comments.get(i).getId());
            List<CommentLv1> lv1s = new ArrayList<>();

            for (int j = 0; j < getAllByCommentId.size(); j++) {

                if (getAllByCommentId.get(j).getUserReplyId().isEmpty() || getAllByCommentId.get(j).getUserReplyId().equals("")) {
                    CommentLv1 cm1 = commentRepository.getByIdNotUserReplyId(getAllByCommentId.get(j).getId());
                    lv1s.add(cm1);
                    System.out.println();
                } else {
                    CommentLv1 cm1 = commentRepository.getByIdAndUserReplyId(getAllByCommentId.get(j).getId(), getAllByCommentId.get(j).getUserReplyId());
                    System.out.println(cm1);
                    lv1s.add(cm1);
                }
            }

            commentLv0s.add(new CommentLv0(comments.get(i), user, lv1s));
        }

        return new CommentResponse(commentRepository.countByProductId(productId),
                new PagingResponse(commentLv0s, getAllCommentByProductId(productId, pageNumber, pageSize)));
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
