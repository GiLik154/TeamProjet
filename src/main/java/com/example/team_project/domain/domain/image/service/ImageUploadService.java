package com.example.team_project.domain.domain.image.service;

import com.example.team_project.domain.domain.image.ImageUpload;
import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {
    void upload(String name, MultipartFile multipartFile, ImageUpload imageUpload);
}
