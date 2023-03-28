package com.spring.boot.ecommerce.services.product;

import com.spring.boot.ecommerce.auth.AuthUser;
import com.spring.boot.ecommerce.entity.Product;
import com.spring.boot.ecommerce.model.request.product.ProductRequest;
import com.spring.boot.ecommerce.model.response.product.GetProductResponse;
import org.springframework.data.domain.Page;

public interface ProductService {
    Product addProduct(ProductRequest request, AuthUser authUser);

    Product updateProduct(String id, ProductRequest request, AuthUser authUser);

    GetProductResponse getProduct(String id);

    Page<Product> getAllProduct(int pageNumber, int pageSize);

    void delete(String id, AuthUser authUser);
}
