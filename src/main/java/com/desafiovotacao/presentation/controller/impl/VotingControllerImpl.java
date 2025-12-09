package com.desafiovotacao.presentation.controller.impl;

import com.desafiovotacao.application.response.CountingVotesResponse;
import com.desafiovotacao.application.dto.VoteCreateDTO;
import com.desafiovotacao.application.response.VotingResponse;
import com.desafiovotacao.application.service.VotingService;
import com.desafiovotacao.domain.exception.BusinessException;
import com.desafiovotacao.presentation.controller.VotingController;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class VotingControllerImpl implements VotingController {

    private final VotingService service;

    @PostMapping("{topicId}/voting/{associateFederalTaxId}")
    @Override
    public ResponseEntity<VotingResponse> voting(@PathVariable("topicId") @Valid String topicId,
                                                 @PathVariable("associateFederalTaxId") @Valid String associateFederalTaxId,
                                                 @RequestBody @Valid VoteCreateDTO voteCreateDTO) throws BusinessException {
        log.trace("POST /{topicId}/voting/{associateFederalTaxId} - Realizando uma votação para a pauta: " + topicId);

        VotingResponse response = service.voting(topicId, associateFederalTaxId, voteCreateDTO);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("voting/{sessionId}/result")
    @Override
    public ResponseEntity<CountingVotesResponse> count(@PathVariable("sessionId") @Valid String sessionId) throws BusinessException {
        log.trace("GET /voting/{sessionId}/result - Contabilizando votos para a sessão: " + sessionId);

        CountingVotesResponse response = service.count(sessionId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}