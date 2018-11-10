package com.loona.hachathon.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping("/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {

        Resource file = imageService.loadImage(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

    @PostMapping
    public String saveImage(@RequestParam(value = "image", required = false) MultipartFile image) {
        return imageService.saveImage(image);
    }
}
