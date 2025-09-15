package org.example.ra.service.impl;


import com.cloudinary.Cloudinary;
import org.example.ra.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;

    public String upload(MultipartFile avatar) throws IOException {
        Map<String,Object> map = cloudinary.uploader().upload(avatar.getBytes(),Map.of("folder","avatar"));
        return String.valueOf(map.get("id"));
    }
}
