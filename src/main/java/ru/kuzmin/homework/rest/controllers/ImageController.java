package ru.kuzmin.homework.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kuzmin.homework.rest.services.ImageService;

import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(value = "/upload")
    public String uploadPhoto(@RequestBody MultipartFile file, @RequestParam String nameFile,
                              @RequestParam int personId) throws IOException {
        if (!file.isEmpty()) {
            return imageService.upLoadPhoto(file, nameFile, personId);
        } else {
            return "Файл не удалось загрузить, потому что файл пустой.";
        }
    }
}
