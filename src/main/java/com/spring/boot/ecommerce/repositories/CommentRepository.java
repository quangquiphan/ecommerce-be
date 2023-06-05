package com.spring.boot.ecommerce.repositories;

import com.spring.boot.ecommerce.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, String> {
    Comment getById(String id);

    @Query(value = "SELECT c FROM Comment c WHERE c.productId = :productId")
    Page<Comment> getAllByProductId(@Param("productId") String productId, Pageable pageable);
}
