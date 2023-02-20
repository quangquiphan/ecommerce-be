package com.spring.boot.ecommerce.services.category;

import com.spring.boot.ecommerce.auth.AuthUser;
import com.spring.boot.ecommerce.entity.Category;
import com.spring.boot.ecommerce.model.request.category.CategoryRequest;
import com.spring.boot.ecommerce.model.response.category.ListCategoryResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    Category addCategory(CategoryRequest categoryRequest, AuthUser authUser);

    Category updateCategory(String id, CategoryRequest categoryRequest, AuthUser authUser);

    Category getCategory(String id);

    Page<ListCategoryResponse> getAllCategory(int pageNumber, int pageSize);

    void delete(String id, AuthUser authUser);
}
