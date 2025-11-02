package io.github.matheushenriquereiter.cathub.controller;

import io.github.matheushenriquereiter.cathub.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/images")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.OK).body(imageService.uploadImage(file).getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> downloadImage(@PathVariable Integer id) {
        byte[] imageData = imageService.downloadImage(id);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(IMAGE_PNG_VALUE))
                .body(imageData);
    }
}