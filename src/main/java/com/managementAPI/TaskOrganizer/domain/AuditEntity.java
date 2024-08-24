package com.managementAPI.TaskOrganizer.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditEntity implements Serializable {
    @CreatedDate
    @Column(updatable = false)
    private Instant createdDateTime = Instant.now();
    @LastModifiedDate
    private Instant lastModifiedDateTime = Instant.now();

    public Instant getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public Instant getLastModifiedDateTime() {
        return lastModifiedDateTime;
    }

    public void setLastModifiedDateTime(Instant lastModifiedDateTime) {
        this.lastModifiedDateTime = lastModifiedDateTime;
    }
}
