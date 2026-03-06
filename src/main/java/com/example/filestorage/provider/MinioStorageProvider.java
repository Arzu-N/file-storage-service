package com.example.filestorage.provider;

import com.example.filestorage.config.MinioProperties;
import io.minio.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MinioStorageProvider implements StorageProvider {
    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

@PostConstruct
public void init(){
    createBucketIfNotExists();
}

    private void createBucketIfNotExists() {
        try {
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().
                    bucket(minioProperties.getBucket()).build());
            if (!exists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(
                        minioProperties.getBucket()).build());
            }
        } catch (Exception ex) {
            throw new RuntimeException("Bucket creation failed");
        }
    }


    @Override
    public String upload(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(fileName).stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());
            return fileName;
        } catch (Exception ex) {

            throw new RuntimeException("Upload failed: " + ex.getMessage());
        }
    }

    @Override
    public InputStream download(String path) {
        try{
           return  minioClient.getObject(GetObjectArgs.builder().
                   bucket(minioProperties.getBucket())
                    .object(path).build());
        }
        catch (Exception ex){
            throw new RuntimeException("Download failed");
        }
    }

    @Override
    public void delete(String path) {
try{
    minioClient.removeObject(RemoveObjectArgs.builder()
            .bucket(minioProperties.getBucket())
            .object(path).build());
}
catch(Exception ex){
    throw new RuntimeException("Delete failed");
}
    }

    @Override
    public String getProviderName() {
        return "minio";
    }
}


