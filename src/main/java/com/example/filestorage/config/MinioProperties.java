package com.example.filestorage.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="minio")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MinioProperties {
    private String url;
    private String accessKey;
    private String secretKey;
    private String bucket;
}
