package com.spring.boot.ecommerce.model.response.product;

import com.spring.boot.ecommerce.entity.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageResponse {
    private String id;
    private String url;
    private String userId;

    public ProductImageResponse(String url, ProductImage productImage) {
        this.id = productImage.getId();
        this.url = url;
        this.userId = productImage.getUserId();
    }
}
