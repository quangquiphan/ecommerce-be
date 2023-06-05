package com.spring.boot.ecommerce.model.response.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageResponse {
    private String id;
    private String url;
    private String imageName;

    public ProductImageResponse(String url, ProductImageResponse productImage) {
        this.id = productImage.getId();
        this.url = url;
        this.imageName = productImage.getImageName();
    }
}
