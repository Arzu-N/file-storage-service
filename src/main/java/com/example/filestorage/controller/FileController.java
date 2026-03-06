package com.example.filestorage.controller;

import com.example.filestorage.service.StorageService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {

    private final StorageService storageService;


    @PostMapping
    public ResponseEntity<String> upload(@RequestParam MultipartFile file,
                                         HttpServletRequest request) {
        UUID apiKeyId = (UUID) request.getAttribute("apiKeyId");
        if (apiKeyId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("API key invalid");
        }
        try {
            String path = storageService.upload(file, apiKeyId);
            return ResponseEntity.ok(path);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body("Something went wrong: " +
                    e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> download(@PathVariable UUID id,
                                           HttpServletRequest request) {
        UUID apiKeyId = (UUID) request.getAttribute("apiKeyId");
        if (apiKeyId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("API key invalid");
        }
        try {
            InputStream stream = storageService.download(id, apiKeyId);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + id + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new InputStreamResource(stream));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something went wrong: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id,
                                         HttpServletRequest request) {
        UUID apiKeyId = (UUID) request.getAttribute("apiKeyId");
        if (apiKeyId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("API key invalid");
        }
        try {
            storageService.delete(id, apiKeyId);
            return ResponseEntity.ok("File deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something went wrong: " + e.getMessage());
        }
    }
}

