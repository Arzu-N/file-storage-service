package com.example.filestorage.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "admin")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminProperties {
    private String secret;
}
