package com.example.demo.file.service.impl;

import com.example.demo.file.persistence.entity.File;
import com.example.demo.file.persistence.repository.FileRepository;
import com.example.demo.file.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private ServerProperties serverProperties;

    @Value("${app.upload.dir:${user.dir}/images}")
    private String uploadDir;

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Map<String, String> fileExtensionMap = new HashMap<>();

    @PostConstruct
    private void init() {
        fileExtensionMap.put("image/jpeg", "jpg");
    }

    @Override
    @Transactional
    public File upload(File file, MultipartFile multipartFile) {
        logger.debug("Upload dir: " + uploadDir);

        if (file == null) {
            file = new File();
            file.setContentType(multipartFile.getContentType());
            file.setOriginalName(multipartFile.getOriginalFilename());
            file.setSize(multipartFile.getSize());
            file.setPurpose(File.Purpose.CARD_LAYOUT);
            file.setUrl("http://" + InetAddress.getLoopbackAddress().getHostAddress() + ":" + serverProperties.getPort() + "/api/files/");
            fileRepository.save(file);
            file.setUrl(file.getUrl() + file.getId());
            saveFile(multipartFile, file);
            return file;
        }
        file.setContentType(multipartFile.getContentType());
        file.setOriginalName(multipartFile.getOriginalFilename());
        file.setSize(multipartFile.getSize());
        saveFile(multipartFile, file);
        return file;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InputStream> download(File file) {
        try {
            return Optional.of(
                    Files.newInputStream(
                            Paths.get(uploadDir + java.io.File.separator + file.getId() + parseContentType(file.getContentType()))
                    )
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<File> get(UUID id) {
        return fileRepository.findById(id);
    }

    private void saveFile(MultipartFile multipartFile, File file) {
        try {
            Files.createDirectories(Paths.get(uploadDir));
            Path copyDestination = buildFilePath(file.getId(), file.getContentType());
            Files.copy(multipartFile.getInputStream(), copyDestination, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Path buildFilePath(UUID id, String contentType) {
        return Paths.get(uploadDir + java.io.File.separator + id + parseContentType(contentType));
    }

    private String parseContentType(String contentType) {
        String extension = fileExtensionMap.get(contentType);
        return StringUtils.hasText(extension) ? "." + extension : "";
    }
}