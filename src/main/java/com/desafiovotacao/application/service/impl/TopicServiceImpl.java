package com.desafiovotacao.application.service.impl;

import com.desafiovotacao.application.dto.TopicCreateDTO;
import com.desafiovotacao.application.response.TopicResponse;
import com.desafiovotacao.application.service.TopicService;
import com.desafiovotacao.domain.exception.BusinessException;
import com.desafiovotacao.domain.model.Topic;
import com.desafiovotacao.infrastructure.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class TopicServiceImpl implements TopicService {

    private final TopicRepository repository;

    @Override
    public TopicResponse create(TopicCreateDTO topicCreateDTO) {
        Topic existingTopic = repository.findByTitle(topicCreateDTO.title());

        if (!Objects.isNull(existingTopic)) {
            throw new BusinessException("Já possui uma pauta com o título: " + topicCreateDTO.title());
        }

        Topic topic = repository.save(new Topic(topicCreateDTO.title(), topicCreateDTO.description(), topicCreateDTO.createdBy()));

        log.info("Pauta criada com sucesso, id: {}", topic.getId());

        return TopicResponse.builder()
                .id(topic.getId())
                .title(topic.getTitle())
                .description(topic.getDescription())
                .createdBy(topic.getCreatedBy())
                .createdAt(topic.getCreatedAt())
                .updatedAt(topic.getUpdatedAt())
                .build();
    }
}