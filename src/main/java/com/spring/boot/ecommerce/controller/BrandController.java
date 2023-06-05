package com.spring.boot.ecommerce.controller;

import com.spring.boot.ecommerce.auth.AuthorizeValidator;
import com.spring.boot.ecommerce.common.AbstractBaseController;
import com.spring.boot.ecommerce.common.enums.UserRole;
import com.spring.boot.ecommerce.common.utils.RestAPIResponse;
import com.spring.boot.ecommerce.entity.Brand;
import com.spring.boot.ecommerce.model.request.brand.BrandRequest;
import com.spring.boot.ecommerce.model.response.PagingResponse;
import com.spring.boot.ecommerce.model.response.brand.BrandResponse;
import com.spring.boot.ecommerce.services.brand.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(ApiPath.BRAND_APIs)
public class BrandController extends AbstractBaseController {

    final private BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @Operation(summary = "addBrand")
    @AuthorizeValidator(UserRole.ADMIN)
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<RestAPIResponse> addBrand(
            @RequestBody BrandRequest brandRequest
    ) {
        Brand brand = brandService.addBrand(brandRequest);
        return responseUtil.successResponse(
                new BrandResponse(brand)
        );
    }

    @Operation(summary = "updateBrand")
    @AuthorizeValidator(UserRole.ADMIN)
    @RequestMapping(path = ApiPath.ID, method = RequestMethod.PUT)
    public ResponseEntity<RestAPIResponse> updateBrand(
            @PathVariable String id,
            @RequestBody BrandRequest brandRequest
    ) {
        Brand brand = brandService.updateBrand(id, brandRequest);
        return responseUtil.successResponse(
                new BrandResponse(brand)
        );
    }

    @Operation(summary = "getBrand")
    @RequestMapping(path = ApiPath.ID, method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getBrand(
            @PathVariable String id
    ) {
        Brand brand = brandService.getBrand(id);
        return responseUtil.successResponse(
                new BrandResponse(brand)
        );
    }

    @Operation(summary = "getAllBrand")
    @RequestMapping(path = ApiPath.ALL, method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getAllBrand(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return responseUtil.successResponse(
                new PagingResponse(brandService.getAllBrand(pageNumber, pageSize))
        );
    }

    @Operation(summary = "deleteBrand")
    @RequestMapping(path = ApiPath.ID, method = RequestMethod.DELETE)
    public ResponseEntity<RestAPIResponse> deleteBrand(
            @PathVariable String id
    ) {
        brandService.deleteBrand(id);
        return responseUtil.successResponse("Delete successfully!");
    }
}
