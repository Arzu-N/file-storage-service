package com.example.filestorage.service;

import com.example.filestorage.entity.AuditLog;
import com.example.filestorage.entity.FileMetadata;
import com.example.filestorage.provider.StorageProvider;
import com.example.filestorage.repository.AuditLogRepository;
import com.example.filestorage.repository.FileMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StorageService {
    private final StorageProvider storageProvider;
    private final FileMetadataRepository fileMetadataRepository;
    private final AuditLogRepository auditLogRepository;

    @Transactional
    public String upload(MultipartFile file,UUID apiKeyId){
        FileMetadata metaData = new FileMetadata();
        String path = storageProvider.upload(file);
        metaData.setFileName(file.getOriginalFilename());
        metaData.setStoragePath(path);
        metaData.setProvider(storageProvider.getProviderName());
        fileMetadataRepository.save(metaData);
        saveAudit("UPLOAD",apiKeyId);
        return path;
    }

    @Transactional
    public InputStream download(UUID fileId, UUID apiKeyId) {
        FileMetadata metadata = fileMetadataRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));

        saveAudit("DOWNLOAD", apiKeyId);

        return storageProvider.download(metadata.getStoragePath());
    }


    @Transactional
    public void delete(UUID fileId, UUID apiKeyId) {

        FileMetadata metadata = fileMetadataRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));

        storageProvider.delete(metadata.getStoragePath());

        fileMetadataRepository.delete(metadata);

        saveAudit("DELETE", apiKeyId);
    }


    private void saveAudit(String action, UUID apiKeyId) {
        AuditLog auditLog = new AuditLog();
        auditLog.setAction(action);
        auditLog.setApiKeyId(apiKeyId);
        auditLogRepository.save(auditLog);
    }
}

