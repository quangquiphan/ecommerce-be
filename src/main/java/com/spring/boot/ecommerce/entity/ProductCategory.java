package com.spring.boot.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.boot.ecommerce.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "product_category")
public class ProductCategory extends BaseEntity {
    @Id
    @Column(name = "id", length = 64, nullable = false)
    private String id;

    @Column(name = "product_id", length = 64, nullable = false)
    private String productId;

    @Column(name = "category_id", length = 64, nullable = false)
    private String categoryId;
}
