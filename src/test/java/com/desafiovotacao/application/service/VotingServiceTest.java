package com.desafiovotacao.application.service;

import com.desafiovotacao.application.dto.VoteCreateDTO;
import com.desafiovotacao.application.response.CountingVotesResponse;
import com.desafiovotacao.application.response.VotingResponse;
import com.desafiovotacao.application.service.impl.VotingServiceImpl;
import com.desafiovotacao.application.utils.DateUtil;
import com.desafiovotacao.domain.enums.AssociateVoteStatusEnum;
import com.desafiovotacao.domain.enums.SessionStatusEnum;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VotingServiceTest {
    @Mock
    private VoteRepository voteRepository;

    @Mock
    private AssociateRepository associateRepository;

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private VotingServiceImpl service;

    @Test
    void shouldThrowExceptionWhenNoOpenSessionExists() {
        when(sessionRepository.getSessionsByTopicId(1L))
                .thenReturn(List.of());

        VoteCreateDTO voteCreateDTO = new VoteCreateDTO(VoteValueEnum.YES.toString());

        assertThatThrownBy(() ->
                service.voting("1", "12345678900", voteCreateDTO))
                .isInstanceOf(VotingException.class)
                .hasMessage("Nao existe sessão aberta para a pauta");
    }

    @Test
    void shouldCloseExpiredSessionAndThrowException() {
        Session session = mockOpenSession();
        session.setEndAt(DateUtil.now().minusSeconds(300));

        when(sessionRepository.getSessionsByTopicId(1L))
                .thenReturn(List.of(session));

        VoteCreateDTO voteCreateDTO = new VoteCreateDTO(VoteValueEnum.YES.toString());

        assertThatThrownBy(() ->
                service.voting("1", "123", voteCreateDTO))
                .isInstanceOf(VotingException.class)
                .hasMessage("Sessão já encerrada");

        verify(sessionRepository).save(session);
    }

    @Test
    void shouldCreateAssociateAndVoteSuccessfully() {
        Session session = mockValidSession();

        when(sessionRepository.getSessionsByTopicId(1L))
                .thenReturn(List.of(session));
        when(associateRepository.findByFederalTaxId("123"))
                .thenReturn(null);
        when(topicRepository.getReferenceById(1L))
                .thenReturn(mockTopic());

        VotingResponse response = service.voting(
                "1", "123", new VoteCreateDTO(VoteValueEnum.YES.toString()));

        assertThat(response.status())
                .isEqualTo(AssociateVoteStatusEnum.ABLE_TO_VOTE);

        verify(voteRepository).save(any(Vote.class));
    }

    @Test
    void shouldThrowExceptionWhenAssociateAlreadyVoted() {
        Session session = mockValidSession();
        Associate associate = new Associate("123");

        when(sessionRepository.getSessionsByTopicId(1L))
                .thenReturn(List.of(session));
        when(associateRepository.findByFederalTaxId("123"))
                .thenReturn(associate);
        when(voteRepository.findByAssociateIdAndTopicId(associate.getId(), 1L))
                .thenReturn(List.of(new Vote()));

        assertThatThrownBy(() ->
                service.voting("1", "123", new VoteCreateDTO(VoteValueEnum.NO.toString())))
                .isInstanceOf(VotingException.class)
                .hasMessage("Associado já realizou a votação para essa pauta");
    }

    @Test
    void shouldThrowExceptionWhenSessionNotClosed() {
        Session session = mockValidSession();

        when(sessionRepository.getReferenceById(1L))
                .thenReturn(session);

        assertThatThrownBy(() -> service.count("1"))
                .isInstanceOf(VotingException.class);
    }

    @Test
    void shouldReturnCorrectVoteCounting() {
        Session session = mockClosedSession();

        List<Vote> votes = List.of(
                vote(VoteValueEnum.YES),
                vote(VoteValueEnum.NO),
                vote(VoteValueEnum.YES)
        );

        when(sessionRepository.getReferenceById(1L))
                .thenReturn(session);
        when(voteRepository.findVotesBySessionId(1L))
                .thenReturn(votes);

        CountingVotesResponse response = service.count("1");

        assertThat(response.totalVotes()).isEqualTo(3);
        assertThat(response.yes()).isEqualTo(2);
        assertThat(response.no()).isEqualTo(1);
    }

    private Session mockOpenSession() {
        Session session = new Session();
        session.setStatus(SessionStatusEnum.OPEN);
        return session;
    }

    private Session mockValidSession() {
        Session s = new Session();
        s.setStatus(SessionStatusEnum.OPEN);
        s.setEndAt(DateUtil.now().plusSeconds(300));
        s.setTopic(mockTopic());
        s.setId(1L);
        return s;
    }

    private Session mockClosedSession() {
        Session s = mockValidSession();
        s.setStatus(SessionStatusEnum.CLOSED);
        return s;
    }

    private Topic mockTopic() {
        Topic t = new Topic("Pauta", "Desc", "Autor");
        t.setId(1L);
        return t;
    }

    private Vote vote(VoteValueEnum value) {
        Vote v = new Vote();
        v.setValue(value);
        return v;
    }
}
