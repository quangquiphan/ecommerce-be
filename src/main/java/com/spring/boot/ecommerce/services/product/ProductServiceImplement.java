package com.spring.boot.ecommerce.services.product;

import com.spring.boot.ecommerce.auth.AuthUser;
import com.spring.boot.ecommerce.common.enums.Status;
import com.spring.boot.ecommerce.common.exceptions.ApplicationException;
import com.spring.boot.ecommerce.common.utils.RestAPIStatus;
import com.spring.boot.ecommerce.common.utils.UniqueID;
import com.spring.boot.ecommerce.entity.*;
import com.spring.boot.ecommerce.model.request.product.ProductRequest;
import com.spring.boot.ecommerce.model.response.product.GetProductResponse;
import com.spring.boot.ecommerce.repositories.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImplement implements ProductService{

    final private CategoryRepository categoryRepository;
    final private BrandRepository brandRepository;
    final private ProductRepository productRepository;
    final private ProductCategoryRepository productCategoryRepository;
    final private ProductBrandRepository productBrandRepository;

    public ProductServiceImplement(
            CategoryRepository categoryRepository,
            BrandRepository brandRepository, ProductRepository productRepository,
            ProductCategoryRepository productCategoryRepository,
            ProductBrandRepository productBrandRepository
    ) {
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.productBrandRepository = productBrandRepository;
    }

    @Override
    public Product addProduct(ProductRequest request, AuthUser authUser) {
        Product product = new Product();
        List<Brand> brands = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        List<ProductBrand> productBrands = new ArrayList<>();
        List<ProductCategory> productCategories = new ArrayList<>();

        product.setId(UniqueID.getUUID());
        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setDiscount(request.getDiscount());
        product.setQuantity(request.getQuantity());
        product.setStatus(Status.ACTIVE);
        product.setUserId(authUser.getId());

        request.getCategoryIds().forEach((item) -> {
            Category category = categoryRepository.getById(item);
            ProductCategory productCategory = new ProductCategory();
            productCategory.setId(UniqueID.getUUID());
            productCategory.setProductId(product.getId());
            productCategory.setCategoryId(category.getId());
            productCategories.add(productCategory);
            categories.add(category);
        });

        request.getBrandIds().forEach((item) -> {
            Brand brand = brandRepository.getById(item);
            ProductBrand productBrand = new ProductBrand();
            productBrand.setId(UniqueID.getUUID());
            productBrand.setProductId(product.getId());
            productBrand.setBrandId(brand.getId());
            productBrands.add(productBrand);
            brands.add(brand);
        });

        productRepository.save(product);
        productCategoryRepository.saveAll(productCategories);
        productBrandRepository.saveAll(productBrands);
        return product;
    }

    @Override
    public Product updateProduct(String id, ProductRequest request, AuthUser authUser) {
        Product product = productRepository.getById(id);

        if (product == null) {
            throw new ApplicationException(RestAPIStatus.NOT_FOUND);
        }

        List<Brand> brands = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        List<ProductBrand> productBrands = new ArrayList<>();
        List<ProductCategory> productCategories = new ArrayList<>();
        List<ProductCategory> currentListCategory = productCategoryRepository.findAllByProductId(product.getId());
        List<ProductBrand> currentListBrand = productBrandRepository.findAllByProductId(product.getId());

        productCategoryRepository.deleteAll(currentListCategory);
        productBrandRepository.deleteAll(currentListBrand);

        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setDiscount(request.getDiscount());
        product.setQuantity(request.getQuantity());
        product.setUserId(authUser.getId());
        product.setStatus(request.getStatus());

        request.getCategoryIds().forEach((item) -> {
            Category category = categoryRepository.getById(item);
            ProductCategory productCategory = new ProductCategory();
            productCategory.setId(UniqueID.getUUID());
            productCategory.setProductId(product.getId());
            productCategory.setCategoryId(category.getId());
            productCategories.add(productCategory);
            categories.add(category);
        });

        request.getBrandIds().forEach((item) -> {
            Brand brand = brandRepository.getById(item);
            ProductBrand productBrand = new ProductBrand();
            productBrand.setId(UniqueID.getUUID());
            productBrand.setProductId(product.getId());
            productBrand.setBrandId(brand.getId());
            productBrands.add(productBrand);
            brands.add(brand);
        });

        productRepository.save(product);
        productCategoryRepository.saveAll(productCategories);
        productBrandRepository.saveAll(productBrands);
        return product;
    }

    @Override
    public GetProductResponse getProduct(String id) {
        Product product = productRepository.getById(id);

        if(product == null) {
            throw new ApplicationException(RestAPIStatus.NOT_FOUND);
        }

        List<Brand> brands = productBrandRepository.getAllByProductId(product.getId());
        List<Category> categories = productCategoryRepository.getAllByProductId(product.getId());

        return new GetProductResponse(product, categories, brands);
    }

    @Override
    public Page<Product> getAllProduct(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return productRepository.findAll(pageRequest);
    }

    @Override
    public void delete(String id, AuthUser authUser) {
        Product product = productRepository.getById(id);

        if (product == null) {
            throw new ApplicationException(RestAPIStatus.NOT_FOUND);
        }

        if (product.getStatus().equals(Status.IN_ACTIVE)) {

            List<ProductCategory> currentListCategory = productCategoryRepository.findAllByProductId(product.getId());
            List<ProductBrand> currentListBrand = productBrandRepository.findAllByProductId(product.getId());

            productCategoryRepository.deleteAll(currentListCategory);
            productBrandRepository.deleteAll(currentListBrand);
        }

        product.setStatus(Status.IN_ACTIVE);
        product.setUserId(authUser.getId());
        productRepository.save(product);
    }
}
