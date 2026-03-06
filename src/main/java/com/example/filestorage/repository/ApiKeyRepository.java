package com.example.filestorage.repository;

import com.example.filestorage.entity.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ApiKeyRepository extends JpaRepository<ApiKey, UUID> {
    Optional<ApiKey>findByPrefixAndActiveTrue(String prefix);
}
