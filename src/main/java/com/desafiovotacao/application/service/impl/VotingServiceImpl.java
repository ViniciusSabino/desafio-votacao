package com.desafiovotacao.application.service.impl;

import com.desafiovotacao.application.response.CountingVotesResponse;
import com.desafiovotacao.application.dto.VoteCreateDTO;
import com.desafiovotacao.application.response.VotingResponse;
import com.desafiovotacao.application.service.VotingService;
import com.desafiovotacao.application.utils.DateUtil;
import com.desafiovotacao.domain.enums.SessionStatusEnum;
import com.desafiovotacao.domain.enums.AssociateVoteStatusEnum;
import com.desafiovotacao.domain.enums.VoteValueEnum;
import com.desafiovotacao.domain.exception.VotingException;
import com.desafiovotacao.domain.model.Associate;
import com.desafiovotacao.domain.model.Session;
import com.desafiovotacao.domain.model.Topic;
import com.desafiovotacao.domain.model.Vote;
import com.desafiovotacao.infrastructure.repository.AssociateRepository;
import com.desafiovotacao.infrastructure.repository.SessionRepository;
import com.desafiovotacao.infrastructure.repository.TopicRepository;
import com.desafiovotacao.infrastructure.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VotingServiceImpl implements VotingService {

    private final VoteRepository repository;
    private final AssociateRepository associateRepository;
    private final TopicRepository topicRepository;
    private final SessionRepository sessionRepository;

    @Override
    public VotingResponse voting(String topicId, String associateFederalTaxId, VoteCreateDTO voteCreateDTO) {
        List<Session> sessions = sessionRepository.getSessionsByTopicId(Long.valueOf(topicId));

        Optional<Session> activeSession = sessions.stream().filter(s -> s.getStatus() == SessionStatusEnum.OPEN).findFirst();

        if (activeSession.isEmpty()) {
            throw new VotingException("Nao existe sessão aberta para a pauta", AssociateVoteStatusEnum.UNABLE_TO_VOTE);
        }

        Session currentSession = activeSession.get();

        if (currentSession.getEndAt().isBefore(DateUtil.now())) {
            currentSession.setStatus(SessionStatusEnum.CLOSED);

            sessionRepository.save(currentSession);

            throw new VotingException("Sessão já encerrada", AssociateVoteStatusEnum.UNABLE_TO_VOTE);
        }

        Associate associate = associateRepository.findByFederalTaxId(associateFederalTaxId);

        Topic topic = topicRepository.getReferenceById(Long.valueOf(topicId));

        if (Objects.isNull(associate)) {
            Associate createdAssociate = associateRepository.save(new Associate(associateFederalTaxId));

            Vote vote = new Vote(topic, createdAssociate, currentSession, voteCreateDTO.value());

            repository.save(vote);

            return new VotingResponse("Voto computado", AssociateVoteStatusEnum.ABLE_TO_VOTE);
        }

        List<Vote> votes = repository.findByAssociateIdAndTopicId(associate.getId(), Long.valueOf(topicId));


        if (!votes.isEmpty()) {
            throw new VotingException("Associado já realizou a votação para essa pauta", AssociateVoteStatusEnum.UNABLE_TO_VOTE);
        }

        Vote vote = new Vote(topic, associate, currentSession, voteCreateDTO.value());

        repository.save(vote);

        return new VotingResponse("Voto computado", AssociateVoteStatusEnum.ABLE_TO_VOTE);
    }

    @Override
    public CountingVotesResponse count(String sessionId) {
        Session session = sessionRepository.getReferenceById(Long.valueOf(sessionId));

        if (session.getEndAt().isBefore(DateUtil.now())) {
            session.setStatus(SessionStatusEnum.CLOSED);

            sessionRepository.save(session);
        }

        if (session.getStatus() != SessionStatusEnum.CLOSED) {
            throw new VotingException("A Sessão ainda não foi encerrada");
        }

        List<Vote> votes = repository.findVotesBySessionId(Long.valueOf(sessionId));

        return CountingVotesResponse.builder()
                .sessionId(session.getId())
                .topicId(session.getTopic().getId())
                .status(session.getStatus())
                .totalVotes(votes.size())
                .yes(votes.stream().filter(v -> v.getValue().equals(VoteValueEnum.YES)).toList().size())
                .no(votes.stream().filter(v -> v.getValue().equals(VoteValueEnum.NO)).toList().size())
                .build();
    }
}