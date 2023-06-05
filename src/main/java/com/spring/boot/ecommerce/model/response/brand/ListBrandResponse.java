package com.spring.boot.ecommerce.model.response.brand;

import com.spring.boot.ecommerce.common.enums.Status;
import com.spring.boot.ecommerce.entity.Brand;
import com.spring.boot.ecommerce.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListBrandResponse {
    private String id;
    private String brandName;
    private Status status;
    private long quantity;

    public ListBrandResponse(Brand brand, long quantity){
        this.id = brand.getId();
        this.brandName = brand.getBrandName();
        this.status = brand.getStatus();
        this.quantity = quantity;
    }
}
