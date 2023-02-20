package com.spring.boot.ecommerce.services.brand;

import com.spring.boot.ecommerce.auth.AuthUser;
import com.spring.boot.ecommerce.entity.Brand;
import com.spring.boot.ecommerce.model.request.brand.BrandRequest;
import com.spring.boot.ecommerce.model.response.brand.ListBrandResponse;
import org.springframework.data.domain.Page;

public interface BrandService {
    Brand addBrand(BrandRequest brandRequest, AuthUser authUser);

    Brand updateBrand(String id, BrandRequest brandRequest, AuthUser authUser);

    Brand getBrand(String id);

    Page<ListBrandResponse> getAllBrand(int pageNumber, int pageSize);

    void deleteBrand(String id, AuthUser authUser);
}
