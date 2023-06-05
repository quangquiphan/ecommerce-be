package com.spring.boot.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.boot.ecommerce.common.BaseEntity;
import com.spring.boot.ecommerce.common.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
public class Category extends BaseEntity {
    @Id
    @Column(name = "id", length = 64, nullable = false)
    private String id;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "status")
    private Status status;
}
