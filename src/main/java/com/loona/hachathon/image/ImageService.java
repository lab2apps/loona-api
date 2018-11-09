package com.loona.hachathon.image;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.loona.hachathon.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageService {

    private final Path rootLocation;

    @Autowired
    public ImageService(@Value("${application.storage.location}") String imageFolder) {
        this.rootLocation = Paths.get(imageFolder);
    }

//    public List<String> saveImages(List<MultipartFile> images) {
//        List<String> imageUrls = new ArrayList<>();
//        for (MultipartFile image : images) {
//            imageUrls.add(saveImage(image));
//        }
//        return imageUrls;
//    }

    public String saveImage(MultipartFile image) {
        try {
                String imageUrl = Hashing.sha1().hashString(image.getOriginalFilename(), Charsets.UTF_8).toString();
                Files.copy(image.getInputStream(), this.rootLocation.resolve(imageUrl));
                return imageUrl;
        } catch (IOException e) {
            throw new ResourceNotFoundException(); //TODO Replace
        }
    }

    public Resource loadImage(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            } else throw new ResourceNotFoundException();
        } catch (MalformedURLException e) {
            throw new ResourceNotFoundException();
        }
    }

}
