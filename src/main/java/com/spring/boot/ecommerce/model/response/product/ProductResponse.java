package com.spring.boot.ecommerce.model.response.product;

import com.spring.boot.ecommerce.common.enums.Status;
import com.spring.boot.ecommerce.entity.Brand;
import com.spring.boot.ecommerce.entity.Category;
import com.spring.boot.ecommerce.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.util.Date;
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

    private String brandId;

    private Date createdDate;

    private Date updatedDate;

    public ProductResponse(Product product, List<String> categoryIds) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.discount = product.getDiscount();
        this.quantity = product.getQuantity();
        this.status = product.getStatus();
        this.userId = product.getUserId();
        this.categoryIds = categoryIds;
        this.brandId = product.getBrandId();
        this.createdDate = product.getCreatedDate();
        this.updatedDate = product.getUpdatedDate();
    }
}
