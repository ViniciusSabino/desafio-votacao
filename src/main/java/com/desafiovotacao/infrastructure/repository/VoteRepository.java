package com.desafiovotacao.infrastructure.repository;

import com.desafiovotacao.domain.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
 List<Vote> findByAssociateIdAndTopicId(Long associateId, Long topicId);

 List<Vote> findVotesBySessionId(Long sessionId);
}