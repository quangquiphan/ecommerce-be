package com.spring.boot.ecommerce.services.ProductImage;

import com.spring.boot.ecommerce.entity.ProductImage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class ProductImageServiceImplement implements ProductImageService{
    @Override
    public String uploadImage(String path, MultipartFile multipartFile) throws IOException {

        System.out.println(multipartFile);
        // File name
        String name = multipartFile.getOriginalFilename();

        // Fullpath
        String filePath = path + File.pathSeparator + name;

        // create folder if not create
        File file = new File(path);

        if (!file.exists()) {
            file.mkdir();
        }
        System.out.println(filePath);
        // file coppy
        Files.copy(multipartFile.getInputStream(), Paths.get(filePath));

        return "Upload image successfully!";
    }
}
