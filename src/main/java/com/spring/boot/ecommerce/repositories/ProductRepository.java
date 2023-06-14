package com.spring.boot.ecommerce.repositories;

import com.spring.boot.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Product getById(String id);

    @Query(value = " SELECT p FROM Product p WHERE CURDATE() - DATE_FORMAT(p.createdDate, 'YYYY-MM-DD') <= 10")
    List<Product> getByCreatedDate();

    @Query(value = " SELECT p FROM Product p WHERE p.discount <> 0")
    List<Product> getByDiscount();

    @Query(value = " SELECT p FROM Product p WHERE CONCAT(p.id, p.productName) LIKE %?1%")
    List<Product> searchAllByProductNameOrId(@Param("keyword") String keyword);

    @Query(value = " SELECT p FROM Product p, ProductCategory pc"
                 + " WHERE p.id = pc.productId AND pc.categoryId =:categoryId")
    List<Product> getAllByCategoryId(@Param("categoryId") String categoryId);
}
