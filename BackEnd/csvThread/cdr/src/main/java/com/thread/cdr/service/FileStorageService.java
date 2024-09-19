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

    private final String upDestiny = "upload/";

    public String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("File is empty");
        }
        File directory = new File(upDestiny);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path path = Paths.get(upDestiny, fileName);
        Files.copy(file.getInputStream(), path);

        return fileName;
    }

}
