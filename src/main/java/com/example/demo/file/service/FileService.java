package com.example.demo.file.service;

import com.example.demo.file.persistence.entity.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

public interface FileService {

    File upload(File file, MultipartFile multipartFile);

    Optional<InputStream> download(File file);

    Optional<File> get(UUID id);

}
