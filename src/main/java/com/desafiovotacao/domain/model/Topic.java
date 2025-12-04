package com.desafiovotacao.domain.model;

import com.desafiovotacao.domain.enums.TopicStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
@Table(name = "topic")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "topic")
    private Session session;

    @Column(name = "title", length = 255, nullable = false, unique = true)
    private String title;

    @Column(name = "description", length = 255, nullable = false, unique = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private TopicStatusEnum status;

    @Column(name = "createdBy", length = 255, nullable = false, unique = false)
    private String createdBy;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
