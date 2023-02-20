package com.spring.boot.ecommerce.repositories;

import com.spring.boot.ecommerce.entity.Brand;
import com.spring.boot.ecommerce.entity.ProductBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductBrandRepository extends JpaRepository<ProductBrand, String> {

    List<ProductBrand> findAllByProductId(String productId);

    @Query(value = "SELECT b FROM ProductBrand pc, Brand b " +
            "WHERE pc.brandId = b.id AND pc.productId = :productId ")
    List<Brand> getAllByProductId(@Param("productId") String productId);
}
