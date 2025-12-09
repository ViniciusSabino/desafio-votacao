package com.desafiovotacao.application.service;


import com.desafiovotacao.application.dto.TopicCreateDTO;
import com.desafiovotacao.application.response.TopicResponse;
import com.desafiovotacao.application.service.impl.TopicServiceImpl;
import com.desafiovotacao.domain.exception.BusinessException;
import com.desafiovotacao.domain.model.Topic;
import com.desafiovotacao.infrastructure.repository.TopicRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TopicServiceTest {
    @Mock
    private TopicRepository repository;

    @InjectMocks
    private TopicServiceImpl service;

    @Test
    void shouldCreateTopicSuccessfully() {
        TopicCreateDTO dto = new TopicCreateDTO(
                "Pauta Teste",
                "Descrição da pauta",
                "Vinicius Rocha"
        );

        when(repository.findByTitle(dto.title())).thenReturn(null);

        Topic savedTopic = new Topic(
                dto.title(),
                dto.description(),
                dto.createdBy()
        );

        savedTopic.setId(1L);

        when(repository.save(any(Topic.class))).thenReturn(savedTopic);


        TopicResponse response = service.create(dto);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.title()).isEqualTo(dto.title());
        assertThat(response.description()).isEqualTo(dto.description());
        assertThat(response.createdBy()).isEqualTo(dto.createdBy());

        verify(repository).findByTitle(dto.title());
        verify(repository).save(any(Topic.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldThrowExceptionWhenTopicWithSameTitleAlreadyExists() {
        TopicCreateDTO dto = new TopicCreateDTO(
                "Pauta Duplicada",
                "Descrição",
                "Autor"
        );

        Topic existingTopic = new Topic(
                dto.title(),
                dto.description(),
                dto.createdBy()
        );

        when(repository.findByTitle(dto.title())).thenReturn(existingTopic);

        assertThatThrownBy(() -> service.create(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Já possui uma pauta com o título: " + dto.title());

        verify(repository).findByTitle(dto.title());
        verify(repository, never()).save(any());
        verifyNoMoreInteractions(repository);
    }
}
