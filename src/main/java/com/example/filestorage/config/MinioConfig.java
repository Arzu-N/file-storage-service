package com.example.filestorage.config;

import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MinioConfig {

    @Bean
    public MinioClient minioClient(MinioProperties minioProperties){
      return  MinioClient.builder().endpoint(minioProperties.getUrl())
                .credentials(minioProperties.getAccessKey(),
                        minioProperties.getSecretKey())
                .build();
    }
}
