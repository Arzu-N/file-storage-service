package com.example.filestorage.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="audit_logs")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String action;

    @Column(nullable = false)
    private UUID apiKeyId;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

}
