package com.ibdbcompany.ibdb.web.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

import com.ibdbcompany.ibdb.domain.Author;
import com.ibdbcompany.ibdb.domain.Book;
import com.ibdbcompany.ibdb.domain.ImageModel;
import com.ibdbcompany.ibdb.domain.User;
import com.ibdbcompany.ibdb.repository.ImageRepository;
import com.ibdbcompany.ibdb.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
;
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class ImageUploadController {

    private static final String ENTITY_NAME = "image";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    ImageRepository imageRepository;

    @PostMapping("image/upload")
    public ImageModel uplaodImage(@RequestParam("myFile") MultipartFile file) throws IOException {
        ImageModel img = new ImageModel( file.getOriginalFilename(),file.getContentType(),file.getBytes() );
        final ImageModel savedImage = imageRepository.save(img);
        System.out.println("Image saved");
        return savedImage;
    }

    @GetMapping(path = { "/image/{id}" })
    public ImageModel getImage(@PathVariable("id") Long id) throws IOException {
        final Optional<ImageModel> retrievedImage = imageRepository.findById(id);
        ImageModel img = new ImageModel(retrievedImage.get().getName(), retrievedImage.get().getType(),retrievedImage.get().getPicByte());
        return img;
    }

    @PutMapping(path = { "/image/{id}" })
    public ImageModel updateImage(@PathVariable("id") Long id, @RequestParam("myFile") MultipartFile file) throws IOException {
        ImageModel img = new ImageModel( file.getOriginalFilename(),file.getContentType(),file.getBytes() );
        img.setId(id);
        final ImageModel updatedImage = imageRepository.save(img);
        System.out.println("Image updated");
        return updatedImage;
    }

    @DeleteMapping("/image/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        final Optional<ImageModel> retrievedImage = imageRepository.findById(id);
        ImageModel img = new ImageModel(retrievedImage.get().getName(), retrievedImage.get().getType(),retrievedImage.get().getPicByte());
        imageRepository.delete(img);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
