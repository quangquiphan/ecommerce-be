package com.spring.boot.ecommerce.model.response.category;

import com.spring.boot.ecommerce.common.enums.Status;
import com.spring.boot.ecommerce.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private String id;
    private String categoryName;
    private Status status;

    public CategoryResponse(Category category) {
        this.id = category.getId();
        this.categoryName = category.getCategoryName();
        this.status = category.getStatus();
    }
}
