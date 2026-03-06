package com.example.filestorage.provider;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface StorageProvider {
    String upload(MultipartFile multipartFile);
    InputStream download(String path);
    void delete(String path);
    String getProviderName();

}
