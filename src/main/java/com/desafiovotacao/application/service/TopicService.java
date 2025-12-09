package com.desafiovotacao.application.service;

import com.desafiovotacao.application.dto.TopicCreateDTO;
import com.desafiovotacao.application.response.TopicResponse;

public interface TopicService {
    TopicResponse create(TopicCreateDTO topicCreateDTO);
}