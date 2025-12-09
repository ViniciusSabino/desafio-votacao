package com.desafiovotacao.application.service;

import com.desafiovotacao.application.response.CountingVotesResponse;
import com.desafiovotacao.application.dto.VoteCreateDTO;
import com.desafiovotacao.application.response.VotingResponse;

public interface VotingService {
    VotingResponse voting(String topicId, String associateFederalTaxId, VoteCreateDTO voteCreateDTO);

    CountingVotesResponse count(String sessionId);
}