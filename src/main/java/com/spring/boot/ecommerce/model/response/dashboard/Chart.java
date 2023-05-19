package com.spring.boot.ecommerce.model.response.dashboard;

import lombok.Data;
@Data
public class Chart {
    private String yearAndMonth;
    private long totalOrder;

    public Chart(String yearAndMonth, long totalOrder) {
        this.yearAndMonth = yearAndMonth;
        this.totalOrder = totalOrder;
    }
}
