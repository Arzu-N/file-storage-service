package com.example.filestorage.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.lang.annotation.Target;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="api_keys")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ApiKey {
    @Id@GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false,unique = true)
    private String prefix;

    @Column(nullable=false,unique=true)
    private String keyHash;

    @Column(nullable = false)
    private boolean active=true;

    @Column(nullable=false)
    @CreationTimestamp
    private LocalDateTime createdAt;


    public ApiKey(String prefix,String keyHash){
        this.prefix=prefix;
        this.keyHash=keyHash;
    }
}
