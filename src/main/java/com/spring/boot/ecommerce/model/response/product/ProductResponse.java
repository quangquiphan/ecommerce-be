package com.spring.boot.ecommerce.model.response.product;

import com.spring.boot.ecommerce.common.enums.Status;
import com.spring.boot.ecommerce.entity.Brand;
import com.spring.boot.ecommerce.entity.Category;
import com.spring.boot.ecommerce.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private String id;
    private String productName;
    private String description;
    private Double price;
    private Double discount;
    private int quantity;
    private Status status;
    private String userId;

    private List<String> categoryIds;

    private List<String> brandIds;

    public ProductResponse(Product product, List<String> categoryIds, List<String> brandIds) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.discount = product.getDiscount();
        this.quantity = product.getQuantity();
        this.status = product.getStatus();
        this.userId = product.getUserId();
        this.categoryIds = categoryIds;
        this.brandIds = brandIds;
    }
}
