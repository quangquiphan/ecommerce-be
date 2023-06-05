package com.spring.boot.ecommerce.controller;

import com.spring.boot.ecommerce.auth.AuthorizeValidator;
import com.spring.boot.ecommerce.common.AbstractBaseController;
import com.spring.boot.ecommerce.common.enums.UserRole;
import com.spring.boot.ecommerce.common.utils.RestAPIResponse;
import com.spring.boot.ecommerce.model.request.product.ProductRequest;
import com.spring.boot.ecommerce.model.response.PagingResponse;
import com.spring.boot.ecommerce.model.response.product.ProductResponse;
import com.spring.boot.ecommerce.services.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping(ApiPath.PRODUCT_APIs)
@RestController
public class ProductController extends AbstractBaseController {
    final private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "addProduct")
    @AuthorizeValidator(UserRole.ADMIN)
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<RestAPIResponse> addProduct(
            @RequestBody ProductRequest productRequest,
            HttpServletRequest servletRequest
    ) {
        return responseUtil.successResponse( new ProductResponse(
                        productService.addProduct(productRequest),
                        productRequest.getCategoryIds())
        );
    }

    @Operation(summary = "updateProduct")
    @AuthorizeValidator(UserRole.ADMIN)
    @RequestMapping(path = ApiPath.ID, method = RequestMethod.PUT)
    public ResponseEntity<RestAPIResponse> updateProduct(
            @PathVariable String id,
            @RequestBody ProductRequest productRequest,
            HttpServletRequest servletRequest
    ) {
        return responseUtil.successResponse(new ProductResponse(
                productService.updateProduct(id, productRequest),
                productRequest.getCategoryIds())
        );
    }

    @Operation(summary = "getProduct")
    @RequestMapping(path = ApiPath.ID, method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getProduct(
            @PathVariable String id
    ) {
        return responseUtil.successResponse(productService.getProduct(id));
    }

    @Operation(summary = "getAllProduct")
    @RequestMapping(path = ApiPath.ALL, method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getAllProduct(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return responseUtil.successResponse(
                new PagingResponse(productService.getAllProduct(), productService.getPage(pageNumber, pageSize))
        );
    }

    @Operation(summary = "deleteProduct")
    @RequestMapping(path = ApiPath.ID, method = RequestMethod.DELETE)
    public ResponseEntity<RestAPIResponse> deleteProduct(
            @PathVariable String id
    ) {
        productService.delete(id);
        return responseUtil.successResponse("Delete successfully!");
    }
}
