package com.asphyxia.havoc.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    String saveImageToStorage(String uploadDirectory, MultipartFile imageFile) throws IOException;

    // To view an image
    byte[] getImage(String imageDirectory, String imageName);

    // Delete an image
    String deleteImage(String imageDirectory, String imageName) throws IOException;
}
