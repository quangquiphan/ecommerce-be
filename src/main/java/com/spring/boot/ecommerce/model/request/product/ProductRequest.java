package com.spring.boot.ecommerce.model.request.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.spring.boot.ecommerce.common.enums.Status;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductRequest {
    private String productName;
    private String description;
    private Double price;
    private Double discount;
    private int quantity;
    private Status status;
    private List<String> categoryIds;
    private List<String> brandIds;
}
