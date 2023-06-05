package com.spring.boot.ecommerce.services.category;

import com.spring.boot.ecommerce.entity.Category;
import com.spring.boot.ecommerce.model.request.category.CategoryRequest;
import com.spring.boot.ecommerce.model.response.category.ListCategoryResponse;
import org.springframework.data.domain.Page;

public interface CategoryService {
    Category addCategory(CategoryRequest categoryRequest);

    Category updateCategory(String id, CategoryRequest categoryRequest);

    Category getCategory(String id);

    Page<ListCategoryResponse> getAllCategory(int pageNumber, int pageSize);

    void delete(String id);
}
