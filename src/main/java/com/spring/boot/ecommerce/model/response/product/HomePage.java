package com.spring.boot.ecommerce.model.response.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomePage {
    private List<GetProductResponse> newProduct;
    private List<GetProductResponse> discountProduct;
}
