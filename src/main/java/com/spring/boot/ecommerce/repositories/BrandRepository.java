package com.spring.boot.ecommerce.repositories;

import com.spring.boot.ecommerce.entity.Brand;
import com.spring.boot.ecommerce.model.response.brand.ListBrandResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, String> {
    Brand getById(String id);

    @Query(value =
            " SELECT new com.spring.boot.ecommerce.model.response.brand.ListBrandResponse(b, count (p.id))" +
                    " FROM Brand b LEFT JOIN Product p ON b.id = p.brandId " +
                    " GROUP BY b.id, b.brandName, b.status"
    )
    Page<ListBrandResponse> getAllBrand(Pageable pageable);
}
