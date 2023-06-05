package com.spring.boot.ecommerce.model.response.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.spring.boot.ecommerce.common.enums.Status;
import com.spring.boot.ecommerce.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListCategoryResponse {
    private String id;
    private String categoryName;
    private Status status;
    private long quantity;

    public ListCategoryResponse(Category category, long quantity){
        this.id = category.getId();
        this.categoryName = category.getCategoryName();
        this.status = category.getStatus();
        this.quantity = quantity;
    }
}
