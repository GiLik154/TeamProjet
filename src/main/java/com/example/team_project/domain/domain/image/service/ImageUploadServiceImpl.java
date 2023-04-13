package com.example.team_project.domain.domain.image.service;

import com.example.team_project.domain.domain.image.ImageUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class ImageUploadServiceImpl implements ImageUploadService {

    @Override
    public void upload(String name, MultipartFile multipartFile, ImageUpload imageUpload) {
        String fileName = getFileName();

        if (!multipartFile.isEmpty()) {
            imageUpload.uploadImage("/" + getUploadPath(name, multipartFile, fileName) + fileName);
        }else{
            System.out.println("이미지"+multipartFile);
            imageUpload.uploadImage("/images/null/null.jpg");
        }

    }

    private String getFileName() {
        return StringUtils.cleanPath(creatFileName());
    }

    private String getUploadPath(String name, MultipartFile multipartFile, String fileName) {
        String uploadDir = "images/" + name + "/";
        Path uploadPath = Paths.get(uploadDir);

        checkDirectories(uploadPath);

        uploadImage(multipartFile, uploadPath, fileName);

        return uploadDir;
    }



    private String creatFileName() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSSSSS");
        return now.format(formatter) + ".jpg";
    }

    private void checkDirectories(Path uploadPath) {
        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void uploadImage(MultipartFile file, Path uploadPath, String fileName) {
        if (file != null) {
            try (InputStream inputStream = file.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
