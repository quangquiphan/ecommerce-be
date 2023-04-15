package com.spring.boot.ecommerce.services.ProductImage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductImageService {
    String uploadImage(String path, MultipartFile multipartFile) throws IOException;
}
