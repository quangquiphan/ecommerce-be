package com.spring.boot.ecommerce.services.brand;

import com.spring.boot.ecommerce.common.enums.Status;
import com.spring.boot.ecommerce.common.exceptions.ApplicationException;
import com.spring.boot.ecommerce.common.utils.RestAPIStatus;
import com.spring.boot.ecommerce.common.utils.UniqueID;
import com.spring.boot.ecommerce.entity.Brand;
import com.spring.boot.ecommerce.model.request.brand.BrandRequest;
import com.spring.boot.ecommerce.model.response.brand.ListBrandResponse;
import com.spring.boot.ecommerce.repositories.BrandRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImplement implements BrandService{

    final private BrandRepository brandRepository;

    public BrandServiceImplement(
            BrandRepository brandRepository
    ) {
        this.brandRepository = brandRepository;
    }

    @Override
    public Brand addBrand(BrandRequest brandRequest) {
        Brand brand = new Brand();

        if (brandRequest.getBrandName().isEmpty() || brandRequest.getBrandName() == null) {
            throw new ApplicationException(RestAPIStatus.BAD_REQUEST, "Brand name is not null");
        }

        brand.setId(UniqueID.getUUID());
        brand.setBrandName(brandRequest.getBrandName());
        brand.setStatus(brandRequest.getStatus());

        brandRepository.save(brand);
        return brand;
    }

    @Override
    public Brand updateBrand(String id, BrandRequest brandRequest) {
        Brand brand = brandRepository.getById(id);

        if (brand == null) {
            throw new ApplicationException(RestAPIStatus.NOT_FOUND);
        }

        if (brandRequest.getBrandName().isEmpty() || brandRequest.getBrandName() == null) {
            throw new ApplicationException(RestAPIStatus.BAD_REQUEST, "Brand name is not null");
        }

        brand.setBrandName(brandRequest.getBrandName());
        brand.setStatus(Status.ACTIVE);

        brandRepository.save(brand);
        return brand;
    }

    @Override
    public Brand getBrand(String id) {
        Brand brand = brandRepository.getById(id);

        if (brand == null) {
            throw new ApplicationException(RestAPIStatus.NOT_FOUND);
        }

        return brand;
    }

    @Override
    public Page<ListBrandResponse> getAllBrand(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return brandRepository.getAllBrand(pageRequest);
    }

    @Override
    public void deleteBrand(String id) {
        Brand brand = brandRepository.getById(id);

        if (brand == null) {
            throw new ApplicationException(RestAPIStatus.NOT_FOUND);
        }

        if (brand.getStatus().equals(Status.IN_ACTIVE)) {
            brandRepository.delete(brand);
            return;
        }

        brand.setStatus(Status.IN_ACTIVE);

        brandRepository.save(brand);
    }
}
