package com.example.filestorage.service;

import com.example.filestorage.entity.ApiKey;
import com.example.filestorage.repository.ApiKeyRepository;
import lombok.RequiredArgsConstructor;


import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApiKeyService {
private final SecureRandom secureRandom=new SecureRandom();
    private final ApiKeyRepository apiKeyRepository;

    public String generateApiKey(){
        String prefix = "fs_" + UUID.randomUUID().toString().substring(0, 8);
        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);
        String rawKey = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        String fullKey = prefix + "." + rawKey;
        String hash = BCrypt.hashpw(rawKey, BCrypt.gensalt());
        ApiKey apiKey = new ApiKey(prefix,hash);
        apiKeyRepository.save(apiKey);
        return fullKey;
    }

    public UUID validateAndGetId(String fullKey){
      if(!fullKey.contains("."))return null;
        String[] parts = fullKey.split("\\.");
        String prefix = parts[0];
        String rawKey = parts[1];
        ApiKey apiKey = apiKeyRepository.findByPrefixAndActiveTrue(prefix).orElse(null);
        if(apiKey==null) return null;
        if(!BCrypt.checkpw(rawKey,apiKey.getKeyHash())) return null;
        return apiKey.getId();
    }
}
