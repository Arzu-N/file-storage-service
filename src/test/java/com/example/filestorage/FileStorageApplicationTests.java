package com.example.filestorage;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:postgresql://postgres:5432/storage",
        "spring.datasource.username=postgres",
        "spring.datasource.password=postgres123"
})
class FileStorageApplicationTests {

    @Test
    void contextLoads() {
    }

}
