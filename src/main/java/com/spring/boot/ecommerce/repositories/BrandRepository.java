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
            " SELECT new com.spring.boot.ecommerce.model.response.brand.ListBrandResponse(b, count (pb.productId))" +
                    " FROM Brand b LEFT JOIN ProductBrand pb ON b.id = pb.brandId " +
                    " GROUP BY b.id, b.brandName, b.status, b.userId"
    )
    Page<ListBrandResponse> getAllByIdExists(Pageable pageable);
}
