package com.example.filestorage.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.units.qual.C;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@Table(name="files")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FileMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String storagePath;

    @Column(nullable = false)
    private String provider;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime uploadedAt;
}
