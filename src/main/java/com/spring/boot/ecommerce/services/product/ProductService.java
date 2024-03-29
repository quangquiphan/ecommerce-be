package com.spring.boot.ecommerce.services.product;

import com.spring.boot.ecommerce.entity.Product;
import com.spring.boot.ecommerce.model.request.product.ProductRequest;
import com.spring.boot.ecommerce.model.response.product.GetProductResponse;
import com.spring.boot.ecommerce.model.response.product.HomePage;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Product addProduct(ProductRequest request);

    Product updateProduct(String id, ProductRequest request);

    GetProductResponse getProduct(String id);

    HomePage getHomepageProduct();

    List<GetProductResponse> getAllProduct();

    List<GetProductResponse> filterAllProduct();

    List<GetProductResponse> searchProduct(String keyword);

    Page<Product> getPage(int pageNumber, int pageSize);

    void delete(String id);
}
