package com.desafiovotacao.application.service.impl;

import com.desafiovotacao.application.dto.SessionCreateDTO;
import com.desafiovotacao.application.response.SessionResponse;
import com.desafiovotacao.application.service.SessionService;
import com.desafiovotacao.application.utils.DateUtil;
import com.desafiovotacao.domain.enums.AssociateVoteStatusEnum;
import com.desafiovotacao.domain.enums.SessionStatusEnum;
import com.desafiovotacao.domain.exception.BusinessException;
import com.desafiovotacao.domain.exception.VotingException;
import com.desafiovotacao.domain.model.Session;
import com.desafiovotacao.domain.model.Topic;
import com.desafiovotacao.infrastructure.repository.SessionRepository;
import com.desafiovotacao.infrastructure.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessionServiceImpl implements SessionService {

    private final SessionRepository repository;
    private final TopicRepository topicRepository;
    private final SessionRepository sessionRepository;

    @Override
    public SessionResponse create(SessionCreateDTO sessionCreateDTO) {
        if (Objects.isNull(sessionCreateDTO.startAt()) && Objects.isNull(sessionCreateDTO.endAt())) {
            throw new BusinessException("Por favor, indique pelo menos uma das datas para a sessão");
        }

        Optional<Topic> topic = topicRepository.findById(sessionCreateDTO.topicId());

        if (topic.isEmpty()) {
            throw new BusinessException("Pauta não encontrada");
        }

        List<Session> sessions = repository.getSessionsByTopicId(sessionCreateDTO.topicId());

        Optional<Session> openedSession = sessions.stream().filter(s -> s.getStatus().equals(SessionStatusEnum.OPEN)).findFirst();

        if (openedSession.isPresent()) {
            if (openedSession.get().getEndAt().isBefore(DateUtil.now())) {
                openedSession.get().setStatus(SessionStatusEnum.CLOSED);

                sessionRepository.save(openedSession.get());
            }
            else {
                throw new BusinessException("Já possui sessão aberta para a pauta");
            }
        }

        Session session = new Session();

        if (!Objects.isNull(sessionCreateDTO.startAt()) && !Objects.isNull(sessionCreateDTO.endAt())) {
            session.setStartAt(DateUtil.createDateTime(sessionCreateDTO.startAt()));
            session.setEndAt(DateUtil.createDateTime(sessionCreateDTO.endAt()));

            if (session.getEndAt().isBefore(session.getStartAt())) {
                throw new BusinessException("A data de início da sessão não pode ser maior que a data de fechamento");
            }

            if (session.getStartAt().isBefore(DateUtil.now())) {
                throw new BusinessException("A data de início da sessão deve ser maior que a data/horário atual");
            }
        } else if (Objects.isNull(sessionCreateDTO.endAt())) {
            session.setStartAt(DateUtil.createDateTime(sessionCreateDTO.startAt()));
            session.setEndAt(DateUtil.createDateTime(sessionCreateDTO.startAt()).plusSeconds(60));
        } else {
            session.setEndAt(DateUtil.createDateTime(sessionCreateDTO.endAt()));

            Instant startDate = DateUtil.createDateTime(sessionCreateDTO.endAt()).minusSeconds(60);

            if (startDate.isBefore(DateUtil.now())) {
                throw new BusinessException("A sessão deve ter pelo menos 1 minuto de duração a partir da data/horário atual");
            }

            session.setStartAt(startDate);
        }

        session.setCreatedBy(sessionCreateDTO.createdBy());
        session.setStatus(SessionStatusEnum.OPEN);
        session.setTopic(topic.get());

        Session sessionCreated = repository.save(session);

        log.info("Sessão criada com sucesso");

        return SessionResponse.builder()
                .id(sessionCreated.getId())
                .topicId(session.getTopic().getId())
                .startAt(session.getStartAt())
                .endAt(session.getEndAt())
                .createdBy(session.getCreatedBy())
                .status(session.getStatus())
                .createdAt(session.getCreatedAt())
                .updatedAt(session.getUpdatedAt())
                .build();
    }
}