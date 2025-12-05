package com.desafiovotacao.domain.model;

import com.desafiovotacao.application.dto.TopicCreateDTO;
import com.desafiovotacao.application.dto.TopicDTO;
import com.desafiovotacao.application.utils.DateUtil;
import com.desafiovotacao.domain.enums.TopicStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Data
@Table(name = "topic")
@NoArgsConstructor
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 255, nullable = false, unique = true)
    private String title;

    @Column(name = "description", length = 255, nullable = false, unique = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private TopicStatusEnum status;

    @Column(name = "createdBy", length = 255, nullable = false, unique = false)
    private String createdBy;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public Topic(TopicCreateDTO topicCreateDTO) {
        this.title = topicCreateDTO.title();
        this.description = topicCreateDTO.description();
        this.status = TopicStatusEnum.CREATED;
        this.createdBy = topicCreateDTO.createdBy();
    }

    public TopicDTO toDTO() {

        return TopicDTO.builder()
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
