package com.spring.boot.ecommerce.services.category;

import com.spring.boot.ecommerce.common.enums.Status;
import com.spring.boot.ecommerce.common.exceptions.ApplicationException;
import com.spring.boot.ecommerce.common.utils.RestAPIStatus;
import com.spring.boot.ecommerce.common.utils.UniqueID;
import com.spring.boot.ecommerce.entity.Category;
import com.spring.boot.ecommerce.model.request.category.CategoryRequest;
import com.spring.boot.ecommerce.model.response.category.ListCategoryResponse;
import com.spring.boot.ecommerce.repositories.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImplement implements CategoryService{

    final private CategoryRepository categoryRepository;

    public CategoryServiceImplement(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category addCategory(CategoryRequest categoryRequest) {
        Category category = new Category();

        if (categoryRequest.getCategoryName() == null || categoryRequest.getCategoryName().isEmpty()) {
            throw new ApplicationException(RestAPIStatus.BAD_REQUEST, "Category name not null");
        }

        category.setId(UniqueID.getUUID());
        category.setCategoryName(categoryRequest.getCategoryName());
        category.setStatus(categoryRequest.getStatus());

        categoryRepository.save(category);
        return category;
    }

    @Override
    public Category updateCategory(String id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.getById(id);

        if (category == null) {
            throw new ApplicationException(RestAPIStatus.NOT_FOUND);
        }

        category.setCategoryName(categoryRequest.getCategoryName());
        category.setStatus(categoryRequest.getStatus());

        categoryRepository.save(category);
        return category;
    }

    @Override
    public Category getCategory(String id) {
        Category category = categoryRepository.getById(id);

        if (category == null) {
            throw new ApplicationException(RestAPIStatus.NOT_FOUND);
        }

        return category;
    }

    @Override
    public Page<ListCategoryResponse> getAllCategory(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return categoryRepository.getAllByIdExists(pageRequest);
    }

    @Override
    public void delete(String id) {
        Category category = categoryRepository.getById(id);

        if (category == null) {
            throw new ApplicationException(RestAPIStatus.NOT_FOUND);
        }

        if (category.getStatus().equals(Status.IN_ACTIVE)) {
            System.out.println("1");
            categoryRepository.deleteById(category.getId());
            return;
        }

        category.setStatus(Status.IN_ACTIVE);
        categoryRepository.save(category);
    }
}
