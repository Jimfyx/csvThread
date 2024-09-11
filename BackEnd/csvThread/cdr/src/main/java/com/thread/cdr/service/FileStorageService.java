package com.thread.cdr.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    private final String upDestiny = "../resources/";

    public String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("File is empty");
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path path = Paths.get(upDestiny, fileName);
        Files.copy(file.getInputStream(), path);

        return fileName;
    }
    
}
