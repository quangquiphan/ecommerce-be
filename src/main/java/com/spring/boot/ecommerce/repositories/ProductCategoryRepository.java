package com.spring.boot.ecommerce.repositories;

import com.spring.boot.ecommerce.entity.Category;
import com.spring.boot.ecommerce.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, String> {

    List<ProductCategory> findAllByProductId(String productId);

    @Query(value = "SELECT c FROM ProductCategory pc, Category c " +
                   "WHERE pc.categoryId = c.id AND pc.productId = :productId ")
    List<Category> getAllByProductId(@Param("productId") String productId);
}
