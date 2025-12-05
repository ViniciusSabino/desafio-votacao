package com.desafiovotacao.presentation.controller.impl;

import com.desafiovotacao.application.dto.TopicCreateDTO;
import com.desafiovotacao.application.dto.TopicDTO;
import com.desafiovotacao.application.service.TopicService;
import com.desafiovotacao.domain.exception.BusinessException;
import com.desafiovotacao.domain.model.Topic;
import com.desafiovotacao.presentation.controller.TopicController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/topic", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class TopicControllerImpl implements TopicController {

    private final TopicService service;

    @PostMapping
    @Override
    public ResponseEntity<TopicDTO> create(@Valid @RequestBody TopicCreateDTO topicCreateDTO) throws BusinessException {
        log.trace("POST /topic - Creating a new voting topic");

        Topic topic = service.create(topicCreateDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(topic.toDTO());
    }
}