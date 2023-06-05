package com.spring.boot.ecommerce.repositories;

import com.spring.boot.ecommerce.entity.Category;
import com.spring.boot.ecommerce.model.response.category.ListCategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    Category getById(String id);

    @Query(value =
        " SELECT new com.spring.boot.ecommerce.model.response.category.ListCategoryResponse(c, count (pc.productId))" +
        " FROM Category c LEFT JOIN ProductCategory pc ON c.id = pc.categoryId " +
        " GROUP BY c.id, c.categoryName, c.status, c.createdDate, c.updatedDate"
    )
    Page<ListCategoryResponse> getAllByIdExists(Pageable pageable);
}
