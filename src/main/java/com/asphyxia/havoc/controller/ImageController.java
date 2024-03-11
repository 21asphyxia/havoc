package com.asphyxia.havoc.controller;

import com.asphyxia.havoc.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
public class ImageController {

    private final ImageService imageService;

    @GetMapping(value = "/{folder}/{imageName}",produces = "image/jpeg")
    public byte[] getImage(@PathVariable String folder, @PathVariable String imageName) {
        return imageService.getImage(folder, imageName);
    }
}
