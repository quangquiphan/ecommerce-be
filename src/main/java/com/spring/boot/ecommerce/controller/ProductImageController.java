package com.spring.boot.ecommerce.controller;

import com.spring.boot.ecommerce.auth.AuthUser;
import com.spring.boot.ecommerce.auth.AuthorizeValidator;
import com.spring.boot.ecommerce.common.AbstractBaseController;
import com.spring.boot.ecommerce.common.enums.UserRole;
import com.spring.boot.ecommerce.common.utils.Constant;
import com.spring.boot.ecommerce.common.utils.RestAPIResponse;
import com.spring.boot.ecommerce.entity.ProductImage;
import com.spring.boot.ecommerce.services.ProductImage.ProductImageService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping(ApiPath.PRODUCT_IMAGE_APIs)
public class ProductImageController extends AbstractBaseController {
    final private ProductImageService productImageService;

    @Value("${project.image}")
    private String path;

    public ProductImageController(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    @Operation(summary = "uploadImage")
    @AuthorizeValidator(UserRole.ADMIN)
    @RequestMapping(path = "/upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE ,method = RequestMethod.POST)
    public ResponseEntity<RestAPIResponse> uploadImage(
            @PathVariable(name = "id") String id,
            @RequestPart(required = true) MultipartFile file,
            HttpServletRequest request
            ) throws IOException {
        try {
            AuthUser user = jwtTokenUtil.getUserIdFromJWT(request.getHeader(Constant.HEADER_TOKEN));
            return responseUtil.successResponse(productImageService.uploadImage(path, id, file, user.getId()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "updateImage")
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<RestAPIResponse> updateImage(
            @PathVariable(name = "id") String id,
            @RequestPart(required = true) MultipartFile file,
            HttpServletRequest request
    ) throws IOException {
        AuthUser user = jwtTokenUtil.getUserIdFromJWT(request.getHeader(Constant.HEADER_TOKEN));
        return responseUtil.successResponse(productImageService.uploadImage(path, id, file, user.getId()));
    }
}
