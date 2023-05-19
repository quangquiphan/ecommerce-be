package com.spring.boot.ecommerce.services.ProductImage;

import com.spring.boot.ecommerce.common.exceptions.ApplicationException;
import com.spring.boot.ecommerce.common.utils.RestAPIStatus;
import com.spring.boot.ecommerce.common.utils.UniqueID;
import com.spring.boot.ecommerce.entity.ProductImage;
import com.spring.boot.ecommerce.entity.User;
import com.spring.boot.ecommerce.model.response.product.ProductImageResponse;
import com.spring.boot.ecommerce.repositories.ProductImageRepository;
import com.spring.boot.ecommerce.repositories.UserRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductImageServiceImplement implements ProductImageService{
    final ProductImageRepository productImageRepository;
    final UserRepository userRepository;

    public ProductImageServiceImplement(
            ProductImageRepository productImageRepository,
            UserRepository userRepository) {
        this.productImageRepository = productImageRepository;
        this.userRepository = userRepository;
    }

    private final Path root = Paths.get("src/main/resources/images");

    @Override
    public ProductImage uploadImage(String path, String id, MultipartFile multipartFile, String userId)
            throws IOException {
        User user = userRepository.getById(userId);
        if (user == null) {
            throw new ApplicationException(RestAPIStatus.NOT_FOUND);
        }

        // File name
        String name = new Date().getTime() + "." + getFileExtension(multipartFile.getOriginalFilename());

        // Full path
        String filePath = path + name;

        // create folder if not create
        File file = new File(path);

        if (!file.exists()) {
            file.mkdir();
        }

        // file copy
        Files.copy(multipartFile.getInputStream(), Paths.get(filePath));

        ProductImage image = new ProductImage();
        image.setId(UniqueID.getUUID());
        image.setProductId(id);
        image.setPath(name);
        image.setUserId(user.getId());

        return productImageRepository.save(image);
    }

    @Override
    public ProductImage updateImage(String path, String id, MultipartFile multipartFile, String userId) throws IOException {
        ProductImage image = productImageRepository.getById(id);
        if (image == null) {
            throw new ApplicationException(RestAPIStatus.NOT_FOUND);
        }

        User user = userRepository.getById(userId);
        if (user == null) {
            throw new ApplicationException(RestAPIStatus.NOT_FOUND);
        }

        // File name
        String name = multipartFile.getOriginalFilename();

        // Full path
        String filePath = path + name;

        // create folder if not create
        File file = new File(path);

        if (!file.exists()) {
            file.mkdir();
        }

        // file copy
        Files.copy(multipartFile.getInputStream(), Paths.get(filePath));

        image.setPath(name);
        image.setUserId(user.getId());

        return productImageRepository.save(image);
    }

    @Override
    public List<ProductImageResponse> loadImage(String id) {
        List<ProductImageResponse> images = new ArrayList<>();

            List<ProductImageResponse> productImages = productImageRepository.getAllByProductId(id).stream().map(
                    dbFile -> {
                        String file = ServletUriComponentsBuilder
                                .fromCurrentContextPath()
                                .path("/images/")
                                .path(dbFile.getPath())
                                .toUriString();

                        return new ProductImageResponse(dbFile.getId(), file, dbFile.getPath(), dbFile.getUserId());
                    }
            ).collect(Collectors.toList());
            
            for (int i = 0; i < productImages.size(); i++) {
                ProductImageResponse image =
                        new ProductImageResponse(productImages.get(i).getUrl(), productImages.get(i));

                images.add(image);
            }
        return images;
    }

    @Override
    public String deleteAllByProductId(String productId) {
        List<ProductImage> images = productImageRepository.getAllByProductId(productId);
        productImageRepository.deleteAll(images);

        return "Delete successfully";
    }

    @Override
    public String delete(String id) {
        ProductImage image = productImageRepository.getById(id);

        if (image == null) {
            throw new ApplicationException(RestAPIStatus.NOT_FOUND);
        }

        productImageRepository.delete(image);

        return "Delete successfully";
    }

    private String getFileExtension(String file) {
        if (file == null) return null;

        String[] fileName = file.split("\\.");
        return fileName[fileName.length - 1];
    }
}
