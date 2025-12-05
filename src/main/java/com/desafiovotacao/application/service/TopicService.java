package com.desafiovotacao.application.service;

import com.desafiovotacao.application.dto.TopicCreateDTO;
import com.desafiovotacao.domain.model.Topic;

public interface TopicService {

    Topic create(TopicCreateDTO topicCreateDTO);
}
