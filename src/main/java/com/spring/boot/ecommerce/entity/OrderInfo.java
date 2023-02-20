package com.spring.boot.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.boot.ecommerce.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
@Table(name = "order_info")
public class OrderInfo extends BaseEntity implements Serializable {
    @Id
    @Column(name = "id", length = 32, nullable = false)
    private String id;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "address_receive")
    private String addressReceive;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "total")
    private double total;
}
