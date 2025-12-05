package com.desafiovotacao.domain.model;

import com.desafiovotacao.application.dto.SessionDTO;
import com.desafiovotacao.application.dto.TopicDTO;
import com.desafiovotacao.application.utils.DateUtil;
import com.desafiovotacao.domain.enums.SessionStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Data
@Table(name = "session")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "topic_id", nullable = false, unique = true)
    private Topic topic;

    @Column(name = "start_at", nullable = false)
    private Instant startAt;

    @Column(name = "end_at", nullable = false)
    private Instant endAt;

    @Enumerated(EnumType.STRING)
    private SessionStatusEnum status;

    @Column(name = "createdBy", length = 255, nullable = false, unique = false)
    private String createdBy;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public SessionDTO toDTO() {

        return SessionDTO.builder()
                .id(this.id)
                .title(this.title)
                .description(this.description)
                .status(this.status)
                .createdBy(this.createdBy)
                .createdAt(DateUtil.formatDate(this.createdAt))
                .updatedAt(DateUtil.formatDate(this.updatedAt))
                .build();
    }
}
