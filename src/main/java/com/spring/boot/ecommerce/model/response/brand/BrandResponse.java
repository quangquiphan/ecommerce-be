package com.spring.boot.ecommerce.model.response.brand;

import com.spring.boot.ecommerce.common.enums.Status;
import com.spring.boot.ecommerce.entity.Brand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandResponse {
    private String id;
    private String brandName;
    private Status status;
    private String userId;

    public BrandResponse(Brand brand) {
        this.id = brand.getId();
        this.brandName = brand.getBrandName();
        this.status = brand.getStatus();
        this.userId = brand.getUserId();
    }
}
