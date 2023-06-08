package com.spring.boot.ecommerce.controller;

import com.spring.boot.ecommerce.auth.AuthUser;
import com.spring.boot.ecommerce.auth.AuthorizeValidator;
import com.spring.boot.ecommerce.common.AbstractBaseController;
import com.spring.boot.ecommerce.common.enums.UserRole;
import com.spring.boot.ecommerce.common.utils.Constant;
import com.spring.boot.ecommerce.common.utils.RestAPIResponse;
import com.spring.boot.ecommerce.model.request.comment.AddComment;
import com.spring.boot.ecommerce.model.response.PagingResponse;
import com.spring.boot.ecommerce.services.comment.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(ApiPath.COMMENT_APIs)
public class CommentController extends AbstractBaseController {
    final private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @AuthorizeValidator({UserRole.ADMIN, UserRole.CUSTOMER})
    @Operation(summary = "addComment")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<RestAPIResponse> addComment(
            @RequestBody AddComment addComment,
            HttpServletRequest request
    ) {
        AuthUser authUser = jwtTokenUtil.getUserIdFromJWT(request.getHeader(Constant.HEADER_TOKEN));
        return responseUtil.successResponse(commentService.addComment(addComment, authUser));
    }

    @Operation(summary = "getAllCommentByProductId")
    @RequestMapping(path = ApiPath.ALL + "/ProductId", method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getAllCommentByProductId(
            @RequestParam String productId,
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return responseUtil.successResponse(
                new PagingResponse(
                        commentService.getListByProductId(productId),
                        commentService.getAllCommentByProductId(productId, pageNumber, pageSize))
        );
    }

    @Operation(summary = "getAllComment")
    @RequestMapping(path = ApiPath.ALL, method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getAllComment(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return responseUtil.successResponse(new PagingResponse(commentService.getAllComment(pageNumber, pageSize)));
    }

    @Operation(summary = "removeComment")
    @RequestMapping(path = ApiPath.ID, method = RequestMethod.DELETE)
    public ResponseEntity<RestAPIResponse> removeComment(
            @PathVariable String id
    ) {
        return responseUtil.successResponse(commentService.removeComment(id));
    }
}
