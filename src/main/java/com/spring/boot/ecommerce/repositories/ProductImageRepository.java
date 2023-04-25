package com.spring.boot.ecommerce.repositories;

import com.spring.boot.ecommerce.entity.ProductImage;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, String> {
    ProductImage getById(String id);

    List<ProductImage> getAllByProductId(String id);
}
