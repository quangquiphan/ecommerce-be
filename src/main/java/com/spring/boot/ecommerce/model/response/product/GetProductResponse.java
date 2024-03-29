package com.spring.boot.ecommerce.model.response.product;

import com.spring.boot.ecommerce.common.enums.Status;
import com.spring.boot.ecommerce.entity.Brand;
import com.spring.boot.ecommerce.entity.Category;
import com.spring.boot.ecommerce.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetProductResponse {
    private String id;

    private String productName;

    private List<ProductImageResponse> images;

    private String description;

    private Double price;

    private Double discount;

    private int quantity;

    private Status status;

    private String brandId;

    private String brandName;

    private List<Category> categories;

    private Date createdDate;

    private Date updatedDate;

    public GetProductResponse(Product product, List<Category> categories, List<ProductImageResponse> images, Brand brand) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.images = images;
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.discount = product.getDiscount();
        this.quantity = product.getQuantity();
        this.status = product.getStatus();
        this.categories = categories;
        this.brandId = product.getBrandId();
        this.brandName = brand.getBrandName();
        this.createdDate = product.getCreatedDate();
        this.updatedDate = product.getUpdatedDate();
    }
}
