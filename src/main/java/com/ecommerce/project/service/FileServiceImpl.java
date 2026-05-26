package com.ecommerce.project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService  {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        // Get name of current/original file
        String originalFileName = file.getOriginalFilename();

        // Generate a unique file name
        String randomId = UUID.randomUUID().toString();

        // mat.jpg --> 1234 --> 1234.jpg
        String fileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf('.')));
        String filePath = path + File.separator + fileName;

        // check if the path exist and create
        File folder = new File(path);
        if(!folder.exists())
            folder.mkdir();

        // Upload the file
        Files.copy(file.getInputStream(), Paths.get(filePath));

        // return the file

        return fileName;
    }
}
