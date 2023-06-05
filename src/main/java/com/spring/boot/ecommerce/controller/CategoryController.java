package com.spring.boot.ecommerce.controller;

import com.spring.boot.ecommerce.auth.AuthorizeValidator;
import com.spring.boot.ecommerce.common.AbstractBaseController;
import com.spring.boot.ecommerce.common.enums.UserRole;
import com.spring.boot.ecommerce.common.utils.RestAPIResponse;
import com.spring.boot.ecommerce.entity.Category;
import com.spring.boot.ecommerce.model.request.category.CategoryRequest;
import com.spring.boot.ecommerce.model.response.PagingResponse;
import com.spring.boot.ecommerce.model.response.category.CategoryResponse;
import com.spring.boot.ecommerce.services.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(ApiPath.CATEGORY_APIs)
public class CategoryController extends AbstractBaseController {
    final private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "addCategory")
    @AuthorizeValidator(UserRole.ADMIN)
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<RestAPIResponse> addCategory(
            @RequestBody CategoryRequest categoryRequest
    ) {
        Category category = categoryService.addCategory(categoryRequest);
        return responseUtil.successResponse(
                new CategoryResponse(category)
        );
    }

    @Operation(summary = "updateCategory")
    @AuthorizeValidator(UserRole.ADMIN)
    @RequestMapping(path = ApiPath.ID, method = RequestMethod.PUT)
    public ResponseEntity<RestAPIResponse> updateCategory(
            @PathVariable String id,
            @RequestBody CategoryRequest categoryRequest
    ) {
        Category category = categoryService.updateCategory(id, categoryRequest);
        return responseUtil.successResponse(
                new CategoryResponse(category)
        );
    }

    @Operation(summary = "getCategory")
    @RequestMapping(path = ApiPath.ID, method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getCategory(
            @PathVariable String id
    ) {
        Category category = categoryService.getCategory(id);
        return responseUtil.successResponse(
                new CategoryResponse(category)
        );
    }

    @Operation(summary = "getAllCategory")
    @RequestMapping(path = ApiPath.ALL, method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getAllCategory(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return responseUtil.successResponse(
              new PagingResponse(categoryService.getAllCategory(pageNumber, pageSize))
        );
    }

    @Operation(summary = "deleteCategory")
    @RequestMapping(path = ApiPath.ID, method = RequestMethod.DELETE)
    public ResponseEntity<RestAPIResponse> deleteCategory(
            @PathVariable String id
    ) {
        categoryService.delete(id);
        return responseUtil.successResponse("Delete successfully!");
    }
}
