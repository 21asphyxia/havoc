package com.asphyxia.havoc.controller;

import com.asphyxia.havoc.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
public class ImageController {

    private final ImageService imageService;

    @GetMapping(value = "/{folder}/{imageName}", produces = "image/jpeg")
    public byte[] getImage(@PathVariable String folder, @PathVariable String imageName) {
        return imageService.getImage(folder, imageName);
    }
}
