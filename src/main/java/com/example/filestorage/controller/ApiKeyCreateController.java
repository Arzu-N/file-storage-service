package com.example.filestorage.controller;

import com.example.filestorage.config.AdminProperties;
import com.example.filestorage.service.ApiKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api-keys")
public class ApiKeyCreateController {

    private final AdminProperties adminProperties;
    private final ApiKeyService apiKeyService;

    @PostMapping("/create")
    public ResponseEntity<String>createApikey(@RequestHeader("X-ADMIN-SECRET")String adminSecret){
        if(!adminSecret.equals(adminProperties.getSecret())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(apiKeyService.generateApiKey());
    }
}
