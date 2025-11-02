package io.github.matheushenriquereiter.cathub.service;

import io.github.matheushenriquereiter.cathub.entity.Image;
import io.github.matheushenriquereiter.cathub.repository.ImageRepository;
import io.github.matheushenriquereiter.cathub.util.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public Image uploadImage(MultipartFile imageFile) {
        try {
            var imageToSave = Image.builder()
                    .name(imageFile.getOriginalFilename())
                    .type(imageFile.getContentType())
                    .imageData(ImageUtils.compressImage(imageFile.getBytes()))
                    .build();

            return imageRepository.save(imageToSave);
        } catch (IOException error) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao construir imagem.");
        }
    }

    public byte[] downloadImage(Integer id) {
        Optional<Image> dbImage = imageRepository.findById(id);

        return dbImage.map(image -> {
            try {
                return ImageUtils.decompressImage(image.getImageData());
            } catch (DataFormatException | IOException exception) {
                throw new ContextedRuntimeException("Error downloading an image", exception)
                        .addContextValue("Image ID", image.getId());
            }
        }).orElse(null);
    }
}