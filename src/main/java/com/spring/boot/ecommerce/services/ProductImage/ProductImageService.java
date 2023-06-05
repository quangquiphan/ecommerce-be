package com.spring.boot.ecommerce.services.ProductImage;

import com.spring.boot.ecommerce.entity.ProductImage;
import com.spring.boot.ecommerce.model.response.product.ProductImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductImageService {
    ProductImage uploadImage(String path, String id, MultipartFile multipartFile) throws IOException;

   List<ProductImageResponse> loadImage(String id);

    String deleteAllByProductId(String productId);

    String delete(String id);
}
