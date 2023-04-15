package com.spring.boot.ecommerce.controller;

import com.spring.boot.ecommerce.common.AbstractBaseController;
import com.spring.boot.ecommerce.common.utils.RestAPIResponse;
import com.spring.boot.ecommerce.services.ProductImage.ProductImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public ResponseEntity<RestAPIResponse> uploadImage(
            @RequestParam("image") MultipartFile multipartFile
            ) throws IOException {
        System.out.println(path + "controller");
        String message = "";
        System.out.println("2");
        try {
            System.out.println("3");
            message = productImageService.uploadImage(path, multipartFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
