package org.example.ra.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {
    String upload(MultipartFile avatar) throws IOException;
}
