package com.spring.boot.ecommerce.services.brand;

import com.spring.boot.ecommerce.auth.AuthUser;
import com.spring.boot.ecommerce.common.enums.Message;
import com.spring.boot.ecommerce.common.enums.Status;
import com.spring.boot.ecommerce.common.exceptions.ApplicationException;
import com.spring.boot.ecommerce.common.utils.RestAPIStatus;
import com.spring.boot.ecommerce.common.utils.UniqueID;
import com.spring.boot.ecommerce.entity.Brand;
import com.spring.boot.ecommerce.entity.History;
import com.spring.boot.ecommerce.model.request.brand.BrandRequest;
import com.spring.boot.ecommerce.model.response.brand.ListBrandResponse;
import com.spring.boot.ecommerce.repositories.BrandRepository;
import com.spring.boot.ecommerce.repositories.HistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImplement implements BrandService{

    final private BrandRepository brandRepository;
    final private HistoryRepository historyRepository;

    public BrandServiceImplement(
            BrandRepository brandRepository,
            HistoryRepository historyRepository
    ) {
        this.brandRepository = brandRepository;
        this.historyRepository = historyRepository;
    }

    @Override
    public Brand addBrand(BrandRequest brandRequest, AuthUser authUser) {
        Brand brand = new Brand();
        History history = new History();

        if (brandRequest.getBrandName().isEmpty() || brandRequest.getBrandName() == null) {
            throw new ApplicationException(RestAPIStatus.BAD_REQUEST, "Brand name is not null");
        }

        brand.setId(UniqueID.getUUID());
        brand.setBrandName(brandRequest.getBrandName());
        brand.setStatus(brandRequest.getStatus());
        brand.setUserId(authUser.getId());

        history.setId(UniqueID.getUUID());
        history.setUserId(authUser.getId());
        history.setMessage(Message.CREATE);

        brandRepository.save(brand);
        historyRepository.save(history);
        return brand;
    }

    @Override
    public Brand updateBrand(String id, BrandRequest brandRequest, AuthUser authUser) {
        Brand brand = brandRepository.getById(id);
        History history = new History();

        if (brand == null) {
            throw new ApplicationException(RestAPIStatus.NOT_FOUND);
        }

        if (brandRequest.getBrandName().isEmpty() || brandRequest.getBrandName() == null) {
            throw new ApplicationException(RestAPIStatus.BAD_REQUEST, "Brand name is not null");
        }

        brand.setBrandName(brandRequest.getBrandName());
        brand.setStatus(Status.ACTIVE);
        brand.setUserId(authUser.getId());

        history.setId(UniqueID.getUUID());
        history.setUserId(authUser.getId());
        history.setMessage(Message.UPDATE);

        brandRepository.save(brand);
        historyRepository.save(history);
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
        return brandRepository.getAllByIdExists(pageRequest);
    }

    @Override
    public void deleteBrand(String id, AuthUser authUser) {
        Brand brand = brandRepository.getById(id);
        History history = new History();

        if (brand == null) {
            throw new ApplicationException(RestAPIStatus.NOT_FOUND);
        }

        if (brand.getStatus().equals(Status.IN_ACTIVE)) {

            history.setId(UniqueID.getUUID());
            history.setUserId(authUser.getId());
            history.setMessage(Message.UPDATE);

            historyRepository.save(history);
            brandRepository.delete(brand);
            return;
        }

        brand.setStatus(Status.IN_ACTIVE);
        brand.setUserId(authUser.getId());

        history.setId(UniqueID.getUUID());
        history.setUserId(authUser.getId());
        history.setMessage(Message.UPDATE);

        brandRepository.save(brand);
        historyRepository.save(history);
    }
}
