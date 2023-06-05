package com.spring.boot.ecommerce.services.product;

import com.spring.boot.ecommerce.common.enums.Status;
import com.spring.boot.ecommerce.common.exceptions.ApplicationException;
import com.spring.boot.ecommerce.common.utils.RestAPIStatus;
import com.spring.boot.ecommerce.common.utils.UniqueID;
import com.spring.boot.ecommerce.entity.*;
import com.spring.boot.ecommerce.model.request.product.ProductRequest;
import com.spring.boot.ecommerce.model.response.product.GetProductResponse;
import com.spring.boot.ecommerce.model.response.product.ProductImageResponse;
import com.spring.boot.ecommerce.repositories.*;
import com.spring.boot.ecommerce.services.ProductImage.ProductImageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImplement implements ProductService {

    final private CategoryRepository categoryRepository;
    final private BrandRepository brandRepository;
    final private ProductRepository productRepository;
    final private ProductCategoryRepository productCategoryRepository;
    final ProductImageService productImageService;

    public ProductServiceImplement(
            CategoryRepository categoryRepository,
            BrandRepository brandRepository, ProductRepository productRepository,
            ProductCategoryRepository productCategoryRepository,
            ProductImageService productImageService) {
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.productImageService = productImageService;
    }

    @Override
    public Product addProduct(ProductRequest request) {
        Product product = new Product();
        List<ProductCategory> productCategories = new ArrayList<>();

        if (request.getProductName() == null || request.getProductName().isEmpty()) {
            throw new ApplicationException(RestAPIStatus.BAD_PARAMS, "Product name not null");
        }

        product.setId(UniqueID.getUUID());
        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setDiscount(request.getDiscount());
        product.setQuantity(request.getQuantity());
        product.setStatus(Status.ACTIVE);

        request.getCategoryIds().forEach((item) -> {
            Category category = categoryRepository.getById(item);
            ProductCategory productCategory = new ProductCategory();
            productCategory.setId(UniqueID.getUUID());
            productCategory.setProductId(product.getId());
            productCategory.setCategoryId(category.getId());
            productCategories.add(productCategory);
        });

        product.setBrandId(request.getBrandId());

        productRepository.save(product);
        productCategoryRepository.saveAll(productCategories);
        return product;
    }

    @Override
    public Product updateProduct(String id, ProductRequest request) {
        Product product = productRepository.getById(id);

        if (product == null) {
            throw new ApplicationException(RestAPIStatus.NOT_FOUND);
        }
        List<ProductCategory> productCategories = new ArrayList<>();
        List<ProductCategory> currentProductCategories = productCategoryRepository.findAllByProductId(product.getId());

        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setDiscount(request.getDiscount());
        product.setQuantity(request.getQuantity());
        product.setStatus(request.getStatus());

        if (!request.getCategoryIds().isEmpty() || request.getCategoryIds() != null) {
            List<String> categoryIds = request.getCategoryIds();
            for (int i = 0; i < categoryIds.size(); i++) {
                for (int j = 0; j < currentProductCategories.size(); j++) {
                    if (categoryIds.get(i).toString()
                            .equals(currentProductCategories.get(j).getCategoryId().toString())) {
                        productCategories.add(currentProductCategories.get(j));
                        categoryIds.remove(i);
                        break;
                    }
                }
            }

            for (int i = 0; i < categoryIds.size(); i++) {
                ProductCategory productCategory = new ProductCategory();

                Category category = categoryRepository.getById(categoryIds.get(i).toString());

                productCategory.setId(UniqueID.getUUID());
                productCategory.setProductId(product.getId());
                productCategory.setCategoryId(category.getId());

                productCategories.add(productCategory);
            }
        }

        product.setBrandId(request.getBrandId());

        productCategoryRepository.deleteAll(currentProductCategories);
        productCategoryRepository.saveAll(productCategories);
        return productRepository.save(product);
    }

    @Override
    public GetProductResponse getProduct(String id) {
        Product product = productRepository.getById(id);

        if (product == null) {
            throw new ApplicationException(RestAPIStatus.NOT_FOUND);
        }

        List<Category> categories = productCategoryRepository.getAllByProductId(product.getId());
        List<ProductImageResponse> images = productImageService.loadImage(id);

        Brand brand = brandRepository.getById(product.getBrandId());

        return new GetProductResponse(product, categories, images, brand);
    }

    @Override
    public List<GetProductResponse> getAllProduct() {
        List<GetProductResponse> productResponses = new ArrayList<>();
        List<Product> products = productRepository.findAll();

        for (int i = 0; i < products.size(); i++) {
            List<ProductImageResponse> images = productImageService.loadImage(products.get(i).getId());
            List<Category> categories = productCategoryRepository.getAllByProductId(products.get(i).getId());

            Brand brand = brandRepository.getById(products.get(i).getBrandId());

            GetProductResponse productResponse = new GetProductResponse(products.get(i), categories, images, brand);
            productResponses.add(productResponse);
        }

        return productResponses;
    }

    @Override
    public Page<Product> getPage(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return productRepository.findAll(pageRequest);
    }

    @Override
    public void delete(String id) {
        Product product = productRepository.getById(id);

        if (product == null) {
            throw new ApplicationException(RestAPIStatus.NOT_FOUND);
        }

        if (product.getStatus().equals(Status.IN_ACTIVE)) {

            List<ProductCategory> currentListCategory = productCategoryRepository.findAllByProductId(product.getId());

            productCategoryRepository.deleteAll(currentListCategory);
            productImageService.deleteAllByProductId(id);
            return;
        }

        product.setStatus(Status.IN_ACTIVE);
        productRepository.save(product);
    }
}
