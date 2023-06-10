package com.spring.boot.ecommerce.model.response.comment;

import com.spring.boot.ecommerce.model.response.PagingResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private int totalComment;
    private PagingResponse pagingResponse;
}
