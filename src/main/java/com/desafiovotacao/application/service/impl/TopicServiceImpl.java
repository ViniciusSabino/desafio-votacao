package com.desafiovotacao.application.service.impl;

import com.desafiovotacao.application.dto.TopicCreateDTO;
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
    public Topic create(TopicCreateDTO topicCreateDTO) {
        Topic existingTopic = repository.findByTitle(topicCreateDTO.title());

        if(!Objects.isNull(existingTopic)) {
            throw new BusinessException("A topic has already been created with the title: " + topicCreateDTO.title());
        }

        Topic topic = repository.save(new Topic(topicCreateDTO));

        log.info("Topic created successfully, id: {}", topic.getId());

        return topic;
    }
}