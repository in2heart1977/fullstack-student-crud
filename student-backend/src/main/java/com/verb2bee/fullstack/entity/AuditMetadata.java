package com.verb2bee.fullstack.entity;

import jakarta.persistence.Embeddable;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Embeddable // ระบุว่าคลาสนี้สามารถนำไปฝังใน Entity อื่นได้
public class AuditMetadata {

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // No Lombok: Constructor, Getter, Setter...
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
