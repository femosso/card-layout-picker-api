package com.example.demo.file.controller;

import com.example.demo.file.persistence.entity.File;
import com.example.demo.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/{id}")
    //@PreAuthorize("hasRole('ROLE_CLIENT')")
    public ResponseEntity<InputStreamResource> get(@PathVariable("id") UUID id) {
        Optional<File> meta = fileService.get(id);
        if (meta.isPresent()) {
            Optional<InputStream> is = fileService.download(meta.get());
            if (is.isPresent()) {
                return ResponseEntity.ok()
                        .contentLength(meta.get().getSize())
                        .contentType(MediaType.parseMediaType(meta.get().getContentType()))
                        .body(new InputStreamResource(is.get()));
            }
        }
        return ResponseEntity.badRequest().build();
    }
}